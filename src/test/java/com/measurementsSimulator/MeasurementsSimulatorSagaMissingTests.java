package com.measurementsSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementsSimulatorSagaMissingTests {

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
    void missingTest() throws Exception {
        // Arrange
        // Simulate deserialization of a JSON message that contains only AggregateId (no TriggeredAt)
        String json = "{\"AggregateId\": \"" + AggregateId + "\"}";
        TimeoutTriggered timeout = deserializeTimeoutTriggered(json);

        // Act
        systemUnderTest.timeout(timeout, context).toCompletableFuture().get();

        // Assert
        boolean hasSend = context.getSentMessages().stream().anyMatch(m -> m instanceof SendSimulatedMeasurements);
        assertTrue(hasSend, "Expected a SendSimulatedMeasurements message to be sent when TriggeredAt is missing/old");
    }

    private TimeoutTriggered deserializeTimeoutTriggered(String json) {
        // Very small ad-hoc JSON parse for presentation purposes: extract AggregateId if present.
        String marker = "\"AggregateId\"";
        int idx = json.indexOf(marker);
        if (idx >= 0) {
            int colon = json.indexOf(':', idx);
            if (colon >= 0) {
                int firstQuote = json.indexOf('"', colon);
                int secondQuote = json.indexOf('"', firstQuote + 1);
                if (firstQuote >= 0 && secondQuote > firstQuote) {
                    String id = json.substring(firstQuote + 1, secondQuote);
                    // If TriggeredAt is missing we emulate the C# default by using a very old Instant
                    return new TimeoutTriggered(id, Instant.EPOCH);
                }
            }
        }
        // Fallback: return a timeout with the AggregateId constant and very old timestamp
        return new TimeoutTriggered(AggregateId, Instant.EPOCH);
    }

    // Local test helpers (duplicated for isolation)
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
