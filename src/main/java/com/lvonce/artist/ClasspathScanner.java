package com.lvonce.artist;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class ClasspathScanner {
    public static ScanResult scan() {
        ClassGraph graph = new ClassGraph().enableAllInfo().acceptPackages("*");
        return graph.scan();
    }
}
