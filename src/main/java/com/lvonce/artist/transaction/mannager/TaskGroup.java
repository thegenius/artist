package com.lvonce.artist.transaction.mannager;

import com.lvonce.artist.transaction.TaskElement;
import com.lvonce.artist.transaction.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskGroup {
    private static final ConcurrentLinkedQueue<TaskGroup> taskQueue = new ConcurrentLinkedQueue<>();

    public static TaskGroup pollAsyncTask() {
        return taskQueue.poll();
    }

    public static void addAsyncTask(TaskGroup taskGroup) {
        taskQueue.add(taskGroup);
    }

    List<TaskElement<?>> elements = new ArrayList<>();

    boolean shouldConfirm = false;

    public <T> void execute(Task<T, ?> task, T command) {
        this.elements.add(new TaskElement<>(task, command));
    }

    public void clear() {
        this.elements.clear();
        this.shouldConfirm = false;
    }

    public void setShouldConfirm(boolean shouldConfirm) {
        this.shouldConfirm = shouldConfirm;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public TaskElement<?> getLastTask() {
        int last = elements.size() - 1;
        return elements.get(last);
    }

    public TaskElement<?> removeLastTask() {
        int last = elements.size() - 1;
        return elements.remove(last);
    }

    public TaskElement<?> getFirstTask() {
        return elements.get(0);
    }

    public TaskElement<?> removeFirstTask() {
        return elements.remove(0);
    }

    public void async() {
        TaskGroup asyncGroup = new TaskGroup();
        asyncGroup.elements.addAll(elements);
        asyncGroup.shouldConfirm = this.shouldConfirm;
        addAsyncTask(asyncGroup);
        clear();
    }
}
