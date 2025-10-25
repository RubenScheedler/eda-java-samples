package com.after;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.shared.ContainSagaData;
import com.shared.DatetimeProvider;
import com.shared.HandlesTimeouts;
import com.shared.MessageHandlerContext;
import com.shared.Saga;
import com.shared.SagaPropertyMapper;
import com.shared.SendSimulatedMeasurements;
import com.shared.SimulationStarted;
import com.shared.StartedByMessages;

public class MeasurementsSimulatorSaga extends Saga<MeasurementsSimulatorSaga.SagaState>
        implements StartedByMessages<SimulationStarted>, HandlesTimeouts<TimeoutTriggered> {

    public static final int IntervalInSeconds = 3600;
    private final Duration interval = Duration.ofSeconds(IntervalInSeconds);
    private final DatetimeProvider dateTimeProvider;

    public MeasurementsSimulatorSaga(DatetimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    protected void configureHowToFindSaga(SagaPropertyMapper<SagaState> mapper) {
        mapper.mapSaga(messageProperty -> messageProperty.getAggregateId())
                .toMessage(SimulationStarted.class, SimulationStarted::getAggregateId)
                .toMessage(TimeoutTriggered.class, TimeoutTriggered::getAggregateId);
    }

    @Override
    public CompletionStage<Void> handle(SimulationStarted message, MessageHandlerContext context) {
        Data.setLastMeasuredValue(0);
        return RequestTimeout(context,
                interval,
                new TimeoutTriggered(Data.getAggregateId(), dateTimeProvider.now().toEpochMilli())
        );
    }

    @Override
    public CompletionStage<Void> timeout(TimeoutTriggered timeoutMessage, MessageHandlerContext context) {
        Instant measuredAt = Instant.ofEpochMilli(timeoutMessage.getTriggeredAt());

        List<CompletionStage<Void>> measurementsTasks = new ArrayList<>();

        while (!measuredAt.isAfter(dateTimeProvider.now())) { // Fill in downtime gaps
            measurementsTasks.add(context.send(new SendSimulatedMeasurements(
                    "sensor-1",
                    generateStubTemperatureValue(),
                    measuredAt
            )));

            measuredAt = measuredAt.plus(interval);
        }

    return CompletableFuture.allOf(measurementsTasks.stream()
                        .map(CompletionStage::toCompletableFuture)
                        .toArray(CompletableFuture[]::new))
        .thenCompose(v -> RequestTimeout(context, interval, new TimeoutTriggered(Data.getAggregateId(), dateTimeProvider.now().toEpochMilli())));
    }

    // Stub value calculations
    private long generateStubTemperatureValue() {
        Data.setLastMeasuredValue(Data.getLastMeasuredValue() + 1);
        return Data.getLastMeasuredValue();
    }

    public static class SagaState extends ContainSagaData {
        private String aggregateId = "";
        private long lastMeasuredValue;

        public String getAggregateId() {
            return aggregateId;
        }

        public void setAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
        }

        public long getLastMeasuredValue() {
            return lastMeasuredValue;
        }

        public void setLastMeasuredValue(long lastMeasuredValue) {
            this.lastMeasuredValue = lastMeasuredValue;
        }
    }

}
