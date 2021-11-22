/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RegisterForReflection
public final class ModelConstants {

    public static final int STRING_20_CHAR_MAX_LENGTH = 20;
    public static final int STRING_25_CHAR_MAX_LENGTH = 25;
    public static final int STRING_50_CHAR_MAX_LENGTH = 50;
    public static final int STRING_255_CHAR_MAX_LENGTH = 255;
    public static final int STRING_500_CHAR_MAX_LENGTH = 500;

    public static final int LIST_VERSION_NEGATIVE_MIN_VALUE = -1;
    public static final int LIST_VERSION_POSITIVE_MIN_VALUE = 1;
    public static final int CONNECTOR_ID_MIN_VALUE = 0;
    public static final int STACK_LEVEL_MIN_VALUE = 0;
    public static final int INTERVAL_MIN_VALUE = 0;

    public static final long ONE_DAY_EXPIRY = 1;
}
