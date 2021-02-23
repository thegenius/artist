package com.lvonce.artist;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HealthController {
    @GET
    @Path("/health")
    public String health() {
        return "{ \"status\": \"ok\" }";
    }
}
