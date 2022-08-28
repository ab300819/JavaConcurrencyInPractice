package org.algorithm.explore;

/**
 * <p>排序算法</p>
 *
 * @author mason
 * @date 2022/8/28 23:14
 */
public class Sort {

    public void bubbleSort(int[] data) {
        if (data == null || data.length < 1) {
            return;
        }
        int length = data.length;
        for (int i = 0; i < length; ++i) {
            boolean flag = false;
            for (int j = 0; j < length - i - 1; ++j) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j + 1];
                    data[j + 1] = data[j];
                    data[j] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }
}
