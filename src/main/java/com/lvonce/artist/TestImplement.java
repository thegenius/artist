package com.lvonce.artist;

import com.lvonce.artist.annotation.Provider;


@Provider
public class TestImplement implements TestInterface {

    @Override
    public String sayHello() {
        return "hello";
    }
}
