package com.lvonce.artist.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TccTransactionInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TccTaskManager manager = TccTaskManager.manager.get();
        try {
            Object result = invocation.proceed();
            manager.taskGroup.shouldConfirm = true;
            manager.confirm();
            return result;
        } catch (Throwable t) {
            manager.cancel();
            throw t;
        }
    }
}
