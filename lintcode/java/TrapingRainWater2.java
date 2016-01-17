/*
 * Lintcode problem traping rain water II, http://www.lintcode.com/en/problem/trapping-rain-water-ii/
 * Given n x m non-negative integers representing an elevation map 2d where the area
 * of each cell is 1 x 1, compute how much water it is able to trap after raining.
 * Example
 * Given 5*4 matrix
 *
 * [12,13,0,12]
 * [13,4,13,12]
 * [13,8,10,12]
 * [12,13,12,12]
 * [13,13,13,13]
 * return 14.
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - all positive? - yes
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - It seems hard at first, but after thinking, this is just a naive addition of original
 * one-dimension traping rain water problem. First, we scan matrix row by row, and find how much
 * water each cell could contain. Then, we do similar operation, but column by column. Finally,
 * for each cell, the water it could contain is the minimum of the two results.
 * 2.2 [Time complexity] - O(3 * N * M)
 * 2.3 [Space complexity] - O(2 * N * M)
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

public class TrapingRainWater2{
    public int trapRainWater(int[][] heights) {
        if(heights == null) {
            throw new NullPointerException();
        }
        if(heights.length == 0 || heights[0].length == 0) {
            throw new IllegalArgumentException();
        }

        int N = heights.length, M = heights[0].length;

        //row by row
        int[][] rowHeights = new int[N][M];
        for(int row = 0; row < N; row++) {
            int l = 0, r = M - 1;
            int leftHeight = 0, rightHeight = 0, tmpHeight = 0;
            while(l < r) {
                leftHeight = heights[row][l];
                rightHeight = heights[row][r];

                tmpHeight = heights[row][l];
                while(l < r && (leftHeight = heights[row][l]) <= rightHeight) {
                    tmpHeight = Math.max(tmpHeight, leftHeight);
                    rowHeights[row][l] = tmpHeight - leftHeight;
                    l++;
                }
                tmpHeight = heights[row][r];
                while(l < r && (rightHeight = heights[row][r]) <= leftHeight) {
                    tmpHeight = Math.max(tmpHeight, rightHeight);
                    rowHeights[row][r] = tmpHeight - rightHeight;
                    r--;
                }
            }
        }

        //col by col
        int[][] colHeights = new int[N][M];
        for(int col = 0; col < M; col++) {
            int u = 0, d = N - 1;
            int upHeight = 0, downHeight = 0, tmpHeight = 0;
            while(u < d) {
                upHeight = heights[u][col];
                downHeight = heights[d][col];

                tmpHeight = heights[u][col];
                while(u < d && (upHeight = heights[u][col]) <= downHeight) {
                    tmpHeight = Math.max(tmpHeight, upHeight);
                    colHeights[u][col] = tmpHeight - upHeight;
                    u++;
                }
                tmpHeight = heights[d][col];
                while(u < d && (downHeight = heights[d][col]) <= upHeight) {
                    tmpHeight = Math.max(tmpHeight, downHeight);
                    colHeights[d][col] = tmpHeight - downHeight;
                    d--;
                }
            }
        }

        //add together
        int water = 0;
        for(int r = 0; r < N; r++) {
            for(int c = 0; c < M; c++) {
                water += Math.min(rowHeights[r][c], colHeights[r][c]);
            }
        }

        return water;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
