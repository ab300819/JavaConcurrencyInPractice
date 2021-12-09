package org.netty.im.protocol;

public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    SerializerAlgorithm getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
