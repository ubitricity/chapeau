package com.ubitricity.chapeau;

import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointStatus;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.Reason;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.UnitOfMeasure;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Collections;

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
    public void checkChangedChargingStatusWithRealDeviceId() {
        String realDeviceId = "suby01000000001";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildChangeChargingStatusRequest()).when().post("/api/devices/" + realDeviceId + "/changeStatus")
                .then()
                .statusCode(200)
                .body(is("OK"));
    }

    @Test
    public void throwNotFoundExceptionForChangedChargingStatusWithNonExistingDeviceId() {
        String nonExistingDeviceId = "fakeId0000";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildChangeChargingStatusRequest()).when().post("/api/devices/" + nonExistingDeviceId + "/changeStatus")
                .then()
                .statusCode(404);
    }

    ChangeChargingStatusRequest buildChangeChargingStatusRequest() {
        return new ChangeChargingStatusRequest(1, ChargePointStatus.Charging);
    }

    @Test
    public void checkStartTransactionWithRealDeviceId() {
        String realDeviceId = "suby01000000001";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildStartTransactionRequest()).when().post("/api/devices/" + realDeviceId + "/startTransaction")
                .then()
                .statusCode(200)
                .body(is("OK"));
    }

    @Test
    public void throwNotFoundExceptionForStartTransactionWithNonExistingDeviceId() {
        String nonExistingDeviceId = "fakeId0000";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildStartTransactionRequest()).when().post("/api/devices/" + nonExistingDeviceId + "/startTransaction")
                .then()
                .statusCode(404);
    }

    StartTransactionRequest buildStartTransactionRequest(){
        return new StartTransactionRequest(1, "idTag1", 100, 12345, ZonedDateTime.now());

    }

    @Test
    public void checkStopTransactionWithRealDeviceId() {
        String realDeviceId = "suby01000000001";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildStopTransactionRequest()).when().post("/api/devices/" + realDeviceId + "/stopTransaction")
                .then()
                .statusCode(500);
    }

    @Test
    public void throwNotFoundExceptionForStopTransactionWithNonExistingDeviceId() {
        String nonExistingDeviceId = "fakeId0000";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(buildStopTransactionRequest()).when().post("/api/devices/" + nonExistingDeviceId + "/stopTransaction")
                .then()
                .statusCode(404);
    }

    StopTransactionRequest buildStopTransactionRequest(){
        MeterValue transactionData = new MeterValue();
        SampledValue sampledValue = new SampledValue();
        sampledValue.setValue("200");
        sampledValue.setUnit(UnitOfMeasure.WH);
        transactionData.setSampledValue(Collections.singletonList(sampledValue));
        transactionData.setTimestamp(ZonedDateTime.now());

        return
                new StopTransactionRequest("idTag1", 300,
                        ZonedDateTime.now(), 1, Reason.EVDisconnected, Collections.singletonList(transactionData));
    }
}
