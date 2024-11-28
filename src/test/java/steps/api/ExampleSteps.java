package steps.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

@UtilityClass
public class ExampleSteps {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static final String BASE_ENDPOINT = "posts";

    @Step("GET /posts/{id}")
    public Response request(int id) {
        return buildBaseRequest()
                .get(String.format("%s/%s", BASE_ENDPOINT, id));
    }

    @Step("Check that status code is {expectedStatusCode}")
    public void checkStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Incorrect status");
    }

    @Step("Check that response body is correct")
    public void checkResponseBody(Response response) {
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("title"), "Incorrect body");
    }

    private RequestSpecification buildBaseRequest() {
        return given().baseUri(BASE_URL);
    }
}
