package com.after;

import java.util.function.Function;

public class SagaPropertyMapper<TData extends ContainSagaData> {
    public <TMessage> MessageMappingBuilder<TData, TMessage> mapSaga(Function<TData, String> sagaProperty) {
        return new MessageMappingBuilder<>();
    }

    public static class MessageMappingBuilder<TData, TMessage> {
        public <TMsg> MessageMappingBuilder<TData, TMessage> toMessage(Class<TMsg> clazz, java.util.function.Function<TMsg, String> messageProperty) {
            // no-op placeholder
            return this;
        }
    }
}
