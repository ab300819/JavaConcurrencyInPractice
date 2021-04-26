package com.feature.explore;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("hello", 123);
        System.out.println(data.toString());

        List<Integer> numList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(numList.toString());

    }

}
