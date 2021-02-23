package com.lvonce.artist;

import com.lvonce.artist.annotation.Consumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@Consumer
public interface TestInterface {

    @Path("/hello")
    @GET
    String sayHello();

}
