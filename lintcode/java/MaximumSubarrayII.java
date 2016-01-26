/*
 * Lintcode problem, maximum subarray II, http://www.lintcode.com/en/problem/maximum-subarray-ii/
 * Given an array of integers, find two non-overlapping subarrays which have the largest sum.
 * The number in each subarray should be contiguous.
 * Return the largest sum.
 * 
 * Example
 * For given [1, 3, -1, 2, -1, 2], the two subarrays are [1, 3] and [2, -1, 2] or [1, 3, -1, 2] and [2], they both have the largest sum 7.
 * 
 * Note
 * The subarray should contain at least one number
 * 
 * Challenge
 * Can you do it in time complexity O(n) ?
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] -
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * This is not an easy problem. We will generize it here how to calculate k maximum subarray.
 * First we build a presum array, and add a dummy zero at front of array.
 * Then the sum of range[i, j - 1] = array[j] - array[i].
 * We use a matrix n * k to save state of dynamic programming.
 * Each cell (n_i, k_i) means use k_i subarray, the maximum we could find given previous n_i numbers
 * The state transfer function is:
 * f(n_i, k_i) = max(f(n_i - 1, k_i), max{ii = 0...n_i -> f(n_i - ii, k_i - 1) + maximum subarray from ii to n_i})
 * This seems a O(k * N^2) time compelexity due to max of max. However, we could avoid this by using an additional
 * array. The idea is when we traverse from 0 -> n_i in k_i-th turn, we save in additional array
 * the value that is max{ii = 0...n_i -> f(n_i - ii, k_i - 1) + maximum subarray from ii to n_i - array[n_i]}
 * which equals max{f(ii, k_i - 1) - array[ii]}
 *
 * After this basic solution, we could use rolling array method to reduce sapce complexity from O(kN) to O(2N)
 * 2.2 [Time complexity] - O(N)
 * 2.3 [Space complexity] - O(N)
 * 2.4 [If huge amount of data] - 
 * 
 * 3.write pseudocode first, but make sure tell interviewer you will write real
 * code later
 * 3.1 Write code on paper
 * 3.2 Test code on paper
 *
 * 4.write code on a moderate pace
 * 4.1 Type code as-is on paper into computer, list all compile and other errors
 *
 * 5.test code and carefully fix mistakes - extreme case, 0, negative, null, 
 * max, min? user input error (null, negative value)? general case?
 * 5.1[Unit tests]
 */

public class MaximumSubarrayII{
    public int maxTwoSubArrays(ArrayList<Integer> nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.size() == 0) {
            throw new IllegalArgumentException();
        }

        int N = nums.size();
        int K = 2;

        //matrix for dynamic programming
        int[][] mem = new int[K][N + 1];
        int[] tmpMem = new int[N + 1];
        int[] tmpMem2 = new int[N + 1];

        //initialization
        for(int i = 0; i < N + 1; i++) {
            mem[0][i] = 0;
        }
        for(int i = 0; i < K; i++) {
            mem[i][0] = 0;
        }
        for(int i = 1; i < N + 1; i++) {
            tmpMem[i] = 0;
            tmpMem2[i] = 0;
        }

        //dynamic finding maximum
        int max = Integer.MIN_VALUE;
        for(int k = 0; k < K; k++) {
            for(int n = 1; n < N + 1; n++) {
                mem[k][n] = Math.max(Math.max(mem[k][n - 1], tmpMem[n - 1]), 0) + nums.get(n - 1);
                tmpMem2[n] = Math.max(tmpMem2[n - 1], mem[k][n]);
                if(k == K - 1) {
                    max = Math.max(max, mem[k][n]);
                }
            }
            for(int n = 1; n < N + 1; n++) {
                tmpMem[n] = tmpMem2[n];
            }
        }

        return max;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
