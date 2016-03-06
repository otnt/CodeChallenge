/*
 *  leetcode problem Permutations II
 *  https://leetcode.com/problems/permutations-ii/
 *
 *  Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 * 
 * For example,
 * [1,1,2] have the following unique permutations:
 * [1,1,2], [1,2,1], and [2,1,1].
 */

public class Solution {
    private List<List<Integer>> result = new ArrayList<List<Integer>>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(nums);
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

        Set<Integer> visited = new HashSet<Integer>();
        for(int i = begin; i < length; ) {
            swap(nums, i, begin);
            permuteHelp(nums, begin + 1);
            swap(nums, i, begin);
            visited.add(nums[i]);
            do {} while(++i < length && visited.contains(nums[i]));
        }
    }

    private void swap(int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }
}

