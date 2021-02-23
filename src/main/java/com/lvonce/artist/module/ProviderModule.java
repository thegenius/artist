package com.lvonce.artist.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.annotation.Consumer;
import com.lvonce.artist.annotation.ConsumerImpl;
import com.lvonce.artist.annotation.ResourceImpl;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import javax.annotation.Resource;
import javax.inject.Named;

import java.lang.annotation.Annotation;

import static com.lvonce.artist.module.Constant.*;

public class ProviderModule extends AbstractModule {
    ApplicationConfig config;
    Injector injector;
    ScanResult scanResult;

    public ProviderModule(ApplicationConfig config, ScanResult scanResult, AbstractModule ... parentModule) {
        this.config = config;
        this.scanResult = scanResult;
        this.injector = Guice.createInjector(parentModule);
    }

    private boolean isActive(String profile) {
        return this.config.getProfiles().contains(profile);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(PROVIDER_ANNOTATION);
        for (ClassInfo classInfo: classInfoList) {
            AnnotationInfo providerInfo = classInfo.getAnnotationInfo(PROVIDER_ANNOTATION);
            String env = (String) providerInfo.getParameterValues().getValue("value");
            String[] names = (String[]) providerInfo.getParameterValues().getValue("names");
            if (isActive(env)) {
                Class resourceClass = classInfo.loadClass();
                if (names.length == 0) {
                    bind(resourceClass)
                            .toInstance(injector.getInstance(resourceClass));
                } else {
                    for (String name: names) {
                        bind(resourceClass)
                                .annotatedWith(Names.named(name))
                                .toInstance(injector.getInstance(resourceClass));

                        Consumer consumer = new ConsumerImpl(env, name);
                        bind(resourceClass)
                                .annotatedWith(consumer)
                                .toInstance(injector.getInstance(resourceClass));
                    }
                }
            }
        }




    }
}
