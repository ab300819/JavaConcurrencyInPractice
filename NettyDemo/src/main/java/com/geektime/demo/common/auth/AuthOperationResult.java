package com.geektime.demo.common.auth;

import com.geektime.demo.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {
    private final boolean passAuth;

}
