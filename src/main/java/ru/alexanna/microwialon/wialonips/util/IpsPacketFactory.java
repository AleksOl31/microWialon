package ru.alexanna.microwialon.wialonips.util;

import ru.alexanna.microwialon.MonitoringData;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;
import ru.alexanna.microwialon.wialonips.packettypes.LongDataPacket;
import ru.alexanna.microwialon.wialonips.packettypes.ParameterType;

public class IpsPacketFactory {

    public static IpsPacket createLongDataPacket(MonitoringData monitoringData) {
        return new LongDataPacket.Builder(monitoringData.getDate())
                .coordinates(monitoringData.getLatitude(), monitoringData.getLongitude())
                .course(monitoringData.getCourse()).speed(monitoringData.getSpeed())
                .params("fuel", ParameterType.Integer, monitoringData.getFuelVolume().toString())
                .create();
    }
}
