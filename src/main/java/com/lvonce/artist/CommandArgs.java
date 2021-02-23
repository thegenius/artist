package com.lvonce.artist;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.IParameterSplitter;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Data
public class CommandArgs {
    public static class SemiColonSplitter implements IParameterSplitter {
        public List<String> split(String value) {
            return Arrays.asList(value.split(";"));
        }

    }

    @Parameter(names = {"--env"})
    String env = "local";

    @Parameter(names = {"--profiles"}, splitter = SemiColonSplitter.class)
    List<String> profiles = new ArrayList<>();

    @Parameter(names = {"--host"})
    String host = "0.0.0.0";

    @Parameter(names = {"--port"})
    Integer port = 9090;

    @Parameter(names = {"--root"})
    String root = "/";
}
