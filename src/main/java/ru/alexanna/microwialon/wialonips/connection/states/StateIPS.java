package ru.alexanna.microwialon.wialonips.connection.states;

import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;

public interface StateIPS {

    void createNetworkConnection();

    void wialonLogin();

    void communicate(IpsPacket ipsPacket);

    void stopCommunication();

    void breakNetworkConnection();
}
