package ru.alexanna.microwialon.dataproviders;

public interface DataProvider {
    void startDataDelivery();

    void stopDataDelivery();

    int getDbQueryPause();

    void setDbQueryPause(int dbQueryPause);
}
