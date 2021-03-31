package com.lvonce.artist.transaction.mannager;


import com.lvonce.artist.transaction.TaskElement;
import com.lvonce.artist.transaction.task.Task;

public abstract class TaskManager {
    protected final TaskGroup taskGroup = new TaskGroup();

    public <T> void execute(Task<T, ?> task, T command) {
        this.taskGroup.execute(task, command);
    }

    public TaskGroup getTaskGroup() {
        return taskGroup;
    }

    public void cancel() {
        if (taskGroup.shouldConfirm) {
            return;
        }
        while (!taskGroup.isEmpty()) {
            TaskElement<?> element = taskGroup.getLastTask();
            boolean success = element.cancel();
            if (success) {
                taskGroup.removeLastTask();
            } else {
                taskGroup.async();
            }
        }
    }

    abstract public void confirm();
}
