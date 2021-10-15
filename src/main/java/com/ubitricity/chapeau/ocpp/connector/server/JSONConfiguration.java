/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server;

import java.util.HashMap;

public class JSONConfiguration {

    public static final String TCP_NO_DELAY_PARAMETER = "TCP_NO_DELAY";
    public static final String REUSE_ADDR_PARAMETER = "REUSE_ADDR";
    public static final String PROXY_PARAMETER = "PROXY";
    public static final String PING_INTERVAL_PARAMETER = "PING_INTERVAL";
    public static final String USERNAME_PARAMETER = "USERNAME";
    public static final String PASSWORD_PARAMETER = "PASSWORD";

    private final HashMap<String, Object> parameters = new HashMap<>();

    private JSONConfiguration() {
    }

    public static JSONConfiguration get() {
        return new JSONConfiguration();
    }

    public <T> void setParameter(String name, T value) {
        parameters.put(name, value);
    }

    public <T> T getParameter(String name) {
        //noinspection unchecked
        return (T) parameters.get(name);
    }

    public <T> T getParameter(String name, T defaultValue) {
        //noinspection unchecked
        T value = (T) parameters.get(name);
        return value != null ? value : defaultValue;
    }
}
