package com.geektime.demo.common.order;


import com.geektime.demo.common.Operation;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@Slf4j
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OrderOperationResult execute() {
        log.info("order's executing startup with orderRequest: {}", toString());
        // 模拟耗时操作
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        log.info("order's executing complete");
        return new OrderOperationResult(tableId, dish, true);
    }
}
