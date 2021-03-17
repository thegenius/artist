package com.lvonce.artist.module;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;
import com.lvonce.artist.annotation.SagaTransaction;
import com.lvonce.artist.annotation.TccTransaction;
import com.lvonce.artist.transaction.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class InterceptorModule extends AbstractModule {

    static class MethodNameMatcher extends AbstractMatcher<Method> {
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
                Matchers.subclassesOf(SagaTask.class),
                new MethodNameMatcher(),
                new SagaTaskInterceptor()
        );

        bindInterceptor(
                Matchers.subclassesOf(TccTask.class),
                new MethodNameMatcher(),
                new TccTaskInterceptor()
        );

        bindInterceptor(
                Matchers.any(),
                Matchers.annotatedWith(SagaTransaction.class),
                new SagaTransactionInterceptor()
        );

        bindInterceptor(
                Matchers.any(),
                Matchers.annotatedWith(TccTransaction.class),
                new TccTransactionInterceptor()
        );
    }
}
