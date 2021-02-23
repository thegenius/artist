package com.lvonce.artist.module;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import lombok.Data;

import static com.lvonce.artist.module.Constant.CONSUMER_ANNOTATION;
import static com.lvonce.artist.module.Constant.PROVIDER_ANNOTATION;

public class ConsumerUtil {
    @Data
    public static class ConsumerInfo {
        private String value;
        private String name;
    }

    public static ConsumerInfo queryConsumerInfo(ClassInfo classInfo) {
        AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(CONSUMER_ANNOTATION);
        ConsumerInfo consumerInfo = new ConsumerInfo();
        if (annotationInfo == null) {
            consumerInfo.value = "";
            consumerInfo.name = "";
        } else {
            consumerInfo.value = (String) annotationInfo.getParameterValues().getValue("value");
            consumerInfo.name = (String) annotationInfo.getParameterValues().getValue("name");
        }
        return consumerInfo;
    }
}
