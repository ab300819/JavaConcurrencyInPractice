package com.currency.demo.jdk;

/**
 * <p>数据操作</p>
 *
 * @author mason
 */
public class DataCalculation {

    private long total = 0;

    public long getTotal() {
        return total;
    }

    public void add() {
        synchronized (this) {
            total++;
        }
    }

    public void minus() {
        synchronized (this) {
            total--;
        }
    }


}
