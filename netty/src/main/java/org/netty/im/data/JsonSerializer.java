package org.netty.im.data;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import com.common.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.netty.im.data.SerializerAlgorithm.JSON;

/**
 * <p>Json 序列化工具</p>
 *
 * @author mason
 */
public class JsonSerializer implements Serializer {

    @Override
    public SerializerAlgorithm getSerializerAlgorithm() {
        return JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JsonUtil.toBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        String jsonStr = new String(bytes, StandardCharsets.UTF_8);
        return JsonUtil.toObject(jsonStr, new TypeReference<T>() {
        });
    }
}
