package org.algorithm.explore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/17 00:23
 */
class StackTest {

    private final static Logger log = LoggerFactory.getLogger(StackTest.class);

    @Test
    public void arrayStackTest() {
        int size = 100;
        Stack stack = new ArrayStack(size);
        List<String> exception = new ArrayList<>();
        List<String> target = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            stack.push(String.valueOf(i));
            exception.add(String.valueOf(i));
        }
        Collections.reverse(exception);

        for (int i = 0; i < size; ++i) {
            target.add(stack.pop());
        }

        Assertions.assertEquals(exception, target);
    }

    @Test
    public void linkStackTest() {
        int size = 100;
        Stack stack = new LinkStack();
        List<String> exception = new ArrayList<>();
        List<String> target = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            stack.push(String.valueOf(i));
            exception.add(String.valueOf(i));
        }
        Collections.reverse(exception);

        for (int i = 0; i < size; ++i) {
            target.add(stack.pop());
        }

        Assertions.assertEquals(exception, target);
    }

}
