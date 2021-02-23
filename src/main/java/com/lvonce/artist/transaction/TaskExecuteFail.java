package com.lvonce.artist.transaction;

public class TaskExecuteFail extends RuntimeException {
    public TaskExecuteFail() {
        super();
    }
    public TaskExecuteFail(String msg) {
        super(msg);
    }
}
