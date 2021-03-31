package com.lvonce.artist.transaction.tcc;

import com.lvonce.artist.transaction.task.TccTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class Task1 implements TccTask<String, String> {

    @Override
    public String execute(String command) {
        log.info("task1 execute");
        System.out.println("task1 execute");
        return command + " result1";
    }

    @Override
    public void confirm(String command) {
        System.out.println("task1 confirm");
    }

    @Override
    public void cancel(String command) {
        System.out.println("task1 cancel");
        log.info("cancel1 " + command);
    }
}