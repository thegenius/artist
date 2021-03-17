package com.lvonce.artist.transaction;

public interface TransactionExecutor {

    TransactionStatus execute(String name, String taskUuid, String executorUuid, String params);

    void register(String name, String taskUuid, String params);

    void confirm(String name, String taskUuid);

    void cancel(String name, String taskUuid);
}
