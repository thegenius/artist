package com.lvonce.artist.module;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.lvonce.artist.annotation.SagaTransaction;
import com.lvonce.artist.transaction.SagaTaskInterceptor;
import com.lvonce.artist.transaction.SagaTransactionInterceptor;
import com.lvonce.artist.transaction.SagaTransactionManager;
import com.lvonce.artist.transaction.Task;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class InterceptorModule extends AbstractModule {

    class MethodNameMatcher extends AbstractMatcher<Method> {
        @Override
        public boolean matches(Method method) {
            if (method.isSynthetic()) {
                return false;
            }
            return method.getName().equals("execute");
        }
    }

    @Override
    protected void configure() {


        bindInterceptor(
                Matchers.subclassesOf(Task.class),
                new MethodNameMatcher(),
                new SagaTaskInterceptor()
        );

        bindInterceptor(
                Matchers.any(),
                Matchers.annotatedWith(SagaTransaction.class),
                new SagaTransactionInterceptor()
        );
    }
}
