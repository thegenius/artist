package com.lvonce.artist.sql.mybatis;


import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Singleton
public class DataSourceProvider implements Provider<MultiContainer<DataSource>> {

    private final MultiContainer<DataSource> sources = new MultiContainer<>();

    @Inject
    public DataSourceProvider(@Named("classpath-aware-data-source") Map<String, DataSource> classpathAwareDataSource) {
        sources.getAll().putAll(classpathAwareDataSource);
    }

    @Override
    public MultiContainer<DataSource> get() {
        return sources;
    }

}
