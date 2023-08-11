package ru.alexanna.microwialon;

import ru.alexanna.microwialon.dataproviders.DataProvider;
import ru.alexanna.microwialon.dataproviders.DatabaseDataProvider;

public class DataTransfer {
    private final DataProvider dataProvider;

    public DataTransfer() {
        dataProvider = new DatabaseDataProvider(new MonitoringObjectsLoader().getMonitoringObjectMap());
    }

    public void startTransfer() {
        dataProvider.startDataDelivery();
    }

    public void stopTransfer() {
        dataProvider.stopDataDelivery();
    }

}
