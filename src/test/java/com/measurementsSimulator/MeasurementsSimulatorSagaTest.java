package com.measurementsSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementsSimulatorSagaTest {

    private static final String AggregateId = "measurements-processor-backend";

    private final Instant baseNow = Instant.now();
    private TestingDatetimeProvider datetimeProvider;
    private MeasurementsSimulatorSaga systemUnderTest;
    private TestMessageHandlerContext context;

    @BeforeEach
    void setUp() {
        datetimeProvider = new TestingDatetimeProvider(baseNow);
        systemUnderTest = new MeasurementsSimulatorSaga(datetimeProvider);
        systemUnderTest.Data = new MeasurementsSimulatorSaga.SagaState();
        systemUnderTest.Data.setAggregateId(AggregateId);
        context = new TestMessageHandlerContext();
    }

    @Test
    void timeoutSendsSendSimulatedMeasurements() throws Exception {
        // Arrange
        TimeoutTriggered timeout = new TimeoutTriggered(AggregateId, baseNow);

        // Act
        systemUnderTest.timeout(timeout, context).toCompletableFuture().get();

        // Assert
        boolean hasSend = context.getSentMessages().stream().anyMatch(m -> m instanceof SendSimulatedMeasurements);
        assertTrue(hasSend, "Expected a SendSimulatedMeasurements message to be sent");
    }

    @Test
    void timeoutSchedulesNextTimeout() throws Exception {
        // Arrange
        TimeoutTriggered timeout = new TimeoutTriggered(AggregateId, baseNow);

        // Act
        systemUnderTest.timeout(timeout, context).toCompletableFuture().get();

        // Assert: there should be at least one TimeoutTriggered sent
        boolean hasTimeoutSent = context.getSentMessages().stream().anyMatch(m -> m instanceof TimeoutTriggered);
        assertTrue(hasTimeoutSent, "Expected a TimeoutTriggered to be scheduled/sent");
    }

    @Test
    void sagaAfterMultipleIntervalsOfDowntimeSendsCommandForEveryInterval() throws Exception {
        // Arrange
        // Start the saga (this will request an initial timeout at baseNow)
        systemUnderTest.handle(new SimulationStarted(AggregateId), context).toCompletableFuture().get();

        // Now jump 3 intervals into the future
        datetimeProvider.setNow(baseNow.plusSeconds(3 * MeasurementsSimulatorSaga.IntervalInSeconds));

        // Trigger the timeout that was originally scheduled at baseNow
        TimeoutTriggered timeout = new TimeoutTriggered(AggregateId, baseNow);
        systemUnderTest.timeout(timeout, context).toCompletableFuture().get();

        // Assert only one logical timeout call was processed (we invoked it once) and many SendSimulatedMeasurements were sent
        List<SendSimulatedMeasurements> sent = context.getSentMessages().stream()
                .filter(m -> m instanceof SendSimulatedMeasurements)
                .map(m -> (SendSimulatedMeasurements) m)
                .collect(Collectors.toList());

        // We expect 4 send commands: one for the original interval + 3 to fill the gaps
        assertEquals(4, sent.size(), "Expected 3 SendSimulatedMeasurements to be sent (1 + 2 gaps)");
    }

    // --- Test helpers ---
    static class TestingDatetimeProvider implements IDatetimeProvider {
        private Instant now;

        TestingDatetimeProvider(Instant now) { this.now = now; }

        @Override
        public Instant now() { return now; }

        void setNow(Instant now) { this.now = now; }
    }

    static class TestMessageHandlerContext implements IMessageHandlerContext {
        private final java.util.List<Object> sent = new java.util.ArrayList<>();

        @Override
        public java.util.concurrent.CompletionStage<Void> send(Object message) {
            sent.add(message);
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }

        java.util.List<Object> getSentMessages() { return sent; }
    }
}
