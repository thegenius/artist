package com.lvonce.artist.transaction.saga;


import com.lvonce.artist.transaction.task.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task3 implements Task<String, String> {
    @Override
    public String execute(String command) {
        log.info("task3 execute");
//        throw new RuntimeException("test");
            return command + " result3";
    }

    @Override
    public void cancel(String command) {
        log.info("cancel3 " + command);
    }
}

