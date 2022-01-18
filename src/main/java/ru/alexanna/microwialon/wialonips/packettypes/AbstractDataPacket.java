package ru.alexanna.microwialon.wialonips.packettypes;

import ru.alexanna.microwialon.wialonips.util.ServiceIPS;

import java.util.Calendar;
import java.util.Date;

public abstract class AbstractDataPacket extends AbstractIpsPacket {
    private final Date date;
    private final Double latitude;
    private final Double longitude;
    private final Integer speed;
    private final Integer course;
    private final Integer height;
    private final Integer sats;

    abstract static class Builder<T extends Builder<T>> {
        private final Date date;
        private Double latitude = null;
        private Double longitude = null;
        private Integer speed = null;
        private Integer course = null;
        private Integer height = null;
        private Integer sats = null;

        public Builder(Date date) {
            this.date = date;
        }

        public Builder(int year, int month, int date, int hourOfDate, int minute, int second) {
            this.date = getDateTime(year, month, date, hourOfDate, minute, second);
        }

        private Date getDateTime(int year, int month, int date, int hourOfDate, int minute, int second) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, date, hourOfDate, minute, second);
            return calendar.getTime();
        }

        public T coordinates(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return self();
        }

        public T speed(Integer speed) {
            this.speed = speed;
            return self();
        }

        public T course(Integer course) {
            this.course = course;
            return self();
        }

        public T height(Integer height) {
            this.height = height;
            return self();
        }

        public T sats(Integer sats) {
            this.sats = sats;
            return self();
        }

        abstract public AbstractDataPacket create();

        abstract protected T self();
    }

    protected AbstractDataPacket(Builder<?> builder) {
        date = builder.date;
        latitude = builder.latitude;
        longitude = builder.longitude;
        speed = builder.speed;
        course = builder.course;
        height = builder.height;
        sats = builder.sats;
    }

    @Override
    abstract public ClientPacketType getPacketType();

    @Override
    public String getMessage() {
        return String.format("%s;%s;%s;%s;%s;%s;%s",
                ServiceIPS.getFormattedDateTime(date),
                ServiceIPS.getLatitudeMsg(latitude),
                ServiceIPS.getLongitudeMsg(longitude),
                ServiceIPS.getParamMsg(speed),
                ServiceIPS.getParamMsg(course),
                ServiceIPS.getParamMsg(height),
                ServiceIPS.getParamMsg(sats));
    }

}
