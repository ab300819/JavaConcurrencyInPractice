package com.geektime.demo.common.keepalive;

import com.geektime.demo.common.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeepaliveOperationResult extends OperationResult {

    private long time;

}
