package ru.alexanna.microwialon;

import ru.alexanna.microwialon.wialonips.connection.states.StateIPS;

public interface Transmitter {

    void start();
    void stop();
    void addToTransfer(MonitoringData monData);
    StateIPS getState();
}
