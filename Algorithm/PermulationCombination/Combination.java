/*
 * Write a program to give all combination of R numbers from 1 to N.
 *
 * See more about permulation and combination, check here:
 *
 *
 * Our method is: 
 * Recursively, we fix elements one by one, until we fix all elements, we
 * find a new combination. More specifically, we have R pointers go through
 * array, and each time when we fix a new number, we add it to result array.
 * The difference between combination and permulation is that we don't need
 * to swap any number, since combination doesn't care about order, so all
 * pointers go one direction.
 *
 * For example, given R=2 and N=3, so we have to combination C_3^2
 * 123
 * - 1_
 * -- 12 -> print
 * -- 13 -> print
 * - 2_
 * ...
 *
 * Since this is recursive method, space complexity(including stack usage) 
 * would be the depth of search tree, which is O(R). 
 * And time complexity is O(R * C_N^R) since we have C_N^R combination, and for
 * each combination we have to iterate elements from 1 time to R time, which is
 * averagely R/2 time.
 *
 * Author: Pufan Jiang
 * Date: March 5th 2016
 */

public class Combination {
    public void combinate(final int[] nums, final int R) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        int[] result = new int[R];
        combinateHelp(nums, result, 0, 0);
    }

    private void combinateHelp(final int[] nums, final int[] result, 
            int begin, int depth) {
        if(depth == result.length) {
            printArray(result);
            return;
        }

        for(int i = begin; i < nums.length; i++) {
            result[depth] = nums[i];
            combinateHelp(nums, result, i+1, depth+1);
        }
    }

    private void printArray(final int[] array) {
        for(int n : array) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void main(String[] argv) {
        Combination combination = new Combination();
        combination.combinate(new int[]{1,2,3,4,5}, 3);
    }
}
