package com.lvonce.artist.transaction;

public interface TransactionManager {

    void register(String name, String taskUuid, String executorUuid, String params);

    void heartbeat(String name, String taskUuid, String executorUuid);

    void confirm(String name, String taskUuid, String executorUuid);

    void cancel(String name, String taskUuid, String executorUuid);
}
