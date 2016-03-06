/*
 * Leetcode problem Permutation
 * https://leetcode.com/problems/permutations/
 *
 * Given a collection of distinct numbers, return all possible permutations.
 * For example,
 * [1,2,3] have the following permutations:
 * [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 */

public class Solution {
    private List<List<Integer>> result = new ArrayList<List<Integer>>();

    public List<List<Integer>> permute(int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        permuteHelp(nums, 0);
        
        return result;
    }

    private void permuteHelp(int[] nums, int begin) {
        int length = nums.length;
        if(begin == length) {
            List<Integer> permutation = new ArrayList<Integer>();
            for(int n : nums) {
                permutation.add(n);
            }
            result.add(permutation);
            return;
        }

        for(int i = begin; i < length; i++) {
            swap(nums, begin, i);
            permuteHelp(nums, begin + 1);
            swap(nums, begin, i);
        }
    }

    private void swap(int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }
}
