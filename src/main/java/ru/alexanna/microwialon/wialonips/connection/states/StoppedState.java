package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;

public class StoppedState implements StateIPS {
    private final IPSTransmitter ipsTransmitter;

    public StoppedState(IPSTransmitter ipsTransmitter) {
        this.ipsTransmitter = ipsTransmitter;
    }

    @Override
    public void createNetworkConnection() {
        System.out.println("CreateNetworkConnection not applied in StoppedState");
    }

    @Override
    public void wialonLogin() {
        System.out.println("Login not applied in StoppedState");
    }

    @Override
    public void communicate(IpsPacket ipsPacket) {
        System.out.println("Communicate not applied in StoppedState");
    }

    @Override
    public void stopCommunication() {
        System.out.println("The communication is already in the stop state ");
        breakNetworkConnection();
    }

    @Override
    public void breakNetworkConnection() {
        ipsTransmitter.getConnection().close();
        ipsTransmitter.setState(ipsTransmitter.getDisconnectedState());
    }

    @Override
    public String toString() {
        return "StoppedState";
    }
}
