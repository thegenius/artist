package com.lvonce.artist.transaction.tcc;

import com.lvonce.artist.transaction.task.TccTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class Task2 implements TccTask<String, String> {

    @Override
    public String execute(String command) {
        log.info("task2 execute");
        System.out.println("task2 execute");
        throw new RuntimeException("runtime ex");
//        return command + " result2";
    }

    @Override
    public void confirm(String command) {
        System.out.println("task2 confirm");
    }

    @Override
    public void cancel(String command) {

        System.out.println("task2 cancel");
        log.info("cancel2 " + command);
    }
}