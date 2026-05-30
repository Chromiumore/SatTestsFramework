package com.github.chromiumore.satsystem.autotest.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {
    public Response get(String endpoint) {
        return given()
                .log().all()
                .then()
                .log().all()
                .when()
                .get(endpoint);
    }

    public Response post(
            String endpoint,
            Map<String, Object> body
    ) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .then()
                .log().all()
                .when()
                .post(endpoint);
    }

    public Response put(
            String endpoint,
            Map<String, Object> body
    ) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .then()
                .log().all()
                .when()
                .put(endpoint);
    }

    public Response delete(
            String endpoint
    ) {
        return given()
                .log().all()
                .then()
                .log().all()
                .when()
                .delete(endpoint);
    }
}
