package com.lvonce.artist.transaction;

import com.lvonce.artist.transaction.task.Task;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskElement<T> {
    private final Task<T, ?> task;
    private final T command;

    public boolean confirm() {
        try {
            task.confirm(command);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean cancel() {
        try {
            task.cancel(command);
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }
}
