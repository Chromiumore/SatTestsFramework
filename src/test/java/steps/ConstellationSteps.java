package steps;

import base.BaseTest;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstellationSteps extends BaseTest {
    @Дано("Группировка спутников с name={string}")
    public static void createConstellationBody(String name) {
        requestBody = new HashMap<>();
        requestBody.put("name", name);
    }

    @Тогда("Проверим данные группировки в ответе")
    public static void checkConstellationResponseBody() {
        JsonPath json = lastResponse.jsonPath();
        assertEquals(requestBody.get("name"), json.getString("constellationName"));
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим GET запрос с именем группировки для метода {string}")
    public static void sendGetRequestWithConstName(String endpoint) {
        String name = lastResponse.jsonPath().getString("constellationName");
        lastResponse = apiClient.get(endpoint + "/name/" + name);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Добавим спутник в группировку для метода {string}")
    public static void sendPostRequestWithConstellationAndSatelliteIds(String endpoint) {
        requestBody = new HashMap<>();
        requestBody.put("satelliteId", createdSatId);
        lastResponse = apiClient.post(endpoint + "/" + createdConstellationId, requestBody);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Тогда("Проверим, что группировка содержит спутник")
    public static void checkConstellationHasSatellite() {
        lastResponse = apiClient.get("/constellations/" + createdConstellationId);
        List<Map<String, Object>> satList = lastResponse.jsonPath().getList("satellites");
        String satId = satList.getFirst().get("id").toString();
        assertEquals(requestBody.get("satelliteId").toString(), satId);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }
}
