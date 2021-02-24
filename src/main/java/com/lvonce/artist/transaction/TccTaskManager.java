package com.lvonce.artist.transaction;

public class TccTaskManager extends TaskManager {
    public static ThreadLocal<TccTaskManager> manager = ThreadLocal.withInitial(TccTaskManager::new);

    @Override
    public void confirm() {
        if (!taskGroup.shouldConfirm) {
            return;
        }
        while (!taskGroup.isEmpty()) {
            TaskElement<?> element = taskGroup.getFirstTask();
            boolean success = element.confirm();
            if (success) {
                taskGroup.removeFirstTask();
            } else {
                taskGroup.async();
            }
        }
    }
}
