package ru.alexanna.microwialon.wialonips.packettypes;

public class PingPacket extends AbstractIpsPacket {
    @Override
    public ClientPacketType getPacketType() {
        return ClientPacketType.P;
    }

    @Override
    public String getMessage() {
        return "";
    }

}
