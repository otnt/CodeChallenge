/*
 * Write a program to give all permulations from 1 to N.
 *
 * See more about permulation and combination, check here:
 * https://github.com/otnt/CodeChallenge/tree/master/Algorithm/PermulationCombination
 *
 * Our method is: 
 * Recursively, we fix elements one by one, until we fix all elements, we
 * find a new permulation. More specifically, for each position to be fixed, we
 * look through all possibility, and swap them with the position, and 
 * recursively go to next position.
 *
 * For example, given 3, so we have to permulate 123
 * 123
 * - 1__
 * -- 12_
 * --- 123
 * -- 13_
 * --- 132
 * - 2__
 * ...
 *
 * Since this is recursive method, space complexity(including stack usage) 
 * would be the depth of search tree, which is O(N). 
 * And time complexity is O(N * N!) since we have N! permulation, and for
 * each permulation we have to swap elements from 1 time to N time, which is
 * averagely N/2 time.
 *
 * Author: Pufan Jiang
 * Date: March 5th 2016
 */

public class Permulation {
    public void permulate(final int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        permulateHelp(nums, 0);
    }

    private void permulateHelp(final int[] nums, int begin) {
        int length = nums.length;
        if(begin == length) {
            printArray(nums);
            return;
        }

        for(int i = begin; i < length; i++) {
            swap(nums, begin, i);
            permulateHelp(nums, begin + 1);
            swap(nums, begin, i);
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
        Permulation permulation = new Permulation();
        permulation.permulate(new int[]{1,2,3,4,5});
    }
}

