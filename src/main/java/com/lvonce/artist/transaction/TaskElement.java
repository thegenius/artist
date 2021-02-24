package com.lvonce.artist.transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskElement<T> {
    private final Task<T, ?> task;
    private final T command;

    public boolean confirm() {
        try {
            task.cancel(command);
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
