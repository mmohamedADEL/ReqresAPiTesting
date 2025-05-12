package Tests;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.Requests.DelayedResponse;

public class DelayedResponseTest {
    @Test
    public void testDelayedResponse() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = DelayedResponse.getDelayedResponse(3,200).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertNotNull(jsonPath.getString("data"), "data is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertAll();
    }
}
