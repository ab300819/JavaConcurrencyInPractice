package org.netty.im.data;

import lombok.Data;

@Data
public abstract class Packet {

    private Byte version = 1;

    public abstract Command getCommand();
}
