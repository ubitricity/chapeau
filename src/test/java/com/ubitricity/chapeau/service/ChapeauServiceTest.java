package com.ubitricity.chapeau.service;

import com.ubitricity.chapeau.domain.NonExistingDeviceIdException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
class ChapeauServiceTest {
    private static final String REAL_DEVICE_ID = "suby01000000001";
    private static final String NON_EXISTING_DEVICE_ID = "fake-00001";

    @Inject
    ChapeauService chapeauService;

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
        Assertions.assertDoesNotThrow(() -> chapeauService.subscribeOnStatus(REAL_DEVICE_ID));
    }

    @Test
    void checkFailedStatus() {
        Assertions.assertThrows(
                NonExistingDeviceIdException.class, () -> chapeauService.subscribeOnStatus(NON_EXISTING_DEVICE_ID)
                );
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
