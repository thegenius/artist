package com.lvonce.artist.example.api;

import com.lvonce.artist.annotation.Consumer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@Consumer
public interface TestInterface {

    @Path("/hello")
    @GET
    @Produces("application/json")
    String sayHello(String name);

    @Path("/test")
    @GET
    @Produces("application/json")
    String sayTest(String name);

}
