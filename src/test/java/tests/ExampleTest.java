package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import logger.LogConfigExtractor;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.JUnitXMLReporter;
import steps.api.ExampleSteps;
import utils.allure.AllureEnvironment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExampleTest {

    @BeforeClass
    public void setupListeners(ITestContext context) {
        context.getSuite().addListener(new JUnitXMLReporter());
    }

    @BeforeMethod
    public void setUp() {
        AllureEnvironment.clearFile();
        AllureEnvironment.setValue("BASE_URL", ExampleSteps.BASE_URL);
    }

    @AfterMethod
    public void addAttachment() throws FileNotFoundException {
        Allure.addAttachment("Log", new FileInputStream(LogConfigExtractor.LOG_FILE_PATH));
    }

    @Test(description = "JsonPlaceholder API Test")
    @Description("JsonPlaceholder API testing")
    public void exampleTest() {
        Response response = ExampleSteps.request(1);
        ExampleSteps.checkStatusCode(response, HttpStatus.SC_OK);
        ExampleSteps.checkResponseBody(response);
    }
}
