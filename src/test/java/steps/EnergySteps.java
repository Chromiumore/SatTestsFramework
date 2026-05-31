package steps;

import base.BaseTest;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergySteps extends BaseTest {
    @Дано("Создание новой энергетической системы")
    public static void createSatelliteAndEnergySystem() {
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

    @Дано("Энергетическая система с batteryLevel={double}, lowBatteryThreshold={double}, minBattery={double}, maxBattery={double}")
    public static void createEnergySystemBody(double batteryLevel, double lowBatteryThreshold, double minBattery, double maxBattery) {
        requestBody = new HashMap<>();
        requestBody.put("batteryLevel", batteryLevel);
        requestBody.put("lowBatteryThreshold", lowBatteryThreshold);
        requestBody.put("minBattery", minBattery);
        requestBody.put("maxBattery", maxBattery);
    }

    @Тогда("Проверим, что для энергетической системы тело ответа соответствует телу запроса")
    public static void checkEnergyResponseBody() {
        JsonPath json = lastResponse.jsonPath();
        assertEquals(requestBody.get("batteryLevel"), json.getDouble("batteryLevel"));
        assertEquals(requestBody.get("lowBatteryThreshold"), json.getDouble("lowBatteryThreshold"));
        assertEquals(requestBody.get("minBattery"), json.getDouble("minBattery"));
        assertEquals(requestBody.get("maxBattery"), json.getDouble("maxBattery"));
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Дано("Удаление спутника с энергетической системой")
    public static void deleteSatelliteAndEnergySystem() {
        lastResponse = apiClient.delete("/satellites/" + createdSatId);
    }
}
