package ru.alexanna.microwialon;

import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.connection.states.DisconnectedState;

public class MonitoringObject {
    private final String id;
    private final String password;

    private final Transmitter transmitter = new IPSTransmitter(this);
    private MonitoringData monitoringData;

    public MonitoringObject(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void startDataTransfer() {
        transmitter.start();
    }

    public void stopDataTransfer() {
        transmitter.stop();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public synchronized void update(MonitoringData monData) {
        if (!monData.equals(monitoringData)) {
            if (transmitter.state() instanceof DisconnectedState) {
                startDataTransfer();
            }
            transmitter.addToTransfer(monData);
            monitoringData = monData;
        }
    }

//    public StateIPS getTransmitterState() {
//        return transmitter.state();
//    }

}
