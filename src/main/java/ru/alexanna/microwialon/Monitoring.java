package ru.alexanna.microwialon;

import ru.alexanna.microwialon.dataproviders.DataProvider;
import ru.alexanna.microwialon.dataproviders.DatabaseDataProvider;

import java.util.ArrayList;
import java.util.List;

public class Monitoring {
    private final List<MonitoringObject> monitoringObjects = new ArrayList<>();
    private final DataProvider dataProvider;

    public Monitoring() {
        monitoringObjects.add(new MonitoringObject("11223344", null));
        monitoringObjects.add(new MonitoringObject("55667788", null));
        monitoringObjects.add(new MonitoringObject("5678567", null));

        dataProvider = new DatabaseDataProvider(monitoringObjects);
    }

    public void startMonitoring() {
        monitoringObjects.forEach(MonitoringObject::startDataTransfer);
        dataProvider.startDataDelivery();
    }

    public void stopMonitoring() {
        monitoringObjects.forEach(MonitoringObject::stopDataTransfer);
        dataProvider.stopDataDelivery();
    }
}
