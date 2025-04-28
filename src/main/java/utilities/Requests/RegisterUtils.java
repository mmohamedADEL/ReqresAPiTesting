package utilities.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.helper.RequestSpec;

import static utilities.helper.RequestBody.getRegisterRequestBody;

public class RegisterUtils {
    public static Response register(String email, String password, int statusCode) {
        return RestAssured
                .given()
                .spec(RequestSpec.getRequestSpec())
                .body(getRegisterRequestBody(email, password))
                .when()
                .post(utilities.Constants.BASE_URL + utilities.Constants.REGISTER_ENDPOINT)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
