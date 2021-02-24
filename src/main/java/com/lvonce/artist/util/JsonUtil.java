package com.lvonce.artist.util;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static<T> String toJson(T data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            return null;
        }
    }

    public static<T> String toJson(T data, String defaultStr) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            return defaultStr;
        }
    }
}
