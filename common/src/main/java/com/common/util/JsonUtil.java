package com.common.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * JSON 工具包
 *
 * @author mason
 */
public final class JsonUtil {

    private static final String EMPTY_STR = "";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    private JsonUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * convert JSON string to object
     *
     * @param json  json string
     * @param clazz object class
     * @param <T>   object
     * @return object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (clazz != null && StringUtils.isNotBlank(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * convert JSON object to string
     *
     * @param object JSON object
     * @return string
     */
    public static String toJson(Object object) {
        if (object == null) {
            return EMPTY_STR;
        }

        if (object instanceof String) {
            return (String) object;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return EMPTY_STR;
    }

}
