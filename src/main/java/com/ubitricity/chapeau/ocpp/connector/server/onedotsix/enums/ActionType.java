/*
 * Copyright (c), ubitricity Gesellschaft für Verteilte Energiesysteme mbH,
 * Berlin, Germany
 *
 * All rights reserved. Dissemination, reproduction, or use of this material in source
 * and binary forms requires prior written permission from ubitricity.
 */
package com.ubitricity.chapeau.ocpp.connector.server.onedotsix.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionType {

    AUTHORIZE("Authorize"),
    BOOT_NOTIFICATION("BootNotification"),
    CANCEL_RESERVATION("CancelReservation"),
    CHANGE_AVAILABILITY("ChangeAvailability"),
    CHANGE_CONFIGURATION("ChangeConfiguration"),
    CLEAR_CACHE("ClearCache"),
    DATA_TRANSFER("DataTransfer"),
    DIAGNOSTICS_STATUS_NOTIFICATION("DiagnosticsStatusNotification"),
    FIRMWARE_STATUS_NOTIFICATION("FirmwareStatusNotification"),
    GET_CONFIGURATION("GetConfiguration"),
    GET_DIAGNOSTICS("GetDiagnostics"),
    GET_LOCAL_LIST_VERSION("GetLocalListVersion"),
    HEARTBEAT("Heartbeat"),
    METER_VALUES("MeterValues"),
    REMOTE_START_TRANSACTION("RemoteStartTransaction"),
    REMOTE_STOP_TRANSACTION("RemoteStopTransaction"),
    RESERVE_NOW("ReserveNow"),
    RESET("Reset"),
    SEND_LOCAL_LIST("SendLocalList"),
    SET_CHARGING_PROFILE("SetChargingProfile"),
    START_TRANSACTION("StartTransaction"),
    STATUS_NOTIFICATION("StatusNotification"),
    STOP_TRANSACTION("StopTransaction"),
    TRIGGER_MESSAGE("TriggerMessage"),
    UNLOCK_CONNECTOR("UnlockConnector"),
    UPDATE_FIRMWARE("UpdateFirmware"),
    ;

    private final String value;

}
