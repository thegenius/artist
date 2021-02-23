package com.lvonce.artist.sql.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import javax.inject.Provider;

public class SqlSessionManagerProvider implements Provider<MultiContainer<SqlSessionManager>> {

    private final MultiContainer<SqlSessionManager> managers = new MultiContainer<>();

    @Inject
    public SqlSessionManagerProvider(MybatisConfigProvider configProvider) {
        configProvider.get().getAll().forEach((key, value) -> {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(value);

            SqlSessionManager manager = SqlSessionManager.newInstance(factory);
            managers.set(key, manager);
        });
    }

    @Override
    public MultiContainer<SqlSessionManager> get() {
        return this.managers;
    }
}
