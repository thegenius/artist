package com.lvonce.artist.annotation;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Consumers.class)
public @interface Consumer {
    String value() default "";
    String name() default "";
}
