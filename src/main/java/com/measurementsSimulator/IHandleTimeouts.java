package com.measurementsSimulator;

public interface IHandleTimeouts<TMessage> {
    java.util.concurrent.CompletionStage<Void> timeout(TMessage message, IMessageHandlerContext context);
}
