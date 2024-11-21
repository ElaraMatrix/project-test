package tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.JUnitXMLReporter;

import static io.restassured.RestAssured.*;

public class ExampleTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String BASE_ENDPOINT = "posts";

    @BeforeClass
    public void setupListeners(ITestContext context) {
        context.getSuite().addListener(new JUnitXMLReporter());
    }

    @Test
    public void exampleTest() {
        Response response = buildBaseRequest()
                .get(String.format("%s/1", BASE_ENDPOINT));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Incorrect status");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("title"), "Incorrect body");
    }

    private RequestSpecification buildBaseRequest() {
        return given().baseUri(BASE_URL);
    }
}
