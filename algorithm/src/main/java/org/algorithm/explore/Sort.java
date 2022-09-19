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

    /**
     * <a href="https://www.cnblogs.com/chengxiao/p/6194356.html">Merge Sort</a>
     *
     * @param data
     */
    public void mergeSort(int[] data) {
        int[] temp = new int[data.length];
        sort(data, 0, data.length - 1, temp);
    }

    private void sort(int[] data, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) >> 1;
        sort(data, left, mid, temp);
        sort(data, mid + 1, right, temp);
//        merge(data, left, mid, right, temp);
        mergeBySentry(data, left, mid, right);
    }

    private void merge(int[] data, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;
        while (i <= mid && j <= right) {
            if (data[i] <= data[j]) {
                temp[t++] = data[i++];
            } else {
                temp[t++] = data[j++];
            }
        }

        while (i <= mid) {
            temp[t++] = data[i++];
        }

        while (j <= right) {
            temp[t++] = data[j++];
        }

        t = 0;
        while (left <= right) {
            data[left++] = temp[t++];
        }
    }

    private void mergeBySentry(int[] data, int left, int mid, int right) {
        int[] leftData = new int[mid - left + 2];
        int[] rightData = new int[right - mid + 1];

        for (int i = 0; i <= mid - left; ++i) {
            leftData[i] = data[left + i];
        }
        leftData[mid - left + 1] = Integer.MAX_VALUE;

        for (int i = 0; i < right - mid; ++i) {
            rightData[i] = data[mid + 1 + i];
        }
        rightData[right - mid] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;
        int k = left;

        while (k <= right) {
            data[k++] = leftData[i] <= rightData[j] ? leftData[i++] : rightData[j++];
        }
    }

    public void quickSort(int[] data) {
        quickSortC(data, 0, data.length - 1);
    }

    private void quickSortC(int[] data, int start, int end) {
        if (start >= end) {
            return;
        }

        int middle = partition(data, start, end);
        quickSortC(data, start, middle - 1);
        quickSortC(data, middle + 1, end);
    }

    private int partition(int[] data, int start, int end) {
        int pivot = data[end];
        int i = start;
        for (int j = start; j < end; ++j) {
            if (data[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int swap = data[i];
                    data[i] = data[j];
                    data[j] = swap;
                    ++i;
                }
            }
        }

        int swap = data[i];
        data[i] = data[end];
        data[end] = swap;

        return i;
    }

    public void countSort(int[] data) {
        if (data == null || data.length < 1) {
            return;
        }

        int max = data[0];
        for (int i = 1; i < data.length; i++) {
            if (max < data[i]) {
                max = data[i];
            }
        }

        int[] c = new int[max + 1];
        for (int i = 0; i < data.length; i++) {
            c[data[i]]++;
        }

        for (int i = 1; i < max + 1; i++) {
            c[i] = c[i - 1] + c[i];
        }

        int[] r = new int[data.length];
        for (int i = data.length - 1; i >= 0; i--) {
            int index = c[data[i]] - 1;
            r[index] = data[i];
            c[data[i]]--;
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = r[i];
        }
    }

}
