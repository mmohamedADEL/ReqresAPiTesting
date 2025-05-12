package utilities.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import data.Constants;
import utilities.helper.RequestBody;
import utilities.helper.RequestSpec;

public class LoginUtility {

    public static Response login(String username, String password , int statusCode) {
        return  RestAssured
                .given()
                .spec(RequestSpec.getRequestSpec())
                .body(RequestBody.getLoginRequestBody(username, password))
                .when()
                .post(Constants.BASE_URL + Constants.LOGIN_ENDPOINT)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}