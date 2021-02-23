package com.lvonce.artist.module;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.sql.mybatis.MapperProvider;
import io.github.classgraph.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.inject.util.Providers.guicify;
import static com.lvonce.artist.module.Constant.*;


@Slf4j
public class MapperModule extends AbstractModule {


    private final ScanResult scanResult;
    private final ApplicationConfig config;
    private final Map<String, Class<BaseMapper>> mappers = new LinkedHashMap<>();
    private final List<String> xmlMappers = new ArrayList<>();


    public MapperModule(ApplicationConfig config, ScanResult scanResult) {
        this.config = config;
        this.scanResult = scanResult;
        register(scanResult);
    }

    @Provides
    @Named("classpath-aware-mapper")
    public Map<String, Class<BaseMapper>> provideMappers() {
        return mappers;
    }


    @Provides
    @Named("classpath-aware-xml-mapper")
    public List<String> provideXMLMappers() {
        return xmlMappers;
    }

    private boolean isActive(String profile) {
        return this.config.getProfiles().contains(profile) || profile.isEmpty();
    }

    private void handleMapper(ClassInfo classInfo) {
        AnnotationInfoList annotationInfo = classInfo.getAnnotationInfo();
        if (annotationInfo == null) {
            return;
        }
        AnnotationInfoList sourceAnnotations = annotationInfo.getRepeatable(SQL_DATA_SOURCE_ANNOTATION);
        for (AnnotationInfo info : sourceAnnotations) {
            AnnotationParameterValueList values = info.getParameterValues();
            String name = (String) values.getValue("name");
            String env = (String) values.getValue("value");
            if (isActive(env)) {
                Class<BaseMapper> mapperClass = classInfo.loadClass(BaseMapper.class);
                mappers.put(name, mapperClass);
            }
        }
    }

    private void handleXMLMapper(Resource resource) {
        try {
            log.info("xml mapper: {}", resource.getPathRelativeToClasspathElement());
            xmlMappers.add(resource.getPathRelativeToClasspathElement());
        } catch (Exception ex) {
            log.error("handleXMLMapper error: {}", ex.getMessage());
        }
    }


    public void register(ScanResult scanResult) {
        ClassInfoList mapperList = scanResult.getClassesImplementing(MAPPER_INTERFACE_NAME);
        mapperList.forEach(this::handleMapper);
        log.info("mapper list: {}", mapperList);


        ClassGraph xmlMapperGraph = new ClassGraph().acceptPathsNonRecursive("mappers");
        try (ScanResult result = xmlMapperGraph.scan()) {
            ResourceList resources = result.getResourcesWithExtension(".xml");
            for (Resource resource : resources) {
                handleXMLMapper(resource);
            }
        }
    }


    private <T> void bindMapper(String mapperKey, Class<T> mapperClass) {
        MapperProvider<T> provider = new MapperProvider<>(mapperKey, mapperClass);
        bind(mapperClass).toProvider(guicify(provider)).in(Scopes.SINGLETON);
    }

    @Override
    protected void configure() {
        mappers.forEach(this::bindMapper);
    }
}
