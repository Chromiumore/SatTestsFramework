package com.github.chromiumore.satsystem.autotest.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

public class ResponseSpecification {
    public static io.restassured.specification.ResponseSpecification expectedStatusCode(int code) {
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .build();
    }
}
