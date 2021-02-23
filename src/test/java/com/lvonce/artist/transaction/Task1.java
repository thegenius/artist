package com.lvonce.artist.transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class Task1 implements Task<String, String> {
    @Override
    public String execute(String command) {
        log.info("task1 execute");
        return command + " result1";
    }

    @Override
    public void cancel(String command) {
        log.info("cancel1 " + command);
    }
}