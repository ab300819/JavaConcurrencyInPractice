package com.geektime.demo.common.order;

import com.geektime.demo.common.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOperationResult extends OperationResult {

    private int tableId;
    private String dish;
    private boolean complete;

}
