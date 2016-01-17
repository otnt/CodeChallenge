/*
 * Lint code problem, traping rain water, http://www.lintcode.com/en/problem/trapping-rain-water/
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
 * Example
 * Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
 *
 * Challenge
 * O(n) time and O(1) memory
 * O(n) time and O(n) memory is also acceptable.
 *
 * @author: pufan jiang, jiangpufan@gmail.com
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] -
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - Use two pointers, one left, one right, go together face to face. Let value of left is vl, value of right is vr,
 * each time, we move the pointer that has smaller value move to pointer that has larger value, and count the water we passby.
 * 2.2 [Time complexity] - O(n)
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

public class TrapingRainWater{
    public int trapRainWater(int[] heights) {
        if(heights == null) {
            throw new NullPointerException();
        }
        if(heights.length == 0) {
            throw new IllegalArgumentException();
        }

        int l = 0, r = heights.length - 1;
        int water = 0;
        int leftHeight = 0, rightHeight = 0, tmpHeight = 0;
        
        while(l < r) {
            leftHeight = heights[l];
            rightHeight = heights[r];

            tmpHeight = leftHeight;
            while(l < r && (leftHeight = heights[l]) <= rightHeight) {
                tmpHeight = Math.max(tmpHeight, leftHeight);
                water += tmpHeight - leftHeight;
                l++;
            }
            tmpHeight = rightHeight;
            while(l < r && (rightHeight = heights[r]) <= leftHeight) {
                tmpHeight = Math.max(tmpHeight, rightHeight);
                water += tmpHeight - rightHeight;
                r--;
            }
        }

        return water;
    }

    public static void test() {
        TrapingRainWater trw = new TrapingRainWater();
        System.out.println(trw.trapRainWater(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
    }

    public static void main(String[] argv) {
        test();
    }
}
