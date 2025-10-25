package com.measurementsSimulator;

import java.time.Instant;

public class TimeoutTriggered {
    private final String aggregateId;
    private final Instant triggeredAt;

    public TimeoutTriggered(String aggregateId, Instant triggeredAt) {
        this.aggregateId = aggregateId;
        this.triggeredAt = triggeredAt;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public Instant getTriggeredAt() {
        return triggeredAt;
    }
}
