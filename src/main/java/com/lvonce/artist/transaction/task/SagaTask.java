package com.lvonce.artist.transaction.task;

public interface SagaTask<T, R> extends Task<T, R> {
    void cancel(T command);
}
