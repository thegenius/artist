package com.lvonce.artist.annotation;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Repeatable(Providers.class)
public @interface Provider {
    String value() default "";
    String[] names() default {""};
}
