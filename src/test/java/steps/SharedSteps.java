package steps;

import base.BaseTest;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;

import static com.github.chromiumore.satsystem.autotest.specs.ResponseSpecification.expectedStatusCode;
import static org.junit.jupiter.api.Assertions.*;

public class SharedSteps extends BaseTest {

    @Когда("Отправим GET запрос для метода {string}")
    public static void sendGetRequest(String endpoint) {
        lastResponse = apiClient.get(endpoint);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим POST запрос для метода {string}")
    public static void sendPostRequest(String endpoint) {
        lastResponse = apiClient.post(endpoint, requestBody);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим GET запрос с id последнего ответа для метода {string}")
    public static void sendGetRequestWithId(String endpoint) {
        requestId = lastResponse.jsonPath().getLong("id");
        lastResponse = apiClient.get(endpoint + "/" + requestId);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим PUT запрос с id последнего ответа для метода {string}")
    public static void sendPutRequestWithId(String endpoint) {
        lastResponse = apiClient.put(endpoint + "/" + requestId, requestBody);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим POST запрос с id последнего ответа для метода {string}")
    public static void sendPostRequestWithId(String endpoint) {
        lastResponse = apiClient.post(endpoint + "/" + requestId, requestBody);
        Allure.addAttachment("Response body", "application/json", lastResponse.asString());
    }

    @Когда("Отправим DELETE запрос с id последнего ответа для метода {string}")
    public static void sendDeleteRequestWithId(String endpoint) {
        lastResponse = apiClient.delete(endpoint + "/" + requestId);
    }

    @Тогда("Проверим, что в ответе пришел статус-код = {word}")
    public static void checkStatusCode(String code) {
        lastResponse.then().spec(expectedStatusCode(Integer.parseInt(code)));
        Allure.addAttachment("Checked status", "text/plain", "Expected " + code, ", actual " + lastResponse.getStatusCode());
    }

    @Тогда("Проверим ответ, что id совпадают")
    public static void checkId() {
        assertEquals(requestId, lastResponse.jsonPath().getLong("id"));
    }
}
