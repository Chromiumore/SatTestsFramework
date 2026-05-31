package steps;

import base.BaseTest;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceOperationSteps extends BaseTest {
    @Тогда("Проверим отчет на содержание группировки")
    public static void checkOverviewHasConstellation() {
        String textResponse = lastResponse.asString();
        assertTrue(textResponse.contains(constellationName));
    }

    @Дано("Запрос на миссию")
    public static void createMissionRequest() {
        requestBody = new HashMap<>();
        requestBody.put("targetType", "CONSTELLATION");
        requestBody.put("constellationName", constellationName);
    }

    @Когда("Отправим DELETE-запрос на удаление спутника из группировки")
    public static void deleteSatelliteFromConstellation() {
        lastResponse = apiClient.delete("/space-operation/constellations/" + constellationName + "/satellites/" + satelliteName);
    }
}
