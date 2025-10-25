package com.measurementsSimulator;

import java.time.Instant;

public class SendSimulatedMeasurements {
    private final String sensorId;
    private final long value;
    private final Instant measuredAt;

    public SendSimulatedMeasurements(String sensorId, long value, Instant measuredAt) {
        this.sensorId = sensorId;
        this.value = value;
        this.measuredAt = measuredAt;
    }

    public String getSensorId() {
        return sensorId;
    }

    public long getValue() {
        return value;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }
}
