package com.ubitricity.chapeau.domain;

public class NonExistingDeviceIdException extends Exception {
    public NonExistingDeviceIdException(String deviceId) {
        super("DeviceId = " + deviceId + " is not listed in application properties");
    }
}
