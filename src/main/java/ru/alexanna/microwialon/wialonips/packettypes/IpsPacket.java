package ru.alexanna.microwialon.wialonips.packettypes;

public interface IpsPacket {

    ClientPacketType getPacketType();

    String getMessage();

    String getCompletePack();

}
