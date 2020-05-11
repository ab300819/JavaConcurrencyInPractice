package com.geektime.demo.server.handler;

import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 空闲检测 handler
 *
 * @author mason
 */
@Slf4j
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, 5, 0);
    }
}
