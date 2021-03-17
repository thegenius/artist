package com.lvonce.artist.transaction;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Setter
public class TransactionExecutorService implements TransactionExecutor {
    private static final int HEARTBEAT_MILLIS = 1000;

    private final ConcurrentHashMap<String, TransactionElement> transactionMap;
    private final String executorUuid;
    private final TransactionManager manager;
    private final TransactionDispatcher dispatcher;

    public TransactionExecutorService(TransactionManager manager, TransactionDispatcher dispatcher) {
        this.executorUuid = UUID.randomUUID().toString();
        this.manager = manager;
        this.dispatcher = dispatcher;
        this.transactionMap = new ConcurrentHashMap<>();
        startHeartbeat();
    }

    private void startHeartbeat() {
        Thread heartbeatThread = new Thread(() -> {
            try {
                transactionMap.forEach((key, element) -> {
                    manager.heartbeat(element.getName(), element.getTaskUuid(), element.getExecutorUuid());
                    log.info("executor[{}] -> task[{}]", element.getExecutorUuid(), element.getTaskUuid());
                });
                Thread.sleep(HEARTBEAT_MILLIS);
            } catch (Exception ex) {
                log.error("{}", ex.getMessage());
            }
        });
        heartbeatThread.start();
    }

    @Override
    public TransactionStatus execute(String name, String taskUuid, String executorUuid, String params) {
        TransactionElement element = new TransactionElement(name, taskUuid, executorUuid, params);
        this.transactionMap.put(element.taskUuid, element);
        return dispatcher.dispatch(name, taskUuid, executorUuid, params);
    }

    @Override
    public void register(String name, String taskUuid, String params) {
        manager.register(name, taskUuid, executorUuid, params);
        TransactionElement element = new TransactionElement(name, taskUuid, executorUuid, params);
        this.transactionMap.put(element.taskUuid, element);
    }

    @Override
    public void confirm(String name, String taskUuid) {
        manager.confirm(name, taskUuid, executorUuid);
        this.transactionMap.remove(taskUuid);
    }

    @Override
    public void cancel(String name, String taskUuid) {
        manager.cancel(name, taskUuid, executorUuid);
        this.transactionMap.remove(taskUuid);
    }

}
