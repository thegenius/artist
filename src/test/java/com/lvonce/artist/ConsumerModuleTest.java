package com.lvonce.artist;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lvonce.artist.annotation.Consumer;
import com.lvonce.artist.annotation.Provider;
import com.lvonce.artist.module.ConfigModule;
import com.lvonce.artist.module.ConsumerModule;
import com.lvonce.artist.module.ProviderModule;
import io.github.classgraph.ScanResult;
import org.junit.Assert;
import org.junit.Test;

public class ConsumerModuleTest {

    @Provider(value="local", names={"test", "dev"})
    public static class TestProvider implements RestInter {
        public String hello() {
            return "hello";
        }
    }

    public static class TestConsumer {
        @Inject
        @Consumer(value="local", name="test")
        RestInter provider;
    }

    public static class TestConsumer2 {
        RestInter provider;

        @Inject
        public TestConsumer2(@Consumer(value="local", name="dev")RestInter provider) {
            this.provider = provider;
        }

    }



    @Test
    public void test() {
        String[] args = {"--env", "local"};
        ConfigModule configModule = new ConfigModule(args);
        ScanResult scanResult = ClasspathScanner.scan();

        ConsumerModule consumerModule = new ConsumerModule(configModule.getConfig(), scanResult, configModule);
        Injector injector = Guice.createInjector(consumerModule);

        TestConsumer obj = injector.getInstance(TestConsumer.class);


        String result = obj.provider.hello();
        Assert.assertEquals("hello", result);

        TestConsumer2 consumer2 = injector.getInstance(TestConsumer2.class);
        String result2 = consumer2.provider.hello();
        Assert.assertEquals("hello", result2);
    }
}
