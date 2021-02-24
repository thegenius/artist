package com.lvonce.artist.transaction;

public class SagaTaskManager extends TaskManager {
    public static ThreadLocal<SagaTaskManager> manager = ThreadLocal.withInitial(SagaTaskManager::new);

    @Override
    public void confirm() {
        taskGroup.clear();
    }
}
