package ru.alexanna.microwialon;

import ru.alexanna.microwialon.wialonips.connection.IPSTransmitter;
import ru.alexanna.microwialon.wialonips.connection.states.StateIPS;

public class MonitoringObject {
    private final String id;
    private final String password;

    private final Transmitter transmitter = new IPSTransmitter(this);
//    private MonitoringData monData;

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

    public void update(MonitoringData monData) {
//        this.monData = monData;
        transmitter.addToTransfer(monData);
    }

    public StateIPS getTransmitterState() {
        return transmitter.state();
    }

}
