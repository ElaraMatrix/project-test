package logger;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

@UtilityClass
public class LogConfigExtractor {

    private static final Properties properties = new Properties();

    private static final File file = new File(LogConfigExtractor.class.getResource("/log4j2.properties").getFile());

    static {
        try (var reader = java.nio.file.Files.newBufferedReader(file.toPath(), Charset.defaultCharset())) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String LOG_FILE_PATH = getProperty("property.filepath");

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
