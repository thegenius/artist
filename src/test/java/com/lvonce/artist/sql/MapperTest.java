package com.lvonce.artist.sql;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.ClasspathScanner;
import com.lvonce.artist.module.ConfigModule;
import com.lvonce.artist.module.DataSourceModule;
import com.lvonce.artist.module.MapperModule;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;


@Slf4j
public class MapperTest {
    @Test
    public void test() {
        ScanResult scanResult = ClasspathScanner.scan();
        String[] args = {"--env", "local"};
        ConfigModule configModule = new ConfigModule(args);
        ApplicationConfig config = configModule.getConfig();
        DataSourceModule dataSourceModule = new DataSourceModule(config, scanResult, configModule);
        MapperModule mapperModule = new MapperModule(config, scanResult);
        Injector injector = Guice.createInjector(configModule, dataSourceModule, mapperModule);





        PersonMapper personMapper = injector.getInstance(PersonMapper.class);
        Person person = new Person();
        person.setId(1);
        person.setName("wang");
        person.setAge(32);
        personMapper.insert(person);

        Person person2 = new Person();
        person2.setId(2);
        person2.setName("tu");
        person2.setAge(32);
        personMapper.insert(person2);

        Person result = personMapper.selectById(1);
        Assert.assertEquals(person.getName(), result.getName());

        Person result2 = personMapper.getStudentById(1);
        Assert.assertEquals(person.getName(), result2.getName());

        Person result3 = personMapper.getStudentByName("wang");
        Assert.assertEquals(person.getName(), result3.getName());

        Person result4 = personMapper.selectByName("tu");
        Assert.assertEquals(person2.getName(), result4.getName());

        Page<Person> pages = PageHelper.startPage(1, 1, true)
                .doSelectPage(()->personMapper.selectByAge(32));
        Assert.assertEquals(pages.getPages(), 2);

    }
}
