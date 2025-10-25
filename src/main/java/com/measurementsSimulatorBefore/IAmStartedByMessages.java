package com.measurementsSimulatorBefore;

public interface IAmStartedByMessages<TMessage> {
    java.util.concurrent.CompletionStage<Void> handle(TMessage message, IMessageHandlerContext context);
}
