package com.studentportal.interceptor;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.InvocationContext;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;



public class ExceptionInterceptor implements MethodInterceptor<Object, Object> {
    @Override
    public @Nullable Object intercept(InvocationContext<Object, Object> context) {
        return MethodInterceptor.super.intercept(context);
    }

    @Override
    public @Nullable Object intercept(MethodInvocationContext<Object, Object> context) {
        return null;
    }
}
