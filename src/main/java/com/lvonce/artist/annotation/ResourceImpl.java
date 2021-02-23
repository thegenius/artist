package com.lvonce.artist.annotation;

import lombok.Data;
import lombok.Getter;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

@Data
public class ResourceImpl implements Resource {

    private String name;

    private String lookup;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String lookup() {
        return lookup;
    }

    @Override
    public Class<?> type() {
        return java.lang.Object.class;
    }

    @Override
    public AuthenticationType authenticationType() {
        return AuthenticationType.CONTAINER;
    }

    @Override
    public boolean shareable() {
        return true;
    }

    @Override
    public String mappedName() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Resource.class;
    }
}
