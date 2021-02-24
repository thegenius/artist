package com.lvonce.artist;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lvonce.artist.util.JsonUtil;
import org.jboss.resteasy.core.ExceptionHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        com.lvonce.artist.Response<?> response = com.lvonce.artist.Response.ofError(500, exception.getClass().toString());
        String body = JsonUtil.toJson(response);
        return Response.status(200).entity(body).build();
    }
}
