package com.lvonce.artist.transaction.saga;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lvonce.artist.annotation.SagaTransaction;
import com.lvonce.artist.module.InterceptorModule;

import com.lvonce.artist.transaction.SagaTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SagaTransactionManagerTest {



    @Test(expected = RuntimeException.class)
    public void test() throws Exception {
        SagaTaskManager manager = SagaTaskManager.manager.get();
        try {
            Task1 task1 = new Task1();
            Task2 task2 = new Task2();
            Task3 task3 = new Task3();
            manager.execute(task1, "test1");
            task1.execute("test1");

            manager.execute(task2, "test2");
            task2.execute("test2");

            manager.execute(task3, "test3");
            task3.execute("test3");

            manager.confirm();
        } catch (Throwable t) {
            manager.cancel();
            throw t;
        }
    }

    public static class SageTransactionTest1 {
        @Inject
        Task1 task1;
        @Inject
        Task2 task2;
        @Inject
        Task3 task3;

        @SagaTransaction
        public void testTransaction() {
            task1.execute("command1");
            task2.execute("command2");
            task3.execute("command3");
        }
    }

    @Test(expected = RuntimeException.class)
    public void test2() {
        Injector injector = Guice.createInjector(new InterceptorModule());
        SageTransactionTest1 test1 = injector.getInstance(SageTransactionTest1.class);
        test1.testTransaction();
    }



}
