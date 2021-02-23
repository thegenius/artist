package com.lvonce.artist.module;

import com.google.inject.*;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.annotation.ConsumerImpl;
import com.lvonce.artist.annotation.ResourceImpl;
import io.github.classgraph.*;
import lombok.extern.slf4j.Slf4j;

import static com.lvonce.artist.module.Constant.*;

@Slf4j
public class ConsumerModule extends AbstractModule {
    ApplicationConfig config;
    Injector injector;
    ScanResult scanResult;

    public ConsumerModule(ApplicationConfig config, ScanResult scanResult, AbstractModule ... parentModule) {
        this.config = config;
        this.scanResult = scanResult;
        this.injector = Guice.createInjector(parentModule);
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

    @SuppressWarnings("unchecked")
    private void bindInterface(Class interfaceClass, String consumerEnv, String consumerName, ScanResult scanResult) {
        if (!interfaceClass.isInterface()) {
            return;
        }
        if (interfaceClass.isMemberClass() || interfaceClass.isLocalClass()) {
            throw new RuntimeException("@Consumer annotated interface should not be member class or local class");
        }

        ClassInfoList implementList = scanResult.getClassesImplementing(interfaceClass.getCanonicalName());
        for (ClassInfo implementClass: implementList) {
            AnnotationInfo providerInfo = implementClass.getAnnotationInfo(PROVIDER_ANNOTATION);
            if (providerInfo == null) {
                continue;
            }
            String providerEnv = (String) providerInfo.getParameterValues().getValue("value");
            String[] providerNames = (String[]) providerInfo.getParameterValues().getValue("names");
            if (!isActive(providerEnv)) {
                continue;
            }
            if (matchProvider(providerNames, consumerName)) {
                Class implementClazz = implementClass.loadClass();
                ConsumerImpl consumer = new ConsumerImpl(consumerEnv, consumerName);
                bind(interfaceClass).annotatedWith(consumer).to(implementClazz).in(Scopes.SINGLETON);
            }
        }
    }


    private void configField() {
        ClassInfoList classInfoList = scanResult.getClassesWithFieldAnnotation(CONSUMER_ANNOTATION);
        for (ClassInfo classInfo: classInfoList) {
            FieldInfoList fieldInfoList = classInfo.getFieldInfo();
            for (FieldInfo fieldInfo: fieldInfoList) {
                AnnotationInfoList annotationInfoList = fieldInfo.getAnnotationInfoRepeatable(CONSUMER_ANNOTATION);
                for (AnnotationInfo annotationInfo: annotationInfoList) {
                    String consumerEnv = (String) annotationInfo.getParameterValues().getValue("value");
                    if (!isActive(consumerEnv)) {
                        continue;
                    }
                    String consumerName = (String) annotationInfo.getParameterValues().getValue("name");
                    Class interfaceClass = fieldInfo.loadClassAndGetField().getType();
                    bindInterface(interfaceClass, consumerEnv, consumerName, scanResult);
                }
            }
        }
    }

    private void configMethodParameter() {
        ClassInfoList classInfoList = scanResult.getClassesWithMethodParameterAnnotation(CONSUMER_ANNOTATION);
        for (ClassInfo classInfo: classInfoList) {
            MethodInfoList methodInfoList = classInfo.getMethodAndConstructorInfo();
            for (MethodInfo methodInfo: methodInfoList) {
                MethodParameterInfo[] methodParameterInfos = methodInfo.getParameterInfo();
                for (MethodParameterInfo paramInfo: methodParameterInfos) {
                    AnnotationInfoList annotationInfoList = paramInfo.getAnnotationInfoRepeatable(CONSUMER_ANNOTATION);
                    for (AnnotationInfo annotationInfo: annotationInfoList) {
                        String consumerEnv = (String) annotationInfo.getParameterValues().getValue("value");
                        if (!isActive(consumerEnv)) {
                            continue;
                        }
                        String consumerName = (String) annotationInfo.getParameterValues().getValue("name");
                        String interfaceName = paramInfo.getTypeSignatureOrTypeDescriptor().toString();
                        Class interfaceClass = scanResult.loadClass(interfaceName, true);
                        if (interfaceClass != null) {
                            bindInterface(interfaceClass, consumerEnv, consumerName, scanResult);
                        }
                    }
                }
            }
        }
    }




    @SuppressWarnings("unchecked")
    private void configConsumer() {
        ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(CONSUMER_ANNOTATION);
        for (ClassInfo classInfo: classInfoList) {
            if (!classInfo.isInterface()) {
                continue;
            }
            AnnotationInfo consumerInfo = classInfo.getAnnotationInfo(CONSUMER_ANNOTATION);
            String consumerEnv = (String) consumerInfo.getParameterValues().getValue("value");
            String consumerName = (String) consumerInfo.getParameterValues().getValue("name");
            if (!isActive(consumerEnv)) {
                continue;
            }
            Class interfaceClass = classInfo.loadClass();
            bindInterface(interfaceClass, consumerEnv, consumerName, scanResult);
        }
    }



    @Override
    protected void configure() {
        configConsumer();
        configField();
        configMethodParameter();
    }
}
