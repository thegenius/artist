package com.lvonce.artist;

import com.lvonce.artist.annotation.Provider;

@Provider(value = "local", names = {"dev"})
@Provider(value = "prod", names= {"test"})
public class RestImplementWithName implements RestInterface {
    @Override
    public String sayHello() {
        return "hello implements";
    }
}
