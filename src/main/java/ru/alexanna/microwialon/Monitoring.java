package ru.alexanna.microwialon;

import ru.alexanna.microwialon.dataproviders.DataProvider;
import ru.alexanna.microwialon.dataproviders.DatabaseDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monitoring {
    private final DataProvider dataProvider;

    public Monitoring() {
        dataProvider = new DatabaseDataProvider(new MonitoringObjectsLoader().getMonitoringObjectMap());
    }

    public void startMonitoring() {
        dataProvider.startDataDelivery();
    }

    public void stopMonitoring() {
        dataProvider.stopDataDelivery();
    }

}
