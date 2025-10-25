package com.measurementsSimulatorBefore;

import java.util.concurrent.CompletionStage;

public interface IMessageHandlerContext {
    CompletionStage<Void> send(Object message);
}
