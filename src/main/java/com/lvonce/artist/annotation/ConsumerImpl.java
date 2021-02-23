package com.lvonce.artist.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;


@AllArgsConstructor
public class ConsumerImpl implements Consumer, Serializable {
    private static final long serialVersionUID = 0;

    private String value;
    private String name;

    @Override
    public int hashCode() {
        return ((127 * "value".hashCode()) ^ value.hashCode()) +
                ((127 * "name".hashCode()) ^ name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Consumer)) {
            return false;
        }
        Consumer consumer = (Consumer) obj;
        return this.name.equals(consumer.name()) &&
                this.value.equals(consumer.value());
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Consumer.class;
    }
}
