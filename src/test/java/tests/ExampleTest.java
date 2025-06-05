package tests;

import aquality.tracking.integrations.core.AqualityTrackingLifecycle;
import aquality.tracking.integrations.core.ServicesModule;
import aquality.tracking.integrations.core.configuration.IConfiguration;
import com.google.inject.Injector;
import enums.AqualityTrackingTestResult;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import logger.LogConfigExtractor;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.reporters.JUnitXMLReporter;
import steps.api.ExampleSteps;
import utils.allure.AllureEnvironment;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExampleTest {

    private static final Injector INJECTOR = com.google.inject.Guice.createInjector(new ServicesModule());
    private final IConfiguration trackingConfig = INJECTOR.getInstance(IConfiguration.class);
    private final AqualityTrackingLifecycle tracking = new AqualityTrackingLifecycle();

    @BeforeTest
    public void setupListeners(ITestContext context) {
        tracking.startTestRun();
        context.getSuite().addListener(new JUnitXMLReporter());
    }

    @AfterTest
    public void testTearDown(ITestContext context) {
        tracking.finishTestRun();
    }

    @BeforeMethod
    public void setUp(ITestContext context) {
        tracking.startTestExecution(context.getName());
        AllureEnvironment.clearFile();
        AllureEnvironment.setValue("BASE_URL", ExampleSteps.BASE_URL);
    }

    @AfterMethod
    @SneakyThrows
    public void addAttachment(ITestResult testResult) {
        byte[] logBytes = Files.readAllBytes(Paths.get(LogConfigExtractor.LOG_FILE_PATH));
        tracking.addAttachment("Log", logBytes);
        tracking.finishTestExecution(AqualityTrackingTestResult.getStatusByITestResultStatus(testResult.getStatus()),
                "Fail Reason Message");
        Allure.addAttachment("Log", new FileInputStream(LogConfigExtractor.LOG_FILE_PATH));
    }

    @Test(description = "JsonPlaceholder API Test")
    @Issue("DEFECT-1")
    @Description("JsonPlaceholder API testing")
    public void exampleTest() {
        Response response = ExampleSteps.request(1);
        ExampleSteps.checkStatusCode(response, HttpStatus.SC_OK);
        ExampleSteps.checkResponseBody(response);
    }
}
