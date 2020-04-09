package com.geektime.demo.common.order;


import com.geektime.demo.common.Operation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        log.info("order's executing startup with orderRequest: {}" , toString());
        //execute order logic
        log.info("order's executing complete");
        return new OrderOperationResult(tableId, dish, true);
    }
}
