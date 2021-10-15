package com.ubitricity.chapeau.domain;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;
import java.util.Set;

@ConfigMapping(prefix = "devices")
public interface Devices {
    @WithName("device")
    Set<Device> all();

    default Optional<Device> findWithId(String deviceId) {
        return all().stream().filter(device -> device.deviceId().equals(deviceId)).findFirst();
    }

    interface Device {
        @NotEmpty
        String deviceId();
        @NotEmpty
        String authorizationCode();

        @WithDefault("false")
        boolean checkAvailability();
    }
}
