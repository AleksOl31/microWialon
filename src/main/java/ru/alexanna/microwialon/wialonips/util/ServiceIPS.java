package ru.alexanna.microwialon.wialonips.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public final class ServiceIPS {

    public static String getFormattedDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyy;HHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }
    public static String getLatitudeMsg(Double value) {
        return value == null ? "NA;NA" : getFormattedLatitude(value);
    }

    private static String getFormattedLatitude(Double value) {
        return getFirstPartLatitude(value) + ";" + getSecondPartLatitude(value);
    }

    private static String getFirstPartLatitude(Double value) {
        double absValue = Math.abs(value);
        int intPart = (int) absValue;
        double fractionalPart = absValue - intPart;
        String partOfDegrees = (intPart / 10 == 0) ? ("0" + intPart) : Integer.toString(intPart);
        return partOfDegrees + getFractionOfMinutes(fractionalPart);
    }

    private static String getFractionOfMinutes(Double value) {
        double minutes = Math.round(value * 60 * 100_000) / 100_000.0;
        return ((int) minutes / 10 == 0) ? ("0" + minutes) : Double.toString(minutes);
    }

    private static String getSecondPartLatitude(Double value) {
        return value > 0 ? "N" : "S";
    }

    public static String getLongitudeMsg(Double value) {
        return value == null ? "NA;NA" : getFormattedLongitude(value);
    }

    private static String getFormattedLongitude(Double value) {
        return getFirstPartLongitude(value) + ";" + getSecondPartLongitude(value);
    }

    private static String getFirstPartLongitude(Double value) {
        double absValue = Math.abs(value);
        int intPart = (int) absValue;
        double fractionalPart = absValue - intPart;
        String prefix = "";
        switch (numberCounter(intPart)) {
            case 1:
                prefix = "00";
                break;
            case 2:
                prefix = "0";
                break;
        }
        String partOfDegrees = prefix + intPart;
        return partOfDegrees + getFractionOfMinutes(fractionalPart);
    }

    private static int numberCounter(int value) {
        int count = value == 0 ? 1 : 0;
        while (value != 0) {
            count++;
            value /= 10;
        }
        return count;
    }

    private static String getSecondPartLongitude(Double value) {
        return value > 0 ? "E" : "W";
    }

    public static <V extends Number> String getParamMsg(V value) {
        return value == null ? "NA" : value.toString();
    }

    public  static String getParamMsg(Double[] values) {
        if (values == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            result.append(values[i]);
            if (i != values.length - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    public static String getParamMsg(String value) {
        return Objects.equals(value, "") || value == null ? "NA" : value;
    }
}
