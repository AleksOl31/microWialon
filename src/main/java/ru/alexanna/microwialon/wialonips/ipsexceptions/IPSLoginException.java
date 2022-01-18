package ru.alexanna.microwialon.wialonips.ipsexceptions;

public class IPSLoginException extends RuntimeException {
    public IPSLoginException(String message) {
        super(message);
    }

    public IPSLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
