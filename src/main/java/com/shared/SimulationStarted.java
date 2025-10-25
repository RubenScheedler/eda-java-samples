package com.shared;

public class SimulationStarted {
    private final String aggregateId;

    public SimulationStarted(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}
