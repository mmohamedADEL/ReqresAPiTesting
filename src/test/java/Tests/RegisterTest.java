package Tests;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import data.Constants;
import utilities.Requests.RegisterUtils;

public class RegisterTest {

    @Test
    public void testValidRegister() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = RegisterUtils.register(Constants.USER_NAME, Constants.PASSWORD, 200).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertNotNull(jsonPath.getString("id"), "ID is null");
        softAssert.assertNotNull(jsonPath.getString("token"), "token is null");
        softAssert.assertAll();
    }
    @Test
    public void testInvalidRegister() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = RegisterUtils.register(Constants.USER_NAME, "", 400).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertEquals(jsonPath.getString("error"), "Missing password", "Missing password");
        softAssert.assertAll();
    }
}
