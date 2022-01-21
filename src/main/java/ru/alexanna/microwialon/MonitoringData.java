package ru.alexanna.microwialon;

import java.util.Date;
import java.util.Objects;

public class MonitoringData {
    /**
     * Основные мониторинговые данные
     */
    private Date date;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private Integer course;
    private Integer height;
    private Integer sats;
    /**
     * Дополнительные мониторинговые данные
     */
    private Integer fuelVolume;
    private Integer fuelWeight;
    private Integer fuelTemp;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSats() {
        return sats;
    }

    public void setSats(Integer sats) {
        this.sats = sats;
    }

    public Integer getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Integer fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Integer getFuelWeight() {
        return fuelWeight;
    }

    public void setFuelWeight(Integer fuelWeight) {
        this.fuelWeight = fuelWeight;
    }

    public Integer getFuelTemp() {
        return fuelTemp;
    }

    public void setFuelTemp(Integer fuelTemp) {
        this.fuelTemp = fuelTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonitoringData that = (MonitoringData) o;
        return /*Objects.equals(date, that.date) && */ Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(speed, that.speed) && Objects.equals(course, that.course) && Objects.equals(height, that.height) && Objects.equals(sats, that.sats) && Objects.equals(fuelVolume, that.fuelVolume) && Objects.equals(fuelWeight, that.fuelWeight) && Objects.equals(fuelTemp, that.fuelTemp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*date,*/ latitude, longitude, speed, course, height, sats, fuelVolume, fuelWeight, fuelTemp);
    }
}
