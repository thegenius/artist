package com.lvonce.artist.transaction;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionManagerService implements TransactionManager {
    private static final int HEARTBEAT_MILLIS = 1000;
    private final TransactionRepository repository;
    private final TransactionExecutor executor;

    public TransactionManagerService(TransactionExecutor executor, TransactionRepository repository) {
        this.executor = executor;
        this.repository = repository;
        startLoad();
    }

    private void startLoad() {
        Thread heartbeatThread = new Thread(() -> {
            try {
                TransactionElement transaction = this.repository.load();
                if (transaction != null) {
                    executor.execute(
                            transaction.getName(),
                            transaction.getTaskUuid(),
                            transaction.getExecutorUuid(),
                            transaction.getParams()
                    );
                }
                Thread.sleep(HEARTBEAT_MILLIS);
            } catch (Exception ex) {
                log.error("{}", ex.getMessage());
            }
        });
        heartbeatThread.start();
    }

    @Override
    public void register(String name, String taskUuid, String executorUuid, String params) {
//        TransactionEntity entity = new TransactionEntity();
//        entity.setTaskUuid(taskUuid);
//        entity.setParams(params);
//        entity.setName(name);
//
//        entity.setExecutorUuid(executorUuid);
//        entity.setStatus(PENDING);
//        entity.setPreemptCount(0);
//
//        LocalDateTime expireTime = LocalDateTime.now();
//        expireTime = expireTime.plusSeconds(EXPIRE_SECONDS);
//
//        entity.setCreateTime(LocalDateTime.now());
//        entity.setExpireTime(expireTime);
//        entity.setUpdateTime(LocalDateTime.now());
        repository.register(name, taskUuid, executorUuid, params);
    }

    @Override
    public void confirm(String name, String taskUuid, String executorUuid) {
        repository.confirm(name, taskUuid, executorUuid);
    }

    @Override
    public void cancel(String name, String taskUuid, String executorUuid) {
        repository.cancel(name, taskUuid, executorUuid);
    }

    @Override
    public void heartbeat(String name, String taskUuid, String executorUuid) {
        repository.heartbeat(name, taskUuid, executorUuid);
    }
}
