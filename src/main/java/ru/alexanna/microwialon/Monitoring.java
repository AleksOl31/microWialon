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
        monitoringObjectMap.put(65, "20220001");
        monitoringObjectMap.put(85, "20220002");
        monitoringObjectMap.put(195, "20220003");
        monitoringObjectMap.put(298, "20220004");
        monitoringObjectMap.put(348, "20220005");
        monitoringObjectMap.put(384, "20220006");
        monitoringObjectMap.put(447, "20220007");
        monitoringObjectMap.put(494, "20220008");
        monitoringObjectMap.put(569, "20220009");
        monitoringObjectMap.put(604, "20220010");
        monitoringObjectMap.put(799, "20220011");
        monitoringObjectMap.put(813, "20220012");
        monitoringObjectMap.put(816, "20220013");
        monitoringObjectMap.put(1271, "20220014");
        monitoringObjectMap.put(1696, "20220015");
        monitoringObjectMap.put(2050, "20220016");
        monitoringObjectMap.put(2277, "20220017");
        monitoringObjectMap.put(2333, "20220018");
        monitoringObjectMap.put(2353, "20220019");
        monitoringObjectMap.put(2394, "20220020");
        monitoringObjectMap.put(2598, "20220021");
        monitoringObjectMap.put(2601, "20220022");
        monitoringObjectMap.put(2658, "20220023");
        monitoringObjectMap.put(3107, "20220024");
        monitoringObjectMap.put(3186, "20220025");
        monitoringObjectMap.put(3195, "20220026");
        monitoringObjectMap.put(3230, "20220027");
        monitoringObjectMap.put(3244, "20220028");
        monitoringObjectMap.put(3245, "20220029");
        monitoringObjectMap.put(8370, "20220030");
        monitoringObjectMap.put(8720, "20220031");
        monitoringObjectMap.put(8996, "20220032");

        monitoringObjectMap.put(4444, "20220033");
        monitoringObjectMap.put(4835, "20220034");
        monitoringObjectMap.put(5656, "20220035");
        monitoringObjectMap.put(8260, "20220036");

        monitoringObjectMap.put(403, "20220037");
        monitoringObjectMap.put(1998, "20220038");

//        monitoringObjectMap.put(65, "20221111");
        return monitoringObjectMap;
    }
}
