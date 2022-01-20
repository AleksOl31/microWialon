package ru.alexanna.microwialon.wialonips.connection;

import ru.alexanna.microwialon.network.ServerConnection;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSConnectException;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSDataException;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSLoginException;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;

import java.util.Date;

public class IPSCommunicator {

    private final ServerConnection connection;

    public IPSCommunicator(ServerConnection connection) {
        this.connection = connection;
    }

    public void communicate(IpsPacket ipsPacket) throws RuntimeException {
        connection.sendMessage(ipsPacket);
        String sentLogStr = ipsPacket.getMessage() + "-package sent on: \t" + new Date() + "\t";
        String response = connection.receiveMessage();
        String receiveLogStr = response + " - server response received on: \t" + new Date();
        System.out.println(sentLogStr + receiveLogStr);
        if (response == null) {
            throw new IPSConnectException("Server returned null.");
        }

        switch (response) {
            case "#AL#0":
                throw new IPSLoginException("Server rejected the connection.");
            case "#AL#01":
                throw new IPSLoginException("Password verification error.");
            case "#ASD#-1":
                throw new IPSDataException("Short data package structure error.");
            case "#AD#-1":
                throw new IPSDataException("Long data package structure error.");
        }
    }
}
