package org.algorithm.explore;

/**
 * <p>队列动态数组实现</p>
 *
 * @author mason
 * @date 2022/8/22 22:53
 */
public class DynamicArrayQueue implements Queue {

    private final String[] items;
    private final int n;
    private int head;
    private int tail;

    public DynamicArrayQueue(int capacity) {
        this.items = new String[capacity];
        this.n = capacity;
        this.head = 0;
        this.tail = 0;
    }

    @Override
    public boolean push(String data) {
        if (tail == n) {
            if (head == 0) {
                return false;
            }

            for (int i = head; i < tail; ++i) {
                items[i - head] = items[i];
            }

            tail -= head;
            head = 0;
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
