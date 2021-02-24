package com.lvonce.artist.module;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.lvonce.artist.ApplicationConfig;
import com.lvonce.artist.CommandArgs;
import com.lvonce.artist.util.ResourceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.vyarus.guice.validator.ValidationModule;

import javax.inject.Named;
import java.util.LinkedHashSet;
import java.util.Properties;

@Slf4j
@AllArgsConstructor
@Getter
public class ConfigModule extends AbstractModule {

    private final ApplicationConfig config;

    public ConfigModule(String[] args) {
        this.config = resolve(args);
    }


    @Provides
    ApplicationConfig provideConfig() {
        return config;
    }

    @Provides
    @Named("environment.id")
    public String provideEnv() {
        return this.config.getEnv();
    }


    public static ApplicationConfig resolve(String[] args) {
        ApplicationConfig config = parseCommandLine(args);
        return loadResourceFiles(config);
    }


    private static ApplicationConfig parseCommandLine(String[] args) {
        CommandArgs commandArgs = new CommandArgs();
        JCommander.newBuilder().addObject(commandArgs).build().parse(args);
        ApplicationConfig configuration = new ApplicationConfig();
        configuration.setProfiles(new LinkedHashSet<>());
        configuration.getProfiles().addAll(commandArgs.getProfiles());
        configuration.getProfiles().add(commandArgs.getEnv());
        configuration.setEnv(commandArgs.getEnv());
        configuration.setHost(commandArgs.getHost());
        configuration.setPort(commandArgs.getPort());
        configuration.setRoot(commandArgs.getRoot());
        return configuration;
    }

    private static ApplicationConfig loadResourceFiles(ApplicationConfig config) {
        try {
            String defaultResourceFile = "application.yaml";
            JavaPropsMapper mapper = new JavaPropsMapper();

            String defaultContent = ResourceUtil.loadFromResources(defaultResourceFile);
            Properties properties = mapper.readValue(defaultContent, Properties.class);

            for (String profile : config.getProfiles()) {
                String fileName = "application-" + profile + ".yaml";
                String content = ResourceUtil.loadFromResources(fileName);
                Properties contentProperty = mapper.readValue(content, Properties.class);
                properties.putAll(contentProperty);
            }
            config.setProperties(properties);
        } catch (Exception ex) {
            log.error("loadResourceFiles error: {}", ex.getMessage());
        }
        return config;
    }


    @Override
    protected void configure() {
        install(new ValidationModule());

        bindConstant().annotatedWith(Names.named("env")).to(config.getEnv());
//        bind(new TypeLiteral<List<String>>()).toInstance(config.profiles);
        bindConstant().annotatedWith(Names.named("host")).to(config.getHost());
        bindConstant().annotatedWith(Names.named("port")).to(config.getPort());
        bindConstant().annotatedWith(Names.named("root")).to(config.getRoot());
        Names.bindProperties(binder(), config.getProperties());
    }
}
