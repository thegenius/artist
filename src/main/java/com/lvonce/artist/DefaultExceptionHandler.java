package com.lvonce.artist;

import com.lvonce.artist.util.InstanceUtil;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.ValidationException;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        com.lvonce.artist.Response<?> response;
        if (InstanceUtil.isValidationException(exception)) {
            ValidationException validationException = (ValidationException) exception;
            response = com.lvonce.artist.Response.ofError(600, exception.getMessage());
        } else {
            response = com.lvonce.artist.Response.ofError(500, exception.getClass().toString());
        }
        return Response.ok(response).build();
    }
}
