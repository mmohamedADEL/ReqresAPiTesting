package utilities.Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import data.Constants;
import utilities.helper.RequestSpec;

public class ResourceUtility {
    public static Response getSingleResource(int id, int statusCode, String token) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .when()
                .get(Constants.BASE_URL + Constants.RESOURCE_ENDPOINT + "/" + id)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static Response getAllResources( int statusCode, String token) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .when()
                .get(Constants.BASE_URL + Constants.RESOURCE_ENDPOINT)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }

}
