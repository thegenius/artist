package com.lvonce.artist.transaction.tcc;

import lombok.AllArgsConstructor;

import javax.inject.Inject;


public class TccTransaction {

    private Task1 task1;
    private Task2 task2;
    private Task3 task3;

    @Inject
    public TccTransaction(Task1 task1, Task2 task2, Task3 task3) {
        this.task1 = task1;
        this.task2 = task2;
        this.task3 = task3;
    }

    @com.lvonce.artist.annotation.TccTransaction
    void testTcc() {
        task1.execute("command1");
        task2.execute("command2");
        task3.execute("command3");
    }
}
