package com.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 工具类
 *
 * @author mason
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 日期反序列化配置
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T toObject(String json, TypeReference<T> reference) {
        if (reference == null || StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, reference);
        } catch (IOException e) {
            log.error("fail to convert to json. {}", e.getMessage());
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> reference) {
        if (reference == null || StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, reference);
        } catch (IOException e) {
            log.error("fail to convert to json. {}", e.getMessage());
        }
        return null;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("fail to  convert to json string. {}", e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public static byte[] toBytes(Object obj) {
        String jsonStr = toString(obj);
        if (StringUtils.isBlank(jsonStr)) {
            return new byte[0];
        }

        return jsonStr.getBytes(StandardCharsets.UTF_8);
    }

}
