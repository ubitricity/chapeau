/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import eu.chargetime.ocpp.Communicator;
import eu.chargetime.ocpp.Radio;
import eu.chargetime.ocpp.model.CallErrorMessage;
import eu.chargetime.ocpp.model.CallMessage;
import eu.chargetime.ocpp.model.CallResultMessage;
import eu.chargetime.ocpp.model.Message;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

@Slf4j
public class JSONCommunicator extends Communicator {
    private static final int INDEX_MESSAGEID = 0;
    private static final int TYPENUMBER_CALL = 2;
    private static final int INDEX_CALL_ACTION = 2;
    private static final int INDEX_CALL_PAYLOAD = 3;

    private static final int TYPENUMBER_CALLRESULT = 3;
    private static final int INDEX_CALLRESULT_PAYLOAD = 2;

    private static final int TYPENUMBER_CALLERROR = 4;
    private static final int INDEX_CALLERROR_ERRORCODE = 2;
    private static final int INDEX_CALLERROR_DESCRIPTION = 3;
    private static final int INDEX_CALLERROR_PAYLOAD = 4;

    private static final int INDEX_UNIQUEID = 1;
    private static final String CALL_FORMAT = "[2,\"%s\",\"%s\",%s]";
    private static final String CALLRESULT_FORMAT = "[3,\"%s\",%s]";
    private static final String CALLERROR_FORMAT = "[4,\"%s\",\"%s\",\"%s\",%s]";

    /**
     * Handle required injections.
     *
     * @param radio instance of the {@link Radio}.
     */
    public JSONCommunicator(Radio radio) {
        super(radio);
    }

    @Override
    public <T> T unpackPayload(Object payload, Class<T> type) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(payload.toString(), type);
    }

    @Override
    public Object packPayload(Object payload) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer());
        Gson gson = builder.create();
        return gson.toJson(payload);
    }

    @Override
    protected Object makeCallResult(String uniqueId, String action, Object payload) {
        return String.format(CALLRESULT_FORMAT, uniqueId, payload);
    }

    @Override
    protected Object makeCall(String uniqueId, String action, Object payload) {
        return String.format(CALL_FORMAT, uniqueId, action, payload);
    }

    @Override
    protected Object makeCallError(
            String uniqueId, String action, String errorCode, String errorDescription) {
        return String.format(CALLERROR_FORMAT, uniqueId, errorCode, errorDescription, "{}");
    }

    @Override
    protected Message parse(Object json) {
        Message message;
        JsonArray array = JsonParser.parseString(json.toString()).getAsJsonArray();

        if (array.get(INDEX_MESSAGEID).getAsInt() == TYPENUMBER_CALL) {
            message = new CallMessage();
            message.setAction(array.get(INDEX_CALL_ACTION).getAsString());
            message.setPayload(array.get(INDEX_CALL_PAYLOAD).toString());
        } else if (array.get(INDEX_MESSAGEID).getAsInt() == TYPENUMBER_CALLRESULT) {
            message = new CallResultMessage();
            message.setPayload(array.get(INDEX_CALLRESULT_PAYLOAD).toString());
        } else if (array.get(INDEX_MESSAGEID).getAsInt() == TYPENUMBER_CALLERROR) {
            message = new CallErrorMessage();
            ((CallErrorMessage) message).setErrorCode(array.get(INDEX_CALLERROR_ERRORCODE).getAsString());
            ((CallErrorMessage) message)
                    .setErrorDescription(array.get(INDEX_CALLERROR_DESCRIPTION).getAsString());
            ((CallErrorMessage) message).setRawPayload(array.get(INDEX_CALLERROR_PAYLOAD).toString());
        } else {
            log.error("Unknown message type of message: {}", json.toString());
            throw new IllegalArgumentException("Unknown message type");
        }

        message.setId(array.get(INDEX_UNIQUEID).getAsString());

        return message;
    }

    private static class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

        @Override
        public JsonElement serialize(
                ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(zonedDateTime.toOffsetDateTime().withNano(0).toString());
        }
    }

    public static class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

        @Override
        public ZonedDateTime deserialize(
                JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            return ZonedDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString());
        }
    }
}
