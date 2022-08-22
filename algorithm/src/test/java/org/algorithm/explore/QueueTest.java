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

}
