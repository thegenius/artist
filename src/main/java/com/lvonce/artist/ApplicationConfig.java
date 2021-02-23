package com.lvonce.artist;

import lombok.Data;

import java.util.Properties;
import java.util.Set;

@Data
public class ApplicationConfig {
    String env;
    Set<String> profiles;
    String host;
    Integer port;
    String root;
    Properties properties;
}
