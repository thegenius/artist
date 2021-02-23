package com.lvonce.artist;

import javax.ws.rs.Path;

public interface RestInterface {

    @Path("/test")
    public String sayHello();

}
