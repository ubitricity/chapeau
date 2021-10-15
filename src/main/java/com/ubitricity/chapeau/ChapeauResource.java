package com.ubitricity.chapeau;

import com.ubitricity.chapeau.domain.NonExistingDeviceIdException;
import com.ubitricity.chapeau.domain.RejectedRequestException;
import com.ubitricity.chapeau.service.ChapeauService;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;

@Slf4j
@Path("/api/devices/{deviceId}")
public class ChapeauResource {

    private final ChapeauService chapeauService;

    public ChapeauResource(ChapeauService chapeauService) {
        this.chapeauService = chapeauService;
    }

    @POST
    @Path("boot")
    public String boot(@PathParam("deviceId") String deviceId) {
        try {
            chapeauService.subscribeOnBoot(deviceId);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to subscribe for boot notifications", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("status")
    public String status(@PathParam("deviceId") String deviceId) {
        try {
            chapeauService.subscribeOnStatus(deviceId);
            return "OK";
        } catch (NonExistingDeviceIdException e) {
            log.error("Failed to subscribe for status notifications", e);
            throw new NotFoundException(e.getMessage());
        } catch (RejectedRequestException e) {
            log.error("Charge point rejected the connection", e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @POST
    @Path("plug")
    public String plug(@PathParam("deviceId") String deviceId) {
        throw new UnsupportedOperationException();
    }

    @POST
    @Path("unplug")
    public String unplug(@PathParam("deviceId") String deviceId) {
        throw new UnsupportedOperationException();
    }

    @POST
    @Path("rfid-swipe")
    public String rfidSwipe(@PathParam("deviceId") String deviceId) {
        throw new UnsupportedOperationException();
    }

    @Scheduled(every = "20s", delayed = "10s")
//    @Scheduled(every = "5m", delayed = "10s")
    public void checkDevicesAvailability() {
        chapeauService.checkDevicesAvailability();
    }
}
