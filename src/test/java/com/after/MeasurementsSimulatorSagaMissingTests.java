package com.after;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shared.DatetimeProvider;
import com.shared.MessageHandlerContext;
import com.shared.SendSimulatedMeasurements;

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
        String json = "{\"aggregateId\": \"" + AggregateId + "\"}";
        ObjectMapper mapper = new ObjectMapper();
        TimeoutTriggered timeout = mapper.readValue(json, TimeoutTriggered.class);

        // Act
        systemUnderTest.timeout(timeout, context).toCompletableFuture().get();

        // Assert
        boolean hasSend = context.getSentMessages().stream().anyMatch(m -> m instanceof SendSimulatedMeasurements);
        assertTrue(hasSend, "Expected a SendSimulatedMeasurements message to be sent when TriggeredAt is missing/old");
    }

    // Local test helpers (duplicated for isolation)
    static class TestingDatetimeProvider implements DatetimeProvider {
        private Instant now;

        TestingDatetimeProvider(Instant now) { this.now = now; }

        @Override
        public Instant now() { return now; }

        void setNow(Instant now) { this.now = now; }
    }

    static class TestMessageHandlerContext implements MessageHandlerContext {
        private final java.util.List<Object> sent = new java.util.ArrayList<>();

        @Override
        public java.util.concurrent.CompletionStage<Void> send(Object message) {
            sent.add(message);
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }

        java.util.List<Object> getSentMessages() { return sent; }
    }
}
