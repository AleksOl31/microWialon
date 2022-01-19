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

        dataProvider = new DatabaseDataProvider(getMonitoringObjectMap());
    }

    public void startMonitoring() {
        dataProvider.startDataDelivery();
    }

    public void stopMonitoring() {
        dataProvider.stopDataDelivery();
    }

    private Map<Integer, String> getMonitoringObjectMap() {
        Map<Integer, String> monitoringObjectMap = new HashMap<>();
        monitoringObjectMap.put(2394, "20220001");
        monitoringObjectMap.put(2598, "20220002");
        monitoringObjectMap.put(2601, "20220003");
        monitoringObjectMap.put(403, "20220004");
        monitoringObjectMap.put(195, "20220005");
        monitoringObjectMap.put(816, "20220006");
        monitoringObjectMap.put(85, "20220007");
        return monitoringObjectMap;
    }
}
