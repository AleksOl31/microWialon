package ru.alexanna.microwialon.wialonips.ipsexceptions;

public class IPSDataException extends RuntimeException {
    public IPSDataException(String message) {
        super(message);
    }

    public IPSDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
