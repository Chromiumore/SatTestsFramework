package base;

import com.github.chromiumore.satsystem.autotest.client.ApiClient;
import com.github.chromiumore.satsystem.autotest.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    public static ApiClient apiClient;
    protected static Response lastResponse;
    protected static Map<String, Object> requestBody;
    protected static Long requestId;
    protected static Long createdSatId;
    protected static Long createdEnergyId;
    protected static Long createdConstellationId;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        apiClient = new ApiClient();
    }

    protected static Map<String, Object> createSatelliteData(
            String name,
            double batteryLevel,
            double bandwidth
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("batteryLevel", batteryLevel);
        body.put("bandwidth", bandwidth);
        body.put("type", "COMMUNICATION");

        return body;
    }
}
