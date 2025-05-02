package utilities.Requests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import utilities.Constants;
import utilities.helper.RequestSpec;

public class UserUtils {

    public static Response getSingleUser(int id, int statusCode, String token) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .when()
                .get(Constants.BASE_URL + Constants.USERS_ENDPOINT + "/" + id)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }


    public static Response getAllUsersForPage(int pageNumber , int statusCode, String token) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .queryParam("page", pageNumber)
                .when()
                .get(Constants.BASE_URL + Constants.USERS_ENDPOINT)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static Response UpdateUser(int id, String token, User user, int statusCode) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .body(user)
                .when()
                .put(Constants.BASE_URL + Constants.USERS_ENDPOINT + "/" + id)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static Response deleteUser(int id, String token, int statusCode) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .when()
                .delete(Constants.BASE_URL + Constants.USERS_ENDPOINT + "/" + id)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static Response createUser(String token, User user, int statusCode) {
        return RestAssured.given()
                .spec(RequestSpec.getRequestSpec())
                .header("Authorization", token)
                .body(user)
                .when()
                .post(Constants.BASE_URL + Constants.USERS_ENDPOINT)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
