package ru.alexanna.microwialon.wialonips.packettypes;

import java.util.Date;

public class ShortDataPacket extends AbstractDataPacket {

    public static class Builder extends AbstractDataPacket.Builder<Builder> {

        public Builder(Date date) {
            super(date);
        }

        public Builder(int year, int month, int date, int hourOfDate, int minute, int second) {
            super(year, month, date, hourOfDate, minute, second);
        }

        @Override
        public AbstractDataPacket create() {
            return new ShortDataPacket(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    protected ShortDataPacket(Builder builder) {
        super(builder);
    }

    @Override
    public ClientPacketType getPacketType() {
        return ClientPacketType.SD;
    }
}

