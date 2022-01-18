package ru.alexanna.microwialon.wialonips.connection;

import ru.alexanna.microwialon.MonitoringData;
import ru.alexanna.microwialon.MonitoringObject;
import ru.alexanna.microwialon.Transmitter;
import ru.alexanna.microwialon.network.ServerConnection;
import ru.alexanna.microwialon.wialonips.connection.states.*;
import ru.alexanna.microwialon.wialonips.packettypes.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class IPSTransmitter implements Transmitter {

    private final MonitoringObject monObj;
    private ServerConnection connection;
    private final List<IpsPacket> buffer = new LinkedList<>();
    private Thread communicationThread;

    private StateIPS state;

    private final StateIPS disconnectedState;
    private final StateIPS connectedState;
    private final StateIPS registeredState;
    private final StateIPS stoppedState;

    public IPSTransmitter(MonitoringObject monitoringObject) {
        monObj = monitoringObject;

        disconnectedState = new DisconnectedState(this);
        connectedState = new ConnectedState(this);
        registeredState = new RegisteredState(this);
        stoppedState = new StoppedState(this);

        state = disconnectedState;
    }

    private Thread createCommunicationThread() {
        return new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(monObj.getId() + ": starting " + threadName + " to receive messages from the server:\t" + new Date());

            state.createNetworkConnection();
            state.wialonLogin();

            do {
                IpsPacket ipsPacket = getPacketFromBuffer();
                if (ipsPacket != null) {
                    state.communicate(ipsPacket);
                }
                } while (state != stoppedState);
            state.breakNetworkConnection();
            System.out.println(monObj.getId() + ": stopping " + threadName + " to receive messages from the server:\t" + new Date());
        });
    }

    public synchronized void setState(StateIPS state) {
        this.state = state;
    }

    @Override
    public StateIPS state() {
        return state;
    }

    @Override
    public synchronized void addToTransfer(MonitoringData monData) {
        IpsPacket ipsPacket = new LongDataPacket.Builder(monData.getDate()).coordinates(monData.getLatitude(), monData.getLongitude())
                        .course(monData.getCourse()).speed(monData.getSpeed()).params("Operation", ParameterType.Integer, Integer.toString(21)).create();
            buffer.add(ipsPacket);
            notify();
    }

    private synchronized IpsPacket getPacketFromBuffer() {
        while (buffer.size() == 0) {
            try {
                wait(1000 * 60 * 6);
                if (buffer.size() == 0) {
                    state.stopCommunication();
                    return null;
                }
            } catch (InterruptedException e) {
                if (state != stoppedState) state.stopCommunication();
                return null;
            }
        }
        IpsPacket ipsPacket = buffer.remove(0);
        notify();
        return ipsPacket;
    }

    @Override
    public void start() {
        communicationThread = createCommunicationThread();
        communicationThread.start();
    }

    @Override
    public void stop() {
        communicationThread.interrupt();
    }

    public void setConnection(ServerConnection connection) {
        this.connection = connection;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public MonitoringObject getMonObj() {
        return monObj;
    }

    public StateIPS getDisconnectedState() {
        return disconnectedState;
    }

    public StateIPS getConnectedState() {
        return connectedState;
    }

    public StateIPS getRegisteredState() {
        return registeredState;
    }

    public StateIPS getStoppedState() {
        return stoppedState;
    }

}
