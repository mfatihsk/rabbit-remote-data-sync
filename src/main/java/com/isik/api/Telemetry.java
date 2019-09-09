package com.isik.api;

/**
 * @author @fisik
 */
public class Telemetry {

    private int sourceType;

    private int measurement;

    private long timeStamp;

    private long index ;

    private byte[] data;

    public Telemetry(long index, int sourceType, int measurement, long timeStamp, byte[] data) {
        this.index = index;
        this.sourceType = sourceType;
        this.measurement = measurement;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public Telemetry() {
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Telemetry{" +
                "sourceType=" + sourceType +
                ", measurement=" + measurement +
                ", timeStamp=" + timeStamp +
                ", index=" + index +
                '}';
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
