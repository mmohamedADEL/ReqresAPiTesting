import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.Constants;
import utilities.Requests.LoginUtility;
import utilities.Requests.RegisterUtils;
import utilities.Requests.UserUtils;
import utilities.helper.DataUtility;

public class E2ETests {
    @Test(description = "Validate login , get user , update user ,get same user")
    public void testONE_E2E() {
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");


        // Step 2: Get user
        int userId = 7;
        Response getUserResponse = UserUtils.getSingleUser(userId, 200, token);
        JsonPath getUserJsonPath = getUserResponse.jsonPath();
        //getUserResponse.prettyPrint();
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");

        // Step 3: Update user
        User user = new User(
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"name"),
                DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"job"));
        JsonPath updateUserJsonPath  = UserUtils.UpdateUser(userId, token, user, 200).jsonPath();
        //updateUserResponse.prettyPrint();
        softAssert.assertEquals(updateUserJsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(updateUserJsonPath.getString("job"), user.getJob(), "job is not correct");

        // Step 4: Get the same user again
        JsonPath getUpdatedUserJson = UserUtils.getSingleUser(userId, 200, token).jsonPath();

        // Assertions can be added here to validate the responses
        softAssert.assertEquals(getUpdatedUserJson.getString("data.first_name"), user.getName().split(" ")[0], "updated first name is not correct");
        softAssert.assertEquals(getUpdatedUserJson.getString("data.last_name"), user.getName().split(" ")[1], "updated last name is not correct");
        softAssert.assertAll();
    }

    @Test(description = "Validate Register user , create User ,get user by id")
    public void Test_Two_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Register user
        JsonPath registerJsonPath = RegisterUtils.register(Constants.USER_NAME,Constants.PASSWORD, 200).jsonPath();
        softAssert.assertNotNull(registerJsonPath.getString("id"), "ID is null");
        softAssert.assertNotNull(registerJsonPath.getString("token"), "token is null");
        String token = "Bearer " + registerJsonPath.getString("token");
        // Step 2: Create user
        User user = new User(
                DataUtility.getJsonData("TestData","CreateUserData" ,"name"),
                DataUtility.getJsonData("TestData","CreateUserData" ,"job")
        );
        JsonPath createUserJsonPath = UserUtils.createUser(token , user , 201) .jsonPath();
        softAssert.assertNotNull(createUserJsonPath.getString("id"), "ID is null");
        softAssert.assertEquals(createUserJsonPath.getString("name"), user.getName(), "name is Not correct");
        softAssert.assertEquals(createUserJsonPath.getString("job"), user.getJob(), "job is Not correct");
        // Step 3: Get user by id
        int userId = createUserJsonPath.getInt("id");
        JsonPath getUserJsonPath = UserUtils.getSingleUser(userId, 200, token).jsonPath();
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");
        softAssert.assertEquals(getUserJsonPath.getString("data.first_name"), user.getName().split(" ")[0], "first name is not correct");
        softAssert.assertEquals(getUserJsonPath.getString("data.last_name"), user.getName().split(" ")[1], "last name is not correct");
        // Step 4: Assert ALL
        softAssert.assertAll();
    }
    @Test(description = "Validate login , get user , delete user , get deleted user")
    public void Test_Three_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");
        // Step 2: Get user
        int userId = 7;
        Response getUserResponse = UserUtils.getSingleUser(userId, 200, token);
        JsonPath getUserJsonPath = getUserResponse.jsonPath();
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");
        // Step 3: Delete user
        JsonPath DeleteUserJsonPath =  UserUtils.deleteUser(userId, token, 204).jsonPath();
        softAssert.assertNull(DeleteUserJsonPath, "data is not null");
        // Step 4: Try to get the deleted user
        JsonPath getDeletedUserJsonPath = UserUtils.getSingleUser(userId, 404, token).jsonPath();
        // Step 5: Assert ALL
        softAssert.assertAll();
    }
    @Test(description = "Validate login , create user , update user , delete user")
    public void Test_Four_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");
        // Step 2: Create user
        User user =  new User(
                DataUtility.getJsonData("TestData","CreateUserData" ,"name"),
                DataUtility.getJsonData("TestData","CreateUserData" ,"job")
        );
        JsonPath createUserJsonPath = UserUtils.createUser(token , user , 201) .jsonPath();
        softAssert.assertNotNull(createUserJsonPath.getString("id"), "ID is null");
        softAssert.assertEquals(createUserJsonPath.getString("name"), user.getName(), "name is Not correct");
        softAssert.assertEquals(createUserJsonPath.getString("job"), user.getJob(), "job is Not correct");
        // Step 3: Update user
        int userId = createUserJsonPath.getInt("id");
        user.setJob(DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"job"));
        user.setName(DataUtility.getJsonData("TestData" ,"UpdateUserData" ,"name"));
        Response updateUserResponse = UserUtils.UpdateUser(userId, token,user, 200);
        JsonPath updateUserJsonPath = updateUserResponse.jsonPath();
        softAssert.assertEquals(updateUserJsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(updateUserJsonPath.getString("job"), user.getJob(), "job is not correct");
        // Step 4: Delete user
        Response DeleteUserJsonPath =  UserUtils.deleteUser(userId, token, 204);
        // Step 5: Assert ALL
        softAssert.assertAll();
    }

}
