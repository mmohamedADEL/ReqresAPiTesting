import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.Constants;
import utilities.Requests.LoginUtility;
import utilities.Requests.UserUtils;
import utilities.helper.RequestBody;
import utilities.helper.RequestSpec;

import static utilities.helper.RequestBody.getLoginRequestBody;

public class UserTest {
    private String token;

    @BeforeClass
    public void login() {
        Response response = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        response.prettyPrint();
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
    public void TestUpdateUserdata() {
        SoftAssert softAssert = new SoftAssert();
        String name = "morpheus";
        String job = "zion resident";
        JsonPath jsonPath = UserUtils.UpdateUser(2,token , name,  job  ,200).jsonPath();
        softAssert.assertEquals(jsonPath.getString("name"), name, "name is not correct");
        softAssert.assertEquals(jsonPath.getString("job"), job, "job is not correct");
        softAssert.assertNotNull(jsonPath.getString("updatedAt"), "updatedAt is null");
        softAssert.assertAll();
    }
    @Test
    public void TestUpdateUserWithInvalidId() {
        SoftAssert softAssert = new SoftAssert();
        String name = "morpheus";
        String job = "zion resident";
        JsonPath jsonPath = UserUtils.UpdateUser(23,token , name,  job  ,404).jsonPath();
        softAssert.assertEquals(jsonPath.getString("error"), "Not Found", "error is not correct");
        softAssert.assertAll();
    }
    @Test
    public void TestDeleteUser() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = UserUtils.deleteUser(2, token, 204).jsonPath();
        softAssert.assertAll();
    }



}
