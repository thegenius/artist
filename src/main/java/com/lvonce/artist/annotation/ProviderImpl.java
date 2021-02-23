package com.lvonce.artist.annotation;

import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;

@Data
public class ProviderImpl implements Provider, Serializable {
    private static final long serialVersionUID = 0;

    private String value;
    private String[] names;

    @Override
    public String value() {
        return value;
    }

    @Override
    public String[] names() {
        return names;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Provider.class;
    }
}
