/*
 * Lintcode problem Maximum Subarray, http://www.lintcode.com/en/problem/maximum-subarray/
 * Given an array of integers, find a contiguous subarray which has the largest sum.
 * 
 * Example
 * Given the array [−2,2,−3,4,−1,2,1,−5,3], the contiguous subarray [4,−1,2,1] has the largest sum = 6.
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - all integers? yes
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * There are several solutions to this problem.
 * A popular solution is use a preSum array.
 * Here I use another solution, which is more like dynami programming. This solution
 * could reduce the complexity to O(N) for time and O(1) for space. (while preSum
 * need O(N) space complexity)
 * We use an array to save status in dynamic programming. Each element means "if
 * this is the end of maximum subarray, how large could it be". Then the state
 * transfer function is: f(i) = max(f(i - 1), 0) + a(i) 
 * After this basic solution, which use O(N) space. We could reduce the space by
 * something called rolling array, since we only need previous one element in 
 * array, we only need a space of length 2.
 * 2.2 [Time complexity] - O(N)
 * 2.3 [Space complexity] - O(1)
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

public class MaximumSubarray{
    public int maxSubArray(int[] nums) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0) {
            throw new IllegalArgumentException();
        }

        int prev = 0;
        int max = Integer.MIN_VALUE;
        for(int n : nums) {
            prev = Math.max(prev, 0) + n;
            max = Math.max(prev, max);
        }

        return max;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
