package org.netty.im.data;

public interface Serializer {

    SerializerAlgorithm getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
