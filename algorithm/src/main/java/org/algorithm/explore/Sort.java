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

    public void insertionSort(int[] data) {
        if (data == null || data.length < 1) {
            return;
        }

        int length = data.length;
        for (int i = 1; i < length; i++) {
            int value = data[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (data[j] > value) {
                    data[j + 1] = data[j];
                } else {
                    break;
                }
            }
            data[j + 1] = value;
        }

    }


    public void selectionSort(int[] data) {
        if (data == null || data.length < 1) {
            return;
        }

        int length = data.length;
        for (int i = 0; i < length; i++) {
            int min = data[i];
            int location = i;
            for (int j = i; j < length; j++) {
                if (data[j] < min) {
                    min = data[j];
                    location = j;
                }
            }
            data[location] = data[i];
            data[i] = min;
        }
    }
}
