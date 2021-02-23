package com.lvonce.artist.module;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import lombok.Data;

import static com.lvonce.artist.module.Constant.PROVIDER_ANNOTATION;

public class ProviderUtil {
    @Data
    public static class ProviderInfo {
        private String value;
        private String[] names;
    }

    public static ProviderInfo queryProviderInfo(ClassInfo classInfo) {
        AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(PROVIDER_ANNOTATION);
        ProviderInfo providerInfo = new ProviderInfo();
        if (annotationInfo == null) {
            providerInfo.value = "";
            providerInfo.names = new String[]{""};
        } else {
            providerInfo.value = (String) annotationInfo.getParameterValues().getValue("value");
            providerInfo.names = (String[]) annotationInfo.getParameterValues().getValue("names");
        }
        return providerInfo;
    }
}
