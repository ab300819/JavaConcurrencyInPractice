package org.netty.rpc.core.common;

import java.io.Serializable;

import org.netty.rpc.core.common.constants.RpcConstants;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 22:57
 */
public class RpcProtocol implements Serializable {
    private static final long serialVersionUID = -3798769478479610634L;

    private short magicNumber = RpcConstants.MAGIC_NUMBER;

    private int contentLength;

    private byte[] content;

    public RpcProtocol() {
    }

    public RpcProtocol(byte[] content) {
        this.content = content;
        this.contentLength = content.length;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
