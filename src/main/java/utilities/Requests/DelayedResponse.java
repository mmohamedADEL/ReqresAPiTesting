package utilities.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.Constants;
import utilities.helper.RequestSpec;

public class DelayedResponse {
    public static Response getDelayedResponse(int delay, int statusCode) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .when()
                .get(Constants.BASE_URL + Constants.DELAYED_ENDPOINT + "?delay=" + delay)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
