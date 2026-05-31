package base;

import com.github.chromiumore.satsystem.autotest.client.ApiClient;
import com.github.chromiumore.satsystem.autotest.config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;

public class Hooks extends BaseTest {
    private Scenario scenario;

    @Before
    public void tearup(Scenario scenario) {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        apiClient = new ApiClient();

        Allure.parameter("Scenario", scenario.getName());
    }

    @After
    public void attachResponse(Scenario scenario) {
        if (lastResponse != null) {
            String body = lastResponse.getBody().asString();
            Allure.addAttachment(
                    "Response body",
                    "application/json",
                    body,
                    "json"
            );

            Allure.step("Status code " + lastResponse.getStatusCode());
        }
    }
}
