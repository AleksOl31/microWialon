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
            createCourseData(monData);
            transmitter.addToTransfer(monData);
            monitoringData = monData;
        }
    }

    // метод определение текущего курса по 2-м точкам
    private void createCourseData(MonitoringData monData) {
        if (monData.getSpeed() != 0 && monData.getSpeed() != null) {
            double lastLat;
            double lastLng;
            if (monitoringData != null) {
                lastLat = monitoringData.getLatitude();
                lastLng = monitoringData.getLongitude();
            } else {
                lastLat = 0.;
                lastLng = 0.;
            }
            double lat = monData.getLatitude();
            double lng = monData.getLongitude();

            double dLat = lat - lastLat;
            double dLon = lng - lastLng;
            double result = 0.;
            double azimuth = (Math.PI * .5d) - Math.atan(dLat / dLon);
            if (dLon > 0) {
                result = azimuth;
            } else if (dLon < 0) {
                result = azimuth + Math.PI;
            } else if (dLat < 0) {
                result = Math.PI;
            }
            int degrees = (int) Math.floor(Math.toDegrees(result));
//            int degrees = (int) Math.toDegrees(result);
            monData.setCourse(degrees);
        }
    }

//    public StateIPS getTransmitterState() {
//        return transmitter.state();
//    }

}
