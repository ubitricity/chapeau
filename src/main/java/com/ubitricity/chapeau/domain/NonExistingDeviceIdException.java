/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.domain;

public class NonExistingDeviceIdException extends Exception {
    public NonExistingDeviceIdException(String deviceId) {
        super("DeviceId = " + deviceId + " is not listed in application properties");
    }
}
