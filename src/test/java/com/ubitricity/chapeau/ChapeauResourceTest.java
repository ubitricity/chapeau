package com.ubitricity.chapeau;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ChapeauResourceTest {

    private static final String REAL_DEVICE_ID = "suby01000000001";
    private static final String NON_EXISTING_DEVICE_ID = "fakeId0000";

    @Test
    public void checkBootWithRealDeviceId() {
        given()
                .when().post("/api/devices/" + REAL_DEVICE_ID + "/boot")
                .then()
                .statusCode(200)
                .body(is("OK"));
    }

    @Test
    public void throwNotFoundExceptionForBootWithNonExistingDeviceId() {
        given()
                .when().post("/api/devices/" + NON_EXISTING_DEVICE_ID + "/boot")
                .then()
                .statusCode(404);
    }

    @Test
    public void checkStatusWithRealDeviceId() {
        String realDeviceId = "suby01000000001";
        given()
                .when().post("/api/devices/" + realDeviceId + "/status")
                .then()
                .statusCode(200)
                .body(is("OK"));
    }

    @Test
    public void throwNotFoundExceptionForStatusWithNonExistingDeviceId() {
        String nonExistingDeviceId = "fakeId0000";
        given()
                .when().post("/api/devices/" + nonExistingDeviceId + "/status")
                .then()
                .statusCode(404);
    }

}
