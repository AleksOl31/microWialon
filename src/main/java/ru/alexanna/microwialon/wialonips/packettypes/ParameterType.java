package ru.alexanna.microwialon.wialonips.packettypes;

public enum ParameterType {
    Integer("1"), Double("2"), String("3");

    private String value;

    ParameterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
