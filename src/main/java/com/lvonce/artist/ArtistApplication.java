package com.lvonce.artist;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lvonce.artist.module.*;
import io.github.classgraph.ScanResult;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.util.*;

public class ArtistApplication {

    public static void run(String[] args) {

        ScanResult scanResult = ClasspathScanner.scan();
        ConfigModule configModule = new ConfigModule(args);
        ApplicationConfig config = configModule.getConfig();
        DataSourceModule dataSourceModule = new DataSourceModule(config, scanResult, configModule);
        MapperModule mapperModule = new MapperModule(config, scanResult);
        InterceptorModule interceptorModule = new InterceptorModule();

        ProviderModule providerModule = new ProviderModule(config, scanResult, configModule, dataSourceModule, mapperModule, interceptorModule);
        ConsumerModule consumerModule = new ConsumerModule(config, scanResult, configModule, dataSourceModule, mapperModule, interceptorModule, providerModule);
        ControllerModule controllerModule = new ControllerModule(config, scanResult, configModule, dataSourceModule, mapperModule, interceptorModule, providerModule, consumerModule);


        Injector injector = Guice.createInjector(configModule, dataSourceModule, mapperModule, interceptorModule, providerModule, consumerModule, controllerModule);

        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
        Collection<Object> providers = new ArrayList<>();
        providers.add(injector.getInstance(JsonContextResolver.class));
        Collection<Object> controllers = new ArrayList<>(controllerModule.provideController());


        deployment.getProviders().addAll(providers);
        deployment.getResources().addAll(controllers);

        NettyJaxrsServer server = new NettyJaxrsServer();
        server.setDeployment(deployment);
        server.setHostname(config.host);
        server.setPort(config.port);
        server.setRootResourcePath(config.root);
        server.setSecurityDomain(null);
        server.deploy();
        server.start();
    }
}
