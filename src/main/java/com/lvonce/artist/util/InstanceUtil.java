package com.lvonce.artist.util;

import com.lvonce.artist.Response;
import jakarta.validation.ValidationException;

@SuppressWarnings("unused")
public class InstanceUtil {

    @SuppressWarnings("unchecked")
    public static<T> boolean isInstance(Object obj, Class<T> clazz) {
        try {
            T instance = (T) obj;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static<T> boolean isResponse(Object obj) {
        try {
            Response<?> instance = (Response<?>) obj;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isJavaxResponse(Object obj) {
        try {
            javax.ws.rs.core.Response instance = (javax.ws.rs.core.Response) obj;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isValidationException(Object obj) {
        try {
            ValidationException ex = (ValidationException) obj;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
