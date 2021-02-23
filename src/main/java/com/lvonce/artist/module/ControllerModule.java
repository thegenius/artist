package com.lvonce.artist.module;

import com.google.inject.*;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.annotation.ConsumerImpl;
import io.github.classgraph.*;

import javax.inject.Named;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import java.util.*;

import static com.lvonce.artist.module.Constant.*;

public class ControllerModule extends AbstractModule {
    ApplicationConfig config;
    Injector injector;
    private List<Object> controllers = new ArrayList<>();


    @Provides
    @Named("classpath-aware-controller")
    public List<Object> provideController() {
        return controllers;
    }

    public ControllerModule(ApplicationConfig config, ScanResult scanResult, AbstractModule ...parentModule) {
        this.config = config;
        this.injector = Guice.createInjector(parentModule);
        register(scanResult);
    }

    private boolean isActive(String profile) {
        return this.config.getProfiles().contains(profile) || profile.isEmpty();
    }

    private boolean matchProvider(String[] providerNames, String consumerName) {
        for (String providerName: providerNames) {
            if (providerName.equals(consumerName)) {
                return true;
            }
        }
        return false;
    }

    private Object handleInterface(Class interfaceClass, String consumerEnv, String consumerName, ScanResult scanResult) {
        if (!interfaceClass.isInterface()) {
            return null;
        }
        if (!isActive(consumerEnv)) {
            return null;
        }
        if (interfaceClass.isMemberClass() || interfaceClass.isLocalClass()) {
            throw new RuntimeException("@Consumer annotated interface should not be member class or local class");
        }

        ClassInfoList implementList = scanResult.getClassesImplementing(interfaceClass.getCanonicalName());
        for (ClassInfo implementClass: implementList) {
            ProviderUtil.ProviderInfo providerInfo = ProviderUtil.queryProviderInfo(implementClass);
            if (!isActive(providerInfo.getValue())) {
                continue;
            }
            if (matchProvider(providerInfo.getNames(), consumerName)) {
                return injector.getInstance(implementClass.loadClass());
            }
        }
        return null;
    }



    private void register(ScanResult scanResult) {
        Set<String> classNames = new LinkedHashSet<>();
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(REST_PATH_ANNOTATION);
        for (ClassInfo classInfo: classInfoList) {
            String className = classInfo.getName();
            if (className.startsWith("org.jboss.resteasy")) {
                continue;
            }
            if (!classNames.contains(className)) {
                classNames.add(className);
                if (classInfo.isInterface()) {
                    ConsumerUtil.ConsumerInfo consumerInfo = ConsumerUtil.queryConsumerInfo(classInfo);
                    controllers.add(handleInterface(classInfo.loadClass(), consumerInfo.getValue(), consumerInfo.getName(), scanResult));
                } else {
                    controllers.add(injector.getInstance(classInfo.loadClass()));
                }
            }
        }

//        classInfoList = scanResult.getClassesWithMethodAnnotation(REST_PATH_ANNOTATION);
//        for (ClassInfo classInfo: classInfoList) {
//            String className = classInfo.getName();
//            if (className.startsWith("org.jboss.resteasy")) {
//                continue;
//            }
//            if (!classNames.contains(className)) {
//                classNames.add(className);
//                controllers.add(injector.getInstance(classInfo.loadClass()));
//            }
//        }
    }
}
