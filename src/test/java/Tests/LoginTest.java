package Tests;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import data.Constants;
import utilities.Requests.LoginUtility;

public class LoginTest {

    @Test
    public void testLogin() {
        JsonPath kk =  LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200).jsonPath();
        Assert.assertNotNull(kk.get("token"), "Token should not be null");
    }
    @Test
    public void testLoginWithInvalidCredentials() {
        JsonPath jsonPath =  LoginUtility.login(Constants.USER_NAME, "", 400).jsonPath();
        Assert.assertEquals(jsonPath.getString("error"), "Missing password", "Missing password");
    }
}
