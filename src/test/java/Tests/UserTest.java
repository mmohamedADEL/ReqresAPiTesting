package Tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import data.Constants;
import utilities.Requests.LoginUtility;
import utilities.Requests.UserUtils;
import utilities.helper.DataUtility;

import java.io.FileNotFoundException;

public class UserTest {
    private String token;

    @BeforeClass
    public void login() {
        Response response = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        JsonPath jsonPath = response.jsonPath();
        token = "Bearer " + jsonPath.getString("token");
    }

    @Test
    public void TestGetSingleUser() {
        int id = 7;
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.getSingleUser(id, 200, token).jsonPath();
        softAssert.assertEquals(jsonPath.getInt("data.id"), id, "id is not correct");
        softAssert.assertNotNull(jsonPath.getString("data.email"), "email is null");
        softAssert.assertNotNull(jsonPath.getString("data.first_name"), "first name is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertAll();
    }
    @Test
    public void TestGetSingleUserWithInvalidId() {
        int id = 23;
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.getSingleUser(id, 404, token).jsonPath();
        softAssert.assertEquals(jsonPath.getString("data"), null, "data is not null");
        softAssert.assertAll();
    }
    @Test
    public void TestGetAllUsersInSecondPage() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.getAllUsersForPage(2,200, token).jsonPath();
        softAssert.assertNotNull(jsonPath.getList("data"), "data is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertEquals(jsonPath.getInt("data[0].id"), 7, "id is not correct");
        softAssert.assertEquals(jsonPath.getString("data[0].email"), "michael.lawson@reqres.in", "email first user is not correct");
        softAssert.assertEquals(jsonPath.getString("data[0].first_name"), "Michael", "first name first user is not correct");
        softAssert.assertEquals(jsonPath.getInt("per_page"), 6, "per page is not correct");
        softAssert.assertAll();
    }
    @Test
    public void TestGetAllUsersInFirstPage() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.getAllUsersForPage(1,200, token).jsonPath();
        softAssert.assertNotNull(jsonPath.getList("data"), "data is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertEquals(jsonPath.getInt("data[0].id"), 1, "id is not correct");
        softAssert.assertEquals(jsonPath.getString("data[0].email"), "george.bluth@reqres.in", "email first user is not correct");
        softAssert.assertEquals(jsonPath.getInt("per_page"), 6, "per page is not correct");
        softAssert.assertAll();
    }
    @Test
    public void TestUpdateUserdata() throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();
        User user = new User(
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"name"),
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"job"));
        JsonPath jsonPath = UserUtils.UpdateUser(2,token , user  ,200).jsonPath();
        softAssert.assertEquals(jsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(jsonPath.getString("job"), user.getJob(), "job is not correct");
        softAssert.assertNotNull(jsonPath.getString("updatedAt"), "updatedAt is null");
        softAssert.assertAll();
    }
    @Test
    public void TestUpdateUserWithInvalidId() {
        SoftAssert softAssert = new SoftAssert();

        User user = new User(
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"name"),
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"job")
        );
        JsonPath jsonPath = UserUtils.UpdateUser(23,token ,user  ,404).jsonPath();
        //softAssert.assertEquals(jsonPath.getString("error"), "Not Found", "error is not correct");
        softAssert.assertAll();
    }
    @Test
    public void TestDeleteUserCheckResponse() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.deleteUser(2, token, 204).jsonPath();
        //softAssert.assertNull(jsonPath, "Body is not null");
        softAssert.assertAll();
    }
    @Test
    public void TestDeleteUserCheckFunctionality() {
        SoftAssert softAssert = new SoftAssert();
        UserUtils.deleteUser(2, token, 204).jsonPath();
        JsonPath jsonPath = UserUtils.getSingleUser(2, 404, token).jsonPath();
        softAssert.assertAll();
    }
    @Test
    public void TestCreateUser() throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();
        User user = new User(
                DataUtility.getJsonData("TestData","CreateUserData" ,"name"),
                DataUtility.getJsonData("TestData","CreateUserData" ,"job")
        );
        JsonPath jsonPath = UserUtils.createUser(token , user , 201) .jsonPath();
        softAssert.assertNotNull(jsonPath.getString("id"), "ID is null");
        softAssert.assertEquals(jsonPath.getString("name"), user.getName(), "name is Not correct");
        softAssert.assertEquals(jsonPath.getString("job"), user.getJob(), "job is Not correct");
        softAssert.assertAll();
    }



}
