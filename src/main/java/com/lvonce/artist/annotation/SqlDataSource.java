package com.lvonce.artist.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Repeatable(SqlDataSources.class)
public @interface SqlDataSource {
    String value() default "";
    String name() default "";
}
