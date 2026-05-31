package base;

import com.github.chromiumore.satsystem.autotest.client.ApiClient;
import com.github.chromiumore.satsystem.autotest.config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.UUID;

public class Hooks extends BaseTest {
    private Scenario scenario;

    @Before(order = 0)
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

    @Before("@first-in-constellation")
    public void beforeConstellationScenario(Scenario scenario) {
        requestBody = createSatelliteData(
                "Test-1",
                0.55,
                2500
        );
        lastResponse = apiClient.post("/satellites", requestBody);
        createdSatId = lastResponse.jsonPath().getLong("id");
        createdEnergyId = lastResponse.jsonPath().getLong("energy.id");
        requestId = createdEnergyId;
    }


    @After("@constellation-create")
    public void initConstellationId(Scenario scenario) {
        createdConstellationId = lastResponse.jsonPath().getLong("id");
        requestId = createdConstellationId;
    }

    @Before("@first-in-operation")
    public void beforeSpaceOperationScenario(Scenario scenario) {
        requestBody = createSatelliteData(
                "Test-1",
                0.55,
                2500
        );
        lastResponse = apiClient.post("/satellites", requestBody);
        createdSatId = lastResponse.jsonPath().getLong("id");
        createdEnergyId = lastResponse.jsonPath().getLong("energy.id");
        requestId = createdEnergyId;
        satelliteName = lastResponse.jsonPath().getString("name");

        requestBody = new HashMap<>();
        requestBody.put("name", UUID.randomUUID().toString());
        lastResponse = apiClient.post("/constellations", requestBody);
        createdConstellationId = lastResponse.jsonPath().getLong("id");
        constellationName = lastResponse.jsonPath().getString("constellationName");

        requestBody = new HashMap<>();
        requestBody.put("satelliteId", createdSatId);
        lastResponse = apiClient.post("/constellations/" + createdConstellationId, requestBody);
    }

    @After("@last-in-operation or @last-in-constellation")
    public void afterSpaceOperationScenario(Scenario scenario) {
        lastResponse = apiClient.delete("/satellites/" + createdSatId);
        lastResponse = apiClient.delete("/constellations/" + createdConstellationId);
    }
}
