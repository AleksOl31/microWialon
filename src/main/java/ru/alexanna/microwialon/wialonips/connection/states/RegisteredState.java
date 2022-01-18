package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.wialonips.connection.IPSCommunicator;
import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSConnectException;
import ru.alexanna.microwialon.wialonips.ipsexceptions.IPSDataException;
import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;


public class RegisteredState implements StateIPS {
    private final IPSTransmitter ipsTransmitter;

    public RegisteredState(IPSTransmitter ipsTransmitter) {
        this.ipsTransmitter = ipsTransmitter;
    }

    @Override
    public void createNetworkConnection() {
        System.out.println("CreateNetworkConnection not applied in RegisteredState");
    }

    @Override
    public void wialonLogin() {
        System.out.println("Login not applied in RegisteredState");
    }

    @Override
    public synchronized void communicate(IpsPacket ipsPacket) {
        IPSCommunicator communicator = new IPSCommunicator(ipsTransmitter.getConnection());
        try {
            communicator.communicate(ipsPacket);
        } catch (IPSConnectException e) {
            System.out.println(e.getMessage());
//            ipsTransmitter.setState(ipsTransmitter.getDisconnectedState());
//            ipsTransmitter.getConnection().close();
            stopCommunication();
        } catch (IPSDataException e) {
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
    public synchronized void breakNetworkConnection() {
        System.out.println("breakNetworkConnection not applied in RegisteredState");
    }

    @Override
    public String toString() {
        return "RegisteredState";
    }
}
