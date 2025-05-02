package utilities.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utilities.Constants;

public class RequestSpec {
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URL)
                .addHeader(Constants.API_KEY_HEADER , Constants.API_KEY_VALUE)
                .setContentType("application/json")
                .build();
    }
}
