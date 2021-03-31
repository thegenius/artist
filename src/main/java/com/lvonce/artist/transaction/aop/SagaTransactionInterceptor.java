package com.lvonce.artist.transaction.aop;

import com.lvonce.artist.transaction.mannager.SagaTaskManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class SagaTransactionInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        SagaTaskManager manager = SagaTaskManager.manager.get();
        try {
            Object result = invocation.proceed();
            manager.confirm();
            return result;
        } catch (Throwable t) {
            manager.cancel();
            throw t;
        }
    }
}
