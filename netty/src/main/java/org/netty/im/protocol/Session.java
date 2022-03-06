package org.netty.im.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Session {

    private String userId;
    private String userName;

}
