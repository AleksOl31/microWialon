package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.wialonips.connection.IPSCommunicator;
import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSConnectException;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSLoginException;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;
import ru.alexanna.microwialon.wialonips.packettypes.LoginPacket;

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
        communicate(new LoginPacket(ipsTransmitter.getMonObj().getId(), null));
    }

    @Override
    public synchronized void communicate(IpsPacket ipsPacket) {
        IPSCommunicator communicator = new IPSCommunicator(ipsTransmitter.getConnection());
        try {
            communicator.communicate(ipsPacket);
            ipsTransmitter.setState(ipsTransmitter.getRegisteredState());
        } catch (IPSConnectException e) {
            System.out.println(e.getMessage());
//            ipsTransmitter.setState(ipsTransmitter.getDisconnectedState());
//            ipsTransmitter.getConnection().close();
            stopCommunication();
        } catch (IPSLoginException e) {
            System.out.println(e.getMessage());
//            ipsTransmitter.setState(ipsTransmitter.getStoppedState());
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
