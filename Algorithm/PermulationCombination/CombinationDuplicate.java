/*
 * Write a program to give all combination of R numbers of a given input array,
 * which may have duplicate element, remove duplicate result.
 *
 * See more about permulation and combination, check here:
 * https://github.com/otnt/CodeChallenge/tree/master/Algorithm/PermulationCombination
 *
 * Before reading this solution, you should first understand how to give
 * a usual combination without consideration about duplicated elements.
 *
 * Our method is: 
 * One naive solution is to use a set to save all result, and skip duplicated
 * result by the nature of set. This will increase usage of memory from O(1) to
 * O(N!) which is extremely bad.
 * A better solution is we first sort the input array, then when each iteration
 * we skip all numbers that have same value. So each swap will create a new
 * combination. Using this solution, the space complexity is still O(1) and time
 * will increase by O(NlogN) due to sort process. But since the combination
 * takes O(N * N!) which is way larger than O(NlogN) so the overall time 
 * complexity is almost the same.
 *
 * Author: Pufan Jiang
 * Date: March 5th 2016
 */

import java.util.Arrays;

public class CombinationDuplicate {
    public void combinate(final int[] nums, final int R) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        int[] result = new int[R];
        Arrays.sort(nums);
        combinateHelp(nums, result, 0, 0);
    }

    private void combinateHelp(final int[] nums, final int[] result,
            int begin, int depth) {
        if(depth == result.length) {
            printArray(result);
            return;
        }

        for(int i = begin; i < nums.length; ) {
            result[depth] = nums[i];
            combinateHelp(nums, result, i+1, depth+1);
            int current = nums[i];
            do{} while(++i < nums.length && nums[i] == current);
        }
    }

    private void printArray(final int[] array) {
        for(int n : array) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void main(String[] argv) {
        CombinationDuplicate combination = new CombinationDuplicate();
        combination.combinate(new int[]{1,2,2,5,5,5,5}, 2);
    }
}

