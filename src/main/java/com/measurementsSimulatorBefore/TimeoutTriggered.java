package com.measurementsSimulatorBefore;

public class TimeoutTriggered {
    private String aggregateId;
    private long triggeredAt; // epoch millis

    // No-arg constructor for Jackson
    public TimeoutTriggered() { }

    public TimeoutTriggered(String aggregateId, long triggeredAt) {
        this.aggregateId = aggregateId;
        this.triggeredAt = triggeredAt;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public long getTriggeredAt() {
        return triggeredAt;
    }

    public void setTriggeredAt(long triggeredAt) {
        this.triggeredAt = triggeredAt;
    }
}
