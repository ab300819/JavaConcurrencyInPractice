package com.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static boolean is(String json) {
        if (json != null && json.length() > 1) {
            try {
                int count = 0;
                final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
                while (parser.nextToken() != null && count < 10000) {
                    count++;
                }
                return true;
            } catch (Throwable e) {
            }
        }

        return false;
    }

    public static JsonNode read(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean not(String json) {
        return !is(json);
    }

    public static String quote(String source) {
        if (StringUtils.isNotEmpty(source)) {
            return new String(BufferRecyclers.getJsonStringEncoder().quoteAsString(source));
        }
        return source;
    }

    public static <T> T toObject(Class<T> clazz, String json) {
        if (clazz != null && StringUtils.isNotEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static <T> List<T> toList(Class<T> clazz, String json) {
        if (clazz != null && StringUtils.isNotEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    public static <T> Map<String, T> toMap(String json) {
        if (StringUtils.isNotBlank(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, T>>() {
                });
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> toStringMap(String json) {
        return toMap(json);
    }

    public static String toString(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return (String) obj;
            }
            try {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public static byte[] toBytes(Object obj) {
        if (obj != null) {
            try {
                return OBJECT_MAPPER.writeValueAsBytes(obj);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

}
