package com.lvonce.artist.module;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.lvonce.artist.ApplicationConfig;
import io.github.classgraph.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.lvonce.artist.module.Constant.*;

@Slf4j
@Getter
public class DataSourceModule extends AbstractModule {

    private final ApplicationConfig config;
    private final Injector injector;
    private final Map<String, DataSource> sourceMap = new LinkedHashMap<>();

    private final List<Object> controllers = new ArrayList<>();

    public DataSourceModule(ApplicationConfig config, ScanResult result, AbstractModule... parentModules) {
        this.config = config;
        this.injector = Guice.createInjector(parentModules);
        register(result);
    }

    @Provides
    @Named("classpath-aware-data-source")
    public Map<String, DataSource> provideSqlDataSources() {
        return sourceMap;
    }


    private boolean isActive(String profile) {
        return this.config.getProfiles().contains(profile) || profile.isEmpty();
    }


    @SuppressWarnings("unchecked")
    private void handleDataSource(ClassInfo classInfo) {
        AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(SQL_DATA_SOURCE_ANNOTATION);
        AnnotationParameterValueList values = annotationInfo.getParameterValues();
        String name = (String) values.getValue("name");
        String env = (String) values.getValue("value");
        if (isActive(env)) {
            Provider<DataSource> provider
                    = (Provider<DataSource>) injector.getInstance(classInfo.loadClass());
            DataSource dataSource = provider.get();
            sourceMap.put(name, dataSource);
        }
    }

    public void register(ScanResult scanResult) {
        ClassInfoList list
                = scanResult.getClassesWithAnnotation(SQL_DATA_SOURCE_ANNOTATION);
        for (ClassInfo info : list) {
            if (info.implementsInterface(PROVIDER_INTERFACE_NAME)) {
                handleDataSource(info);
            }
        }
    }
}
