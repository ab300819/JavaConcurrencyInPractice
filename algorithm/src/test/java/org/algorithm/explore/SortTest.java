package org.algorithm.explore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/28 23:56
 */
class SortTest {

    private int[] data;
    private int[] expectation;
    private Sort sort;

    @BeforeEach
    public void initData() {
        sort = new Sort();
        data = new int[]{10, 2, 11, 9, 7, 5, 3, 4, 1, 6, 8, 12};
        expectation = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    }

    @Test
    void bubbleSortTest() {
        sort.bubbleSort(data);
        Assertions.assertArrayEquals(expectation, data);
    }
}
