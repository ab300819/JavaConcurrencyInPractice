package org.algorithm.explore;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>队列测试</p>
 *
 * @author mason
 * @date 2022/8/23 00:09
 */
public class QueueTest {

    @Test
    public void arrayQueueTest() {
        int size = 100;
        Queue queue = new ArrayQueue(size);
        List<String> exception = new ArrayList<>();
        List<String> target = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            queue.push(String.valueOf(i));
            exception.add(String.valueOf(i));
        }
        for (int i = 0; i < size; ++i) {
            target.add(queue.take());
        }

        Assertions.assertEquals(exception, target);
    }

    @Test
    public void linkQueueTest() {
        int size = 100;
        Queue queue = new LinkQueue();
        List<String> exception = new ArrayList<>();
        List<String> target = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            queue.push(String.valueOf(i));
            exception.add(String.valueOf(i));
        }

        for (int i = 0; i < size; ++i) {
            target.add(queue.take());
        }

        Assertions.assertEquals(exception, target);
    }

    @Test
    public void dynamicArrayQueueTest() {
        int size = 5;
        Queue queue = new DynamicArrayQueue(size);
        List<String> exception = new ArrayList<>();
        List<String> target = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            queue.push(String.valueOf(i));
        }

        queue.take();
        queue.take();
        queue.take();
        queue.push("5");
        queue.push("6");
        queue.push("7");

        exception.add("3");
        exception.add("4");
        exception.add("5");
        exception.add("6");
        exception.add("7");
        for (int i = 0; i < size; ++i) {
            target.add(queue.take());
        }

        Assertions.assertEquals(exception, target);
    }

}
