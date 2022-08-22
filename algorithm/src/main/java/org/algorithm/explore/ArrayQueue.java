package org.algorithm.explore;

/**
 * <p>队列数组实现</p>
 *
 * @author mason
 * @date 2022/8/22 22:53
 */
public class ArrayQueue implements Queue {

    private final String[] items;
    private final int n;
    private int head;
    private int tail;

    public ArrayQueue(int capacity) {
        this.items = new String[capacity];
        this.n = capacity;
        this.head = 0;
        this.tail = 0;
    }

    @Override
    public boolean push(String data) {
        if (tail == n) {
            return false;
        }
        items[tail] = data;
        ++tail;
        return true;
    }

    @Override
    public String take() {
        if (tail == head) {
            return null;
        }
        String data = items[head];
        ++head;
        return data;
    }
}
