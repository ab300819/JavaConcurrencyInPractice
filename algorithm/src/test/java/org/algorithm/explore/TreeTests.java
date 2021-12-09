package org.algorithm.explore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreeTests {

    @Test
    public void testCase() {
        int[] nums = new int[]{1, 2, 3, 1, 2, 3};

        Assertions.assertFalse(containsNearbyDuplicate(nums, 2));
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        for (int i = 0; i < nums.length; ++i) {
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[i] == nums[j]) {
                    if ((nums[j] - nums[i]) <= k) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
