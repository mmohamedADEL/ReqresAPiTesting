import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.Constants;
import utilities.Requests.LoginUtility;
import utilities.Requests.ResourceUtility;

public class ResourceTest {
    private String token;

    @BeforeClass
    public void login() {
        Response response = LoginUtility.login(Constants.USER_NAME, Constants.PASSWORD, 200);
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        token = "Bearer " + jsonPath.getString("token");
    }
    @Test
    public void TestGetValidSingleResource() {
        SoftAssert softAssert = new SoftAssert();
        int id = 2;
        JsonPath jsonPath = ResourceUtility.getSingleResource(id, 200, token).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertEquals(jsonPath.getInt("data.id"), id, "id is not correct");
        softAssert.assertNotNull(jsonPath.getString("data.name"), "name is null");
        softAssert.assertNotNull(jsonPath.getString("data.pantone_value"), "pantone_value is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertAll();
    }
    @Test
    public void TestGetInvalidSingleResource() {
        SoftAssert softAssert = new SoftAssert();
        int id = 23;
        JsonPath jsonPath = ResourceUtility.getSingleResource(id, 404, token).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertEquals(jsonPath.getString("data"), null, "data is not null");
        softAssert.assertAll();
    }
    @Test
    public void TestGetAllResources() {
        SoftAssert softAssert = new SoftAssert();
        JsonPath jsonPath = ResourceUtility.getAllResources(200, token).jsonPath();
        jsonPath.prettyPrint();
        softAssert.assertNotNull(jsonPath.getList("data"), "data is null");
        softAssert.assertNotNull(jsonPath.getString("support.url"), "url is null");
        softAssert.assertEquals(jsonPath.getInt("data[0].id"), 1, "id is not correct");
        softAssert.assertEquals(jsonPath.getString("data[0].name"), "cerulean", "name first resource is not correct");
        softAssert.assertEquals(jsonPath.getString("data[0].pantone_value"), "15-4020", "pantone_value first resource is not correct");
        softAssert.assertEquals(jsonPath.getInt("per_page"), 6, "per page is not correct");
        softAssert.assertAll();
    }
}
