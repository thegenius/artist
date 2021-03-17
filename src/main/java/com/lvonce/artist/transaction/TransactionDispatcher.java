package com.lvonce.artist.transaction;

public interface TransactionDispatcher {
    TransactionStatus dispatch(String name, String taskUuid, String executorUuid, String params);
}
