package org.netty.im.data;

import static org.netty.im.data.SerializerAlgorithm.JSON;

public class JsonSerializer implements Serializer {

    @Override
    public SerializerAlgorithm getSerializerAlgorithm() {
        return JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
