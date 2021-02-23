package com.lvonce.artist.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SagaTransactionManager {

    private static final ConcurrentLinkedQueue<List<TaskPair<?>>> taskQueue = new ConcurrentLinkedQueue<>();

    public static List<TaskPair<?>> pollTask() {
        return taskQueue.poll();
    }

    public static void addTask(List<TaskPair<?>> taskPair) {
        taskQueue.add(taskPair);
    }

    public static ThreadLocal<SagaTransactionManager> manager = ThreadLocal.withInitial(SagaTransactionManager::new);

    //    /** -------------- Saga Task -----------------------
    //     * EMPTY ---> PENDING ---> SUCCESS ---> CANCELLING
    //     *              |                           |
    //     *              |                           |
    //     *              | ------>   FAIL      <------
    //     * -------------------------------------------------*/
    @Data
    @AllArgsConstructor
    private static class TaskPair<T> {
        private Task<T, ?> task;
        private T command;

        public Task.State cancel() {
            try {
                task.cancel(command);
                return Task.State.FAIL;
            } catch (Throwable ex) {
                return Task.State.CANCELLING;
            }
        }
    }

    List<TaskPair<?>> taskPairs = new ArrayList<>();

    public <T> void execute(Task<T, ?> task, T command) {
        this.taskPairs.add(new TaskPair<>(task, command));
    }

    public void async() {
        List<TaskPair<?>> copyPairs = new ArrayList<>(taskPairs);
        addTask(copyPairs);
        taskPairs.clear();
    }


    public void cancel() {
        while (!taskPairs.isEmpty()) {
            int last = taskPairs.size() - 1;
            TaskPair<?> pair = taskPairs.get(last);
            Task.State state = pair.cancel();
            if (state.equals(Task.State.CANCELLING)) {
                async();
            } else {
                taskPairs.remove(last);
            }
        }
    }
    public void confirm() {
        taskPairs.clear();
    }
}
