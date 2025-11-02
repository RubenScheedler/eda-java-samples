package com.shared;

public interface HandlesMessages<TMessage> {
    java.util.concurrent.CompletionStage<Void> handle(TMessage message, MessageHandlerContext context);
}