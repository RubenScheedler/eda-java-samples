package com.measurementsSimulator;

import java.util.concurrent.CompletionStage;

public abstract class Saga<TData extends ContainSagaData> {
    protected TData Data;

    protected abstract void configureHowToFindSaga(SagaPropertyMapper<TData> mapper);

    // Placeholder: schedule a timeout via the messaging framework. Here we simply send the message.
    protected CompletionStage<Void> RequestTimeout(IMessageHandlerContext context, java.time.Duration timespan, Object message) {
        return context.send(message);
    }
}
