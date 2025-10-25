package com.before;

/**
 * Timeout event that only carries the aggregate id. Matches the C# record TimeoutTriggered(string AggregateId).
 */
public class TimeoutTriggered {
    private String aggregateId;

    // No-arg constructor for frameworks / deserialization
    public TimeoutTriggered() { }

    public TimeoutTriggered(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }
}
