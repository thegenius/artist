package com.lvonce.artist.transaction;

public class TaskExecutePending extends RuntimeException {
    public TaskExecutePending() {
        super();
    }
    public TaskExecutePending(String msg) {
        super(msg);
    }
}
