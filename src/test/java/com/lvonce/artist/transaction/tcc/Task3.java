package com.lvonce.artist.transaction.tcc;

import com.lvonce.artist.transaction.TccTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class Task3 implements TccTask<String, String> {

    @Override
    public String execute(String command) {
        log.info("task3 execute");
        System.out.println("task3 execute");
        return command + " result3";
    }

    @Override
    public void confirm(String command) {
        System.out.println("task3 confirm");
        log.info("task3 confirm");
    }

    @Override
    public void cancel(String command) {
        System.out.println("task3 cancel");
        log.info("cancel3 " + command);
    }
}