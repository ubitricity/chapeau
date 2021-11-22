/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.service;

import com.ubitricity.chapeau.domain.NonExistingDeviceIdException;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.ChargePointStatus;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.Reason;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums.UnitOfMeasure;
import com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Collections;

@QuarkusTest
class ChapeauServiceTest {
    private static final String REAL_DEVICE_ID = "suby0100000001";
    private static final String NON_EXISTING_DEVICE_ID = "fake-00001";

    @Inject
    ChapeauService chapeauService;

    ChangeChargingStatusRequest buildChangeChargingStatusRequest() {
        return new ChangeChargingStatusRequest(1, ChargePointStatus.Charging);
    }

    @Test
    void checkSuccessfulBoot() {
        Assertions.assertDoesNotThrow(() -> chapeauService.subscribeOnBoot(REAL_DEVICE_ID));
    }

    @Test
    void checkFailedBoot() {
        Assertions.assertThrows(
                NonExistingDeviceIdException.class, () -> chapeauService.subscribeOnBoot(NON_EXISTING_DEVICE_ID)
        );
    }

    @Test
    void checkSuccessfulStatus() {

        Assertions.assertDoesNotThrow(() -> chapeauService.subscribeOnStatus(REAL_DEVICE_ID, buildChangeChargingStatusRequest()));
    }

    @Test
    void checkFailedStatus() {
        Assertions.assertThrows(
                NonExistingDeviceIdException.class, () -> chapeauService.subscribeOnStatus(NON_EXISTING_DEVICE_ID, buildChangeChargingStatusRequest())
        );
    }

    @Test
    void checkSuccessSubcribeOnStartTransaction(){
        StartTransactionRequest request = new StartTransactionRequest(1, "idTag", 100, 12345, ZonedDateTime.now());
        Assertions.assertDoesNotThrow(() -> chapeauService.subscribeOnStartTransaction(REAL_DEVICE_ID, request));

    }

    @Test
    void checkFailedSubcribeOnStartTransaction(){
        StartTransactionRequest request = new StartTransactionRequest(1, "idTag", 100, 12345, ZonedDateTime.now());
        Assertions.assertThrows(
                NonExistingDeviceIdException.class, () -> chapeauService.subscribeOnStartTransaction(NON_EXISTING_DEVICE_ID, request)
        );
    }

    @Test
    void checkFailedSubscribeOnStopTransaction() {
        MeterValue transactionData = new MeterValue();
        SampledValue sampledValue = new SampledValue();
        sampledValue.setValue("200");
        sampledValue.setUnit(UnitOfMeasure.WH);
        transactionData.setSampledValue(Collections.singletonList(sampledValue));
        transactionData.setTimestamp(ZonedDateTime.now());

        StopTransactionRequest stopTransactionRequest =
                new StopTransactionRequest("idTag", 300,
                        ZonedDateTime.now(), 1, Reason.EVDisconnected, Collections.singletonList(transactionData));
        Assertions.assertThrows(Exception.class, () -> chapeauService.subscribeOnStopTransaction(REAL_DEVICE_ID, stopTransactionRequest));
    }

    /*@Test
    void checkStartTransaction() {
        Assertions.assertTrue(chapeauService.subscribeOnStartTransaction(REAL_DEVICE_ID));
    }

    @Test
    void checkStopTransaction() {
        Assertions.assertTrue(chapeauService.subscribeOnStopTransaction(REAL_DEVICE_ID));
    }

    @Test
    void checkAuthorization() {
        Assertions.assertTrue(chapeauService.subscribeOnAutorization(REAL_DEVICE_ID));
    }*/

}
