package com.lvonce.artist.transaction.task;

public interface Task<T, R> {

    R execute(T command);

    void cancel(T command);

    default void confirm(T command) {};
}
