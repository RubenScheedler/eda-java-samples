package com.before;

import java.time.Duration;
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
        // Schedule the first timeout (framework should deliver a TimeoutTriggered)
        return RequestTimeout(context,
                interval,
                new TimeoutTriggered(Data.getAggregateId())
        );
    }

    @Override
    public CompletionStage<Void> timeout(TimeoutTriggered timeoutMessage, MessageHandlerContext context) {
        // On timeout, send a single measurement at the current time and schedule the next timeout.
        context.send(new SendSimulatedMeasurements(
                "sensor-1",
                generateStubTemperatureValue(),
                dateTimeProvider.now()
        ));

        return RequestTimeout(context,
                interval,
                new TimeoutTriggered(Data.getAggregateId())
        );
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
