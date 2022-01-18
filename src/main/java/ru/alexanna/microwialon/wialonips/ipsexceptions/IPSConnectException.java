package ru.alexanna.microwialon.wialonips.ipsexceptions;

public class IPSConnectException extends RuntimeException {
    public IPSConnectException(String message) {
        super(message);
    }

    public IPSConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
