package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.MonitoringObject;
import ru.alexanna.microwialon.wialonips.connection.IPSCommunicator;
import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSConnectException;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSLoginException;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;
import ru.alexanna.microwialon.wialonips.util.IpsPacketFactory;

public class ConnectedState implements StateIPS {
    private final IPSTransmitter ipsTransmitter;

    public ConnectedState(IPSTransmitter ipsTransmitter) {
        this.ipsTransmitter = ipsTransmitter;
    }

    @Override
    public void createNetworkConnection() {
        System.out.println("CreateNetworkConnection not applied in ConnectedState");
    }

    @Override
    public synchronized void wialonLogin() {
        MonitoringObject monObj = ipsTransmitter.getMonObj();
        communicate(IpsPacketFactory.createLoginPacket(monObj.getId(), monObj.getPassword()));
    }

    @Override
    public synchronized void communicate(IpsPacket ipsPacket) {
        IPSCommunicator communicator = new IPSCommunicator(ipsTransmitter.getConnection());
        try {
            communicator.communicate(ipsPacket);
            ipsTransmitter.setState(ipsTransmitter.getRegisteredState());
        } catch (IPSConnectException | IPSLoginException e) {
            System.out.println(e.getMessage());
            stopCommunication();
        }
    }

    @Override
    public synchronized void stopCommunication() {
        ipsTransmitter.setState(ipsTransmitter.getStoppedState());
        ipsTransmitter.stop();
    }

    @Override
    public void breakNetworkConnection() {
        System.out.println("BreakNetworkConnection not applied in ConnectedState");
    }

    @Override
    public String toString() {
        return "ConnectedState";
    }
}
