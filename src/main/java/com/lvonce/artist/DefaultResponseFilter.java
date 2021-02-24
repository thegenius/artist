package com.lvonce.artist;

import com.lvonce.artist.util.JsonUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class DefaultResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Object entity = responseContext.getEntity();
        if (entity instanceof Response) {
            return;
        }
        MediaType mediaType = responseContext.getMediaType();
        if (mediaType == null || mediaType.equals(MediaType.APPLICATION_JSON_TYPE)) {
            Response<?> responseBody = Response.of(entity);
            String body = JsonUtil.toJson(responseBody);
            responseContext.setEntity(body);
        }
    }
}
