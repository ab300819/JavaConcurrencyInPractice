package org.algorithm.explore;

/**
 * <p>栈实现</p>
 *
 * @author mason
 * @date 2022/8/17 00:15
 */
public class ArrayStack implements Stack {
    private String[] item;
    private int count;
    private int size;

    public ArrayStack(int size) {
        this.item = new String[size];
        this.size = size;
        this.count = 0;
    }

    @Override
    public boolean push(String data) {
        if (size == count) {
            return false;
        }
        item[count] = data;
        ++count;
        return true;
    }

    @Override
    public String pop() {
        if (count == 0) {
            return null;
        }
        String data = item[count - 1];
        --count;
        return data;
    }

}
