package com.lvonce.artist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lvonce.artist.module.ConfigModule;
import com.lvonce.artist.module.RestfulModule;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

public class RestfulModuleTest {

//    @Test(expected = RuntimeException.class)
//    public void test() {
//        String[] args = {"--env", "local"};
//        ConfigModule configModule = new ConfigModule(args);
//        ScanResult scanResult = ClasspathScanner.scan();
//
//        RestfulModule restfulModule = new RestfulModule(configModule.getConfig(), scanResult, configModule);
//        Injector injector = Guice.createInjector(restfulModule);
//        RestClassDirect restClassDirect = injector.getInstance(RestClassDirect.class);
//    }

    @Getter
    public static class InjectClass {
        @Inject
        @Named("dev")
        RestInterface restInterface;
    }


    @Test
    public void test2() {
        String[] args = {"--env", "local"};
        ConfigModule configModule = new ConfigModule(args);
        ScanResult scanResult = ClasspathScanner.scan();

        RestfulModule restfulModule = new RestfulModule(configModule.getConfig(), scanResult, configModule);
        Injector injector = Guice.createInjector(restfulModule);
        RestInterface restInterface = injector.getInstance(RestInterface.class);

        String result = restInterface.sayHello();
        Assert.assertEquals("hello world", result);
    }

    @Test
    public void test3() {
        String[] args = {"--env", "local"};
        ConfigModule configModule = new ConfigModule(args);
        ScanResult scanResult = ClasspathScanner.scan();

        RestfulModule restfulModule = new RestfulModule(configModule.getConfig(), scanResult, configModule);
        Injector injector = Guice.createInjector(restfulModule);
        InjectClass obj = injector.getInstance(InjectClass.class);

        String result = obj.getRestInterface().sayHello();
        Assert.assertEquals("hello implements", result);
    }


}
