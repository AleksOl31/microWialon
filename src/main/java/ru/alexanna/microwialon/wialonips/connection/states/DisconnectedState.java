package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.network.ServerConnection;
import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;

public class DisconnectedState implements StateIPS {
    private final IPSTransmitter ipsTransmitter;

    public DisconnectedState(IPSTransmitter ipsTransmitter) {
        this.ipsTransmitter = ipsTransmitter;
    }

    @Override
    public synchronized void createNetworkConnection() {
        ipsTransmitter.setConnection(new ServerConnection());
        if (ipsTransmitter.getConnection().isConnected()) {
            ipsTransmitter.setState(ipsTransmitter.getConnectedState());
        }
    }

    @Override
    public void wialonLogin() {
        System.out.println("Login not applied in DisconnectedState");
    }

    @Override
    public void communicate(IpsPacket ipsPacket) {
        System.out.println("Communicate not applied in DisconnectedState");
    }

    @Override
    public void stopCommunication() {
        System.out.println("StopCommunication not applied in DisconnectedState");
    }

    @Override
    public void breakNetworkConnection() {
        System.out.println("BreakNetworkConnection not applied in DisconnectedState");
    }

    @Override
    public String toString() {
        return "DisconnectedState";
    }
}
