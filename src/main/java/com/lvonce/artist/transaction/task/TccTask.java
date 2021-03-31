package com.lvonce.artist.transaction.task;

import com.lvonce.artist.transaction.task.Task;

public interface TccTask<T, R> extends Task<T, R> {

    void cancel(T command);

    void confirm(T command);
}
