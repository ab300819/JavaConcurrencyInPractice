package org.netty.im.protocol;

import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
