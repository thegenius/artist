package com.lvonce.artist;

import org.jboss.resteasy.core.ExceptionHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        com.lvonce.artist.Response<?> response = com.lvonce.artist.Response.ofError(500, exception.getMessage());
        return Response.status(200).entity(response).build();
    }
}
