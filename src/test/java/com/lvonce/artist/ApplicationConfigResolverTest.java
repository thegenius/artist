package com.lvonce.artist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lvonce.artist.module.ConfigModule;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

public class ApplicationConfigResolverTest {

    @Data
    public static class TestProperty {
        @Inject
        @Named("prop1")
        String hello;
    }


    @Test
    public void test() {
        String[] args = {"--env", "local"};
        ConfigModule configModule = new ConfigModule(args);
        Injector injector = Guice.createInjector(configModule);
        TestProperty testProperty = injector.getInstance(TestProperty.class);
        Assert.assertEquals( "hello world", testProperty.hello);

    }
}
