package com.lvonce.artist.transaction;

import com.lvonce.artist.annotation.TccTransaction;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TccTransactionInterceptor implements MethodInterceptor {

    boolean canFail(MethodInvocation invocation) {
        TccTransaction tccTransaction = invocation.getMethod().getAnnotation(TccTransaction.class);
        return tccTransaction.failable();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TccTaskManager manager = TccTaskManager.manager.get();
        try {
            Object result = invocation.proceed();
            manager.taskGroup.shouldConfirm = true;
            manager.confirm();
            return result;
        } catch (TransactionFail fail) {
            if (canFail(invocation)) {
                manager.cancel();
            }
            throw fail;
        }
    }
}
