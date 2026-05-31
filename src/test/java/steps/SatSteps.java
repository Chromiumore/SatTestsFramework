package steps;

import base.BaseTest;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;

import static org.junit.jupiter.api.Assertions.*;

public class SatSteps extends BaseTest {
    @Дано("Спутник связи с именем {string}, зарядом {double} и пропускной способностью {double}")
    public static void createCommunicationSatelliteBody(String name, double batteryLevel, double bandwidth) {
        requestBody = createSatelliteData(name, batteryLevel ,bandwidth);
        Allure.addAttachment("Request body", requestBody.toString());
    }

    @Тогда("Проверим, что для спутника тело ответа соответствует телу запроса")
    public static void checkResponseBody() {
        JsonPath json = lastResponse.jsonPath();
        assertEquals(requestBody.get("name"), json.getString("name"));
        assertEquals(requestBody.get("batteryLevel"), json.getDouble("batteryLevel"));
        assertEquals(requestBody.get("bandwidth"), json.getDouble("bandwidth"));
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }
}
