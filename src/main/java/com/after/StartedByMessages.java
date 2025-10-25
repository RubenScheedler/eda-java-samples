package com.after;

public interface StartedByMessages<TMessage> {
    java.util.concurrent.CompletionStage<Void> handle(TMessage message, MessageHandlerContext context);
}
