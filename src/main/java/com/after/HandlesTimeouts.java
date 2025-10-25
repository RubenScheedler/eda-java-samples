package com.after;

public interface HandlesTimeouts<TMessage> {
    java.util.concurrent.CompletionStage<Void> timeout(TMessage message, MessageHandlerContext context);
}
