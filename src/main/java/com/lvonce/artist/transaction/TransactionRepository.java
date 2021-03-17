package com.lvonce.artist.transaction;


public interface TransactionRepository {
    TransactionElement load();
    void register(String name, String taskUuid, String executorUuid, String params);
    void confirm(String name, String taskUuid, String executorUuid);
    void cancel(String name, String taskUuid, String executorUuid);
    void heartbeat(String name, String taskUuid, String executorUuid);
}
