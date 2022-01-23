package ru.alexanna.microwialon.wialonips.util;

import ru.alexanna.microwialon.MonitoringData;
import ru.alexanna.microwialon.wialonips.packettypes.*;

public class IpsPacketFactory {

    public static IpsPacket createLongDataPacket(MonitoringData monitoringData) {
        return new LongDataPacket.Builder(monitoringData.getDate())
                .coordinates(monitoringData.getLatitude(), monitoringData.getLongitude())
                .course(monitoringData.getCourse()).speed(monitoringData.getSpeed())
                .sats(monitoringData.getSats()).height(monitoringData.getHeight())
                .params("fuel", ParameterType.Integer, monitoringData.getFuelVolume().toString())
                .create();
    }

    public static IpsPacket createLoginPacket(String id, String password) {
        return new LoginPacket(id, password);
    }
}
