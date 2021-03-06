package com.lvonce.artist.sql;

import com.lvonce.artist.annotation.SqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@SqlDataSource(value = "local", name = "h2-mem")
public class H2DataSourceProvider implements Provider<DataSource> {
    private void init(DataSource dataSource) {
        try {
            Connection conn = dataSource.getConnection();
            String table = "CREATE TABLE if not exists person(\n" +
                    " id int not null,\n" +
                    " name varchar(20) null,\n" +
                    " age int unsigned null,\n" +
                    " primary key (id),\n" +
                    " key test_idx_name_age(name, age)\n" +
                    ");";


            PreparedStatement createStatement = conn.prepareStatement(table);
            createStatement.executeUpdate();

            table = "CREATE TABLE IF NOT EXISTS `task_info`(\n" +
                    "task_uuid VARCHAR(128) NOT NULL, \n" +
                    "name VARCHAR(128) NOT NULL, \n" +
                    "params TEXT NOT NULL, \n" +
                    "status TINYINT NOT NULL,\n"+

                    "executor_uuid VARCHAR(128) NOT NULL, \n"+
                    "expire_time DATETIME NOT NULL, \n"+
                    "create_time DATETIME NOT NULL, \n"+
                    "update_time DATETIME NOT NULL, \n"+
                    "preempt_count INT NOT NULL DEFAULT 0, \n"+

                    "PRIMARY KEY(`task_uuid`)" +
                    ");";
            createStatement = conn.prepareStatement(table);
            createStatement.executeUpdate();



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DataSource get() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:dbc2m;DATABASE_TO_UPPER=false;MODE=MYSQL");
        config.setUsername("sa");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", true);
        DataSource dataSource = new HikariDataSource(config);
        init(dataSource);
        return dataSource;
    }
}
