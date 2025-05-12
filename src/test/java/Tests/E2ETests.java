package Tests;

import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Enabled;
import models.User;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import data.Constants;
import utilities.Requests.LoginUtility;
import utilities.Requests.RegisterUtils;
import utilities.Requests.ResourceUtility;
import utilities.Requests.UserUtils;
import utilities.helper.DataUtility;
import Listeners.TestListenerClass;
import utilities.helper.LogUtil;

@Listeners({TestListenerClass.class})
public class E2ETests {
    @Description("This is a test to validate the end-to-end flow of login, user creation, and user retrieval.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Feature("E2E Testing")
    @Test(description = "Validate login , get user , update user ,get same user")
    public void Test_ONE_E2E() {
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        LogUtil.info("Login request body: name " + Constants.USER_NAME + " password " + Constants.PASSWORD);
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");
        LogUtil.info("Token: " + token);


        // Step 2: Get user
        int userId = 7;
        Response getUserResponse = UserUtils.getSingleUser(userId, 200, token);
        JsonPath getUserJsonPath = getUserResponse.jsonPath();
        //getUserResponse.prettyPrint();
        LogUtil.info("Get user request body: id " + userId);
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");
        LogUtil.info("step 2: Get user id: " + getUserJsonPath.getInt("data.id"));
        // Step 3: Update user
        User user = new User("mohammed ali", "Software Engineer");
        JsonPath updateUserJsonPath  = UserUtils.UpdateUser(userId, token, user, 200).jsonPath();
        LogUtil.info("Update user request body: id " + userId + " name " + user.getName() + " job " + user.getJob());
        softAssert.assertEquals(updateUserJsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(updateUserJsonPath.getString("job"), user.getJob(), "job is not correct");
        LogUtil.info("step 3 update user name and job has been done");

        // Step 4: Get the same user again
        JsonPath getUpdatedUserJson = UserUtils.getSingleUser(userId, 200, token).jsonPath();
        LogUtil.info("Get updated user request body: id " + userId);
        // Assertions can be added here to validate the responses
        softAssert.assertEquals(getUpdatedUserJson.getString("data.first_name"), user.getName().split(" ")[0], "updated first name is not correct");
        softAssert.assertEquals(getUpdatedUserJson.getString("data.last_name"), user.getName().split(" ")[1], "updated last name is not correct");
        softAssert.assertAll();
    }
    @Description("This is a test to validate the end-to-end flow of user registration, user creation, and user retrieval.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Feature("E2E Testing")
    @Test(description = "Validate Register user , create User ,get user by id")
    public void Test_Two_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Register user
        JsonPath registerJsonPath = RegisterUtils.register(Constants.USER_NAME,Constants.PASSWORD, 200).jsonPath();
        LogUtil.info("Register user request body: name " + Constants.USER_NAME + " password " + Constants.PASSWORD);
        softAssert.assertNotNull(registerJsonPath.getString("id"), "ID is null");
        softAssert.assertNotNull(registerJsonPath.getString("token"), "token is null");
        String token = "Bearer " + registerJsonPath.getString("token");
        LogUtil.info("Step one done ,Token: " + token);
        // Step 2: Create user
        User user = new User(
                DataUtility.getJsonData("TestData","CreateUserData" ,"name"),
                DataUtility.getJsonData("TestData","CreateUserData" ,"job")
        );
        LogUtil.info("Create user request body: name " + user.getName() + " job " + user.getJob());
        JsonPath createUserJsonPath = UserUtils.createUser(token , user , 201) .jsonPath();
        softAssert.assertNotNull(createUserJsonPath.getString("id"), "ID is null");
        softAssert.assertEquals(createUserJsonPath.getString("name"), user.getName(), "name is Not correct");
        softAssert.assertEquals(createUserJsonPath.getString("job"), user.getJob(), "job is Not correct");
        LogUtil.info("Step two done , user id: " + createUserJsonPath.getInt("id"));
        // Step 3: Get user by id
        int userId = createUserJsonPath.getInt("id");
        JsonPath getUserJsonPath = UserUtils.getSingleUser(userId, 200, token).jsonPath();
        LogUtil.info("Get user request body: id " + userId);
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");
        softAssert.assertEquals(getUserJsonPath.getString("data.first_name"), user.getName().split(" ")[0], "first name is not correct");
        softAssert.assertEquals(getUserJsonPath.getString("data.last_name"), user.getName().split(" ")[1], "last name is not correct");
        LogUtil.info("Step three done , user id: " + getUserJsonPath.getInt("data.id"));
        // Step 4: Assert ALL
        softAssert.assertAll();
    }
    @Description("This is a test to validate the end-to-end flow of login, user deletion, and user retrieval.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Test(description = "Validate login , get user , delete user , get deleted user")
    public void Test_Three_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        LogUtil.info("Step 1: Login successful.");
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");

        // Step 2: Get user
        int userId = 7;
        Response getUserResponse = UserUtils.getSingleUser(userId, 200, token);
        LogUtil.info("Step 2: Retrieved user details successfully.");
        JsonPath getUserJsonPath = getUserResponse.jsonPath();
        softAssert.assertEquals(getUserJsonPath.getInt("data.id"), userId, "id is not correct");

        // Step 3: Delete user
        JsonPath DeleteUserJsonPath = UserUtils.deleteUser(userId, token, 204).jsonPath();
        LogUtil.info("Step 3: User deleted successfully.");
        softAssert.assertNull(DeleteUserJsonPath, "data is not null");

        // Step 4: Try to get the deleted user
        JsonPath getDeletedUserJsonPath = UserUtils.getSingleUser(userId, 404, token).jsonPath();
        LogUtil.info("Step 4: Verified that the deleted user cannot be retrieved.");

        // Step 5: Assert ALL
        softAssert.assertAll();
    }
    @Description("This is a test to validate the end-to-end flow of login, user creation, user update, and user deletion.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Test(description = "Validate login , create user , update user , delete user")
    public void Test_Four_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        LogUtil.info("Step 1: Login successful.");
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");

        // Step 2: Create user
        User user = new User(
                DataUtility.getJsonData("TestData", "CreateUserData", "name"),
                DataUtility.getJsonData("TestData", "CreateUserData", "job")
        );
        LogUtil.info("Step 2: User creation request sent.");
        JsonPath createUserJsonPath = UserUtils.createUser(token, user, 201).jsonPath();
        softAssert.assertNotNull(createUserJsonPath.getString("id"), "ID is null");
        softAssert.assertEquals(createUserJsonPath.getString("name"), user.getName(), "name is Not correct");
        softAssert.assertEquals(createUserJsonPath.getString("job"), user.getJob(), "job is Not correct");
        LogUtil.info("Step 2: User created successfully.");

        // Step 3: Update user
        int userId = createUserJsonPath.getInt("id");
        user.setJob(DataUtility.getJsonData("TestData", "UpdateUserData", "job"));
        user.setName(DataUtility.getJsonData("TestData", "UpdateUserData", "name"));
        Response updateUserResponse = UserUtils.UpdateUser(userId, token, user, 200);
        LogUtil.info("Step 3: User update request sent.");
        JsonPath updateUserJsonPath = updateUserResponse.jsonPath();
        softAssert.assertEquals(updateUserJsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(updateUserJsonPath.getString("job"), user.getJob(), "job is not correct");
        LogUtil.info("Step 3: User updated successfully.");

        // Step 4: Delete user
        Response DeleteUserJsonPath = UserUtils.deleteUser(userId, token, 204);
        LogUtil.info("Step 4: User deletion request sent and completed.");

        // Step 5: Assert ALL
        softAssert.assertAll();
    }
    @Description("This is a test to validate the end-to-end flow of login, resource retrieval, and resource validation.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Feature("resource retrieval")
    @Test (description = "Validate login , get single resource  , get all resource , check single resource in all resources")
    public void Test_Five_E2E(){
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        LogUtil.info("Step 1: Login successful.");
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");

        // Step 2: Get single resource
        int resourceId = 2;
        Response getResourceResponse = ResourceUtility.getSingleResource(resourceId, 200, token);
        LogUtil.info("Step 2: Single resource retrieval request sent.");
        JsonPath getResourceJsonPath = getResourceResponse.jsonPath();
        softAssert.assertEquals(getResourceJsonPath.getInt("data.id"), resourceId, "id is not correct");
        LogUtil.info("Step 2: Single resource retrieved successfully.");

        // Step 3: Get all resources
        Response getAllResourcesResponse = ResourceUtility.getAllResources(200, token);
        LogUtil.info("Step 3: All resources retrieval request sent.");
        JsonPath getAllResourcesJsonPath = getAllResourcesResponse.jsonPath();
        softAssert.assertNotNull(getAllResourcesJsonPath.getList("data"), "data is null");
        softAssert.assertNotNull(getAllResourcesJsonPath.getString("support.url"), "url is null");
        softAssert.assertEquals(getAllResourcesJsonPath.getInt("data[0].id"), 1, "id is not correct");
        softAssert.assertEquals(getAllResourcesJsonPath.getString("data[0].name"), "cerulean", "name first resource is not correct");
        softAssert.assertEquals(getAllResourcesJsonPath.getInt("data[1].id"), resourceId, "id is not correct");
        softAssert.assertEquals(getAllResourcesJsonPath.getString("data[1].name"), getResourceJsonPath.getString("data.name"), "name first resource is not correct");
        softAssert.assertEquals(getAllResourcesJsonPath.getInt("data[1].year"), getResourceJsonPath.getInt("data.year"), "year first resource is not correct");
        LogUtil.info("Step 3: All resources retrieved successfully.");

        // Step 4: Assert ALL
        softAssert.assertAll();
    }

    @Description("This is a test to validate the end-to-end flow of login, user creation, retrieving all users, and verifying the created user exists in the list.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Adel")
    @Feature("E2E Testing")
    @Test(description = "Validate login, create user, get all users, and verify created user in the list")
    public void Test_Six_E2E() {
        SoftAssert softAssert = new SoftAssert();

        // Step 1: Login
        Response loginResponse = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        LogUtil.info("Step 1: Login successful.");
        JsonPath loginJsonPath = loginResponse.jsonPath();
        String token = "Bearer " + loginJsonPath.getString("token");

        // Step 2: Create user
        User user = new User(
                DataUtility.getJsonData("TestData", "CreateUserData", "name"),
                DataUtility.getJsonData("TestData", "CreateUserData", "job")
        );
        LogUtil.info("Step 2: User creation request sent.");
        JsonPath createUserJsonPath = UserUtils.createUser(token, user, 201).jsonPath();
        int userId = createUserJsonPath.getInt("id");
        softAssert.assertNotNull(userId, "ID is null");
        softAssert.assertEquals(createUserJsonPath.getString("name"), user.getName(), "name is not correct");
        softAssert.assertEquals(createUserJsonPath.getString("job"), user.getJob(), "job is not correct");
        LogUtil.info("Step 2: User created successfully with ID: " + userId);

        // Step 3: Get all users
        Response getAllUsersResponse = UserUtils.getAllUsersForPage (2,200, token);
        LogUtil.info("Step 3: All users retrieval request sent.");
        JsonPath getAllUsersJsonPath = getAllUsersResponse.jsonPath();
        softAssert.assertNotNull(getAllUsersJsonPath.getList("data"), "data is null");
        LogUtil.info("Step 3: All users retrieved successfully.");

        // Step 4: Verify created user exists in the list
        boolean userExists = getAllUsersJsonPath.getList("data.id").contains(userId);
        softAssert.assertTrue(userExists, "Created user does not exist in the list of all users.");
        LogUtil.info("Step 4: Verified that the created user exists in the list of all users.");

        // Step 5: Assert ALL
        softAssert.assertAll();
    }

}
