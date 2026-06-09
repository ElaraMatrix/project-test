package tests;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import steps.api.ExampleSteps;
import utils.allure.AllureHistoryStatusOverrideListener;

@Epic("JSONPlaceholder API")
@Feature("Posts API")
@Listeners(AllureHistoryStatusOverrideListener.class)
public class SimplePassingTest {

    @Tag("simple")
    @Test(description = "GET /posts/1 returns 200 OK")
    @Story("Get single post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checks that GET /posts/1 returns HTTP 200 and body contains 'title'")
    public void getPostReturnsOk() {
        Response response = ExampleSteps.request(1);
        ExampleSteps.checkStatusCode(response, HttpStatus.SC_OK);
        ExampleSteps.checkResponseBody(response);
        Allure.label("testType", "simple");
    }

    @Test(description = "GET /posts/2 returns 200 OK")
    @Story("Get single post")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checks that GET /posts/2 returns HTTP 200")
    public void getSecondPostReturnsOk() {
        Response response = ExampleSteps.request(2);
        ExampleSteps.checkStatusCode(response, HttpStatus.SC_OK);
    }

    @Test(description = "Response body is not empty")
    @Story("Response body validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Checks that response body from GET /posts/1 is not blank")
    public void responseBodyIsNotEmpty() {
        Response response = ExampleSteps.request(1);
        String body = response.getBody().asString();
        Assert.assertFalse(body.isBlank(), "Response body must not be empty");
    }

    @Test(description = "Response content-type is JSON")
    @Story("Response headers validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Checks that Content-Type header contains 'application/json'")
    public void responseContentTypeIsJson() {
        Response response = ExampleSteps.request(1);
        String contentType = response.getContentType();
        Assert.assertTrue(contentType.contains("application/json"),
                "Expected application/json but got: " + contentType);
    }

    @Test(description = "Response time is acceptable")
    @Story("Performance")
    @Severity(SeverityLevel.MINOR)
    @Description("Checks that GET /posts/1 responds within 5 seconds")
    public void responseTimeIsAcceptable() {
        Response response = ExampleSteps.request(1);
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 5000,
                "Response time exceeded 5000ms: " + responseTime + "ms");
    }
}
