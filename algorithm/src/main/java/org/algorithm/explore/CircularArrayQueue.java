package org.algorithm.explore;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/23 21:26
 */
public class CircularArrayQueue implements Queue {

    private String[] items;
    private int n;
    private int tail;
    private int head;

    public CircularArrayQueue(int capacity) {
        this.items = new String[capacity];
        this.n = capacity;
        this.tail = 0;
        this.head = 0;
    }

    @Override
    public boolean push(String data) {
        if ((tail + 1) % n == head) {
            return false;
        }
        items[tail] = data;
        tail = (tail + 1) % n;
        return false;
    }

    @Override
    public String take() {
        if (tail == head) {
            return null;
        }
        String result = items[head];
        head = (head + 1) % n;
        return result;
    }
}
