package utils.allure;

import logger.Log;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

@Slf4j
@UtilityClass
public class AllureEnvironment {

    public static final String ROOT = System.getProperty("user.dir") + File.separator;
    public static final String ALLURE_RESULTS_PATH = ROOT + "allure-results" + File.separator;
    public static final String ENVIRONMENT_PATH = ALLURE_RESULTS_PATH + "environment.properties";

    private static final Properties properties = new Properties();

    private static final File dir = new File(ALLURE_RESULTS_PATH);
    private static final File file = new File(ENVIRONMENT_PATH);

    static {
        try {
            deleteDirectory(dir.toPath());
            Log.info("allure-results directory has been deleted");
        } catch (IOException e) {
            Log.error("Failed to delete allure-results directory: " + e.getMessage());
            throw new RuntimeException(e);
        }
        if (dir.mkdir()) Log.info("allure-results directory has been created");
        try {
            if (file.createNewFile()) Log.info("environments.properties has been created in allure-results");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (var reader = Files.newBufferedReader(file.toPath(), Charset.defaultCharset())) {
            properties.load(reader);
        } catch (IOException e) {
            Log.error("environments.properties error: " + e);
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static void setValue(String key, String value) {
        Log.info("set environments.properties value: [" + key + "=" + value + "]");
        properties.setProperty(key, value);
        saveFile();
    }

    public static void removeValue(String key) {
        Log.info("remove environments.properties value by key: [" + key + "]");
        properties.remove(key);
        saveFile();
    }

    public static void clearFile() {
        Log.info("environments.properties has been cleared");
        properties.forEach(properties::remove);
    }

    private static void saveFile() {
        try (var writer = Files.newBufferedWriter(file.toPath(), Charset.defaultCharset())) {
            properties.store(writer, null);
        } catch (IOException e) {
            Log.error("environments.properties error: " + e);
            throw new RuntimeException(e);
        }
    }

    private static void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file); // Удалить файл
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir); // Удалить директорию после удаления её содержимого
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
