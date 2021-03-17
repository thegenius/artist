package com.lvonce.artist.transaction.tcc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lvonce.artist.module.InterceptorModule;
import org.junit.Test;

import javax.inject.Inject;

public class TccTransactionTest {


    TccTransaction tccTransaction;


    public TccTransactionTest() {
        Injector injector = Guice.createInjector(
                new InterceptorModule());
        tccTransaction = injector.getInstance(TccTransaction.class);
    }


    @Test
    public void test() {
        tccTransaction.testTcc();
    }

}
