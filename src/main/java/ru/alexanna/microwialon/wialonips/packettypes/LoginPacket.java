package ru.alexanna.microwialon.wialonips.packettypes;

import ru.alexanna.microwialon.wialonips.util.ServiceIPS;

public class LoginPacket extends AbstractIpsPacket {
    private final String id;
    private final String password;

    public LoginPacket(String id, String password) {
        this.id = id;
        this.password = ServiceIPS.getParamMsg(password);
    }

    @Override
    public ClientPacketType getPacketType() {
        return ClientPacketType.L;
    }

    @Override
    public String getMessage() {
        return id + ";" + password;
    }

}
