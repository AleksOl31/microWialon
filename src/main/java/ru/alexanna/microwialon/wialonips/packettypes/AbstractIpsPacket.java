package ru.alexanna.microwialon.wialonips.packettypes;

public abstract class AbstractIpsPacket implements IpsPacket {

    public abstract ClientPacketType getPacketType();

    public abstract String getMessage();

    public String getCompletePack() {
        return "#" + getPacketType().toString() + "#" + getMessage() + "\r\n";
    }

    @Override
    public String toString() {
        return getCompletePack();
    }
}
