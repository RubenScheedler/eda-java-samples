package com.shared;

import java.util.concurrent.CompletionStage;

public interface MessageHandlerContext {
    CompletionStage<Void> send(Object message);
}
