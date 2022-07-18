package com.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试 sort
 *
 * @author mason
 */
@Slf4j
public class SortTest {

    private final Random random=new Random();

    @Test
    public void testIntSort() {
        int[] numArray = new int[100];
        List<Integer> numList = Arrays.stream(numArray)
                .map(t ->random.nextInt(1000))
                .boxed()
                .collect(Collectors.toList());
        log.info( numList.toString());

        Collections.sort(numList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return -1;
                } else if (o1.equals(o2)) {
                    return 0;
                } else {
                    return 0;
                }
            }
        });

        log.info(numList.toString());
    }

}
