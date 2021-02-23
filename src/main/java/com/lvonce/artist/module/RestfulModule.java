package com.lvonce.artist.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.lvonce.artist.ApplicationConfig;
import io.github.classgraph.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.lvonce.artist.module.Constant.*;


@Slf4j
public class RestfulModule extends AbstractModule {

    @Data
    public static class RestfulElement {
        Class<?> restInterface;
        Map<String, Class<?>> restImplements = new LinkedHashMap<>();
    }


    private final ApplicationConfig config;
    private final Injector injector;
    private final List<RestfulElement> restfulElements = new ArrayList<>();

    public RestfulModule(ApplicationConfig config, ScanResult result, AbstractModule... parentModules) {
        this.config = config;
        this.injector = Guice.createInjector(parentModules);
        extractResources(result);
    }

    private boolean isActive(String profile) {
        return this.config.getProfiles().contains(profile) || profile.isEmpty();
    }

    private void extractResources(ScanResult scanResult) {
        ClassInfoList classInfoList = scanResult.getClassesWithMethodAnnotation(REST_PATH_ANNOTATION);
        for (ClassInfo classInfo : classInfoList) {
            if (!classInfo.isInterface()) {
                log.warn("@Path must be annotated on interface: {} is not!", classInfo.getName());
                continue;
            }
            RestfulElement element = new RestfulElement();

            Class<?> interfaceClass = classInfo.loadClass();
            element.restInterface = interfaceClass;

            ClassInfoList implementClasses = scanResult.getClassesImplementing(interfaceClass.getName());
            for (ClassInfo implementClass : implementClasses) {
                AnnotationInfo profileAnnotation = implementClass.getAnnotationInfo(PROFILE_ANNOTATION);
                if (profileAnnotation == null) {
                    element.restImplements.put("", implementClass.loadClass());
                    continue;
                }
                String env = (String) profileAnnotation.getParameterValues().getValue("value");
                String[] names = (String[]) profileAnnotation.getParameterValues().getValue("names");
                if (!isActive(env)) {
                    continue;
                }
                for (String name : names) {
                    element.restImplements.put(name, implementClass.loadClass());
                }
            }

            if (!element.getRestImplements().isEmpty()) {
                restfulElements.add(element);
            }
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        for (RestfulElement element : restfulElements) {
            Class interfaceClass = element.getRestInterface();
            for (Map.Entry<String, Class<?>> entry : element.getRestImplements().entrySet()) {
                String key = entry.getKey();
                Class implementClass = entry.getValue();
                Object obj = injector.getInstance(implementClass);
                if (key.isEmpty()) {
                    bind(interfaceClass).toInstance(obj);
                } else {
                    bind(interfaceClass).annotatedWith(Names.named(key)).toInstance(obj);
                }
            }
        }
    }
}
