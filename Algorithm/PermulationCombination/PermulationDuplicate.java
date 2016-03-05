/*
 * Write a program to give all permulations of a given input array, which may
 * have duplicate element, remove duplicate result.
 *
 * See more about permulation and combination, check here:
 * https://github.com/otnt/CodeChallenge/tree/master/Algorithm/PermulationCombination
 *
 * Before reading this solution, you should first understand how to give
 * a usual permutation without consideration about duplicated elements.
 *
 * Our method is: 
 * One naive solution is to use a set to save all result, and skip duplicated
 * result by the nature of set. This will increase usage of memory from O(1) to
 * O(N!) which is extremely bad.
 * A better solution is we first sort the input array, then when each iteration
 * we skip all numbers that have same value. So each swap will create a new
 * permulation. Using this solution, the space complexity is still O(1) and time
 * will increase by O(NlogN) due to sort process. But since the permulation
 * takes O(N * N!) which is way larger than O(NlogN) so the overall time 
 * complexity is almost the same.
 *
 * Author: Pufan Jiang
 * Date: March 5th 2016
 */

import java.util.Arrays;

public class PermulationDuplicate {
    public void permulate(final int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(nums);
        permulateHelp(nums, 0);
    }

    private void permulateHelp(final int[] array, int begin) {
        int length = array.length;
        if(begin == length) {
            printArray(array);
            return;
        }

        for(int i = begin; i < length; ) {
            swap(array, begin, i);
            permulateHelp(array, begin + 1);
            swap(array, begin, i);
            int current = array[i];
            do {} while(++i < length && array[i] == current);
        }
    }


    private void swap(final int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    private void printArray(final int[] array) {
        for(int n : array) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void main(String[] argv) {
        PermulationDuplicate permulation = new PermulationDuplicate();
        permulation.permulate(new int[]{1,2,2,2,5});
    }
}

