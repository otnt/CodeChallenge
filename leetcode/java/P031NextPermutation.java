/*
 * leetcode problem p31, Next Permutation
 *
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * 
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
 * 
 * The replacement must be in-place, do not allocate extra memory.
 * 
 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 */

public class Solution {
    public void nextPermutation(int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        int index = findRightmostNonMaximumNumber(nums);

        //if could not find one, return reversed array
        if(index == -1) {
            reverse(nums);
            return;
        }
        
        //otherwise, swap with ceiling number, and sort
        //numbers at the end
        int index2 = findCeilingNumber(nums, index);
        swap(nums, index, index2);
        Arrays.sort(nums, index+1, nums.length);
        return;
    }

    private int findRightmostNonMaximumNumber(int[] nums) {
        int index = nums.length - 2;
        while(index >= 0) {
            if(nums[index] < nums[index+1]) {
                break;
            }
            index--;
        }
        return index;
    }

    private int findCeilingNumber(int[] nums, int index) {
        int minIndex = index + 1;
        int min = nums[minIndex];
        int value = nums[index];
        for(int i = index + 2; i < nums.length; i++) {
            if(nums[i] > value && nums[i] < min) {
                minIndex = i;
                min = nums[minIndex];
            }
        }
        return minIndex;
    }

    private void reverse(int[] array) {
        for(int i = 0; i < array.length / 2; i++) {
            swap(array, i, array.length - i - 1);
        }
    }

    private void swap(int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }
}
