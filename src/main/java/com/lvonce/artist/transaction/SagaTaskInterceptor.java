package com.lvonce.artist.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class SagaTaskInterceptor implements MethodInterceptor {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object invoke(MethodInvocation invocation) throws Throwable {
        SagaTaskManager manager = SagaTaskManager.manager.get();
        Task task = (Task)invocation.getThis();
        Object[] args = invocation.getArguments();
        manager.execute(task, args[0]);
        return invocation.proceed();
    }
}
