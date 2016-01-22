/*
 * Leetcode problem, Best Meeting Point
 * A group of two or more people wants to meet and minimize the total travel distance. You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
 * 
 * For example, given three people living at (0,0), (0,4), and (2,2):
 * 1 - 0 - 0 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.
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
 * It is easy to find this problem could be dissect to two one-dimension problems.
 * However, how to solve this in one dimension? An intuition solution is to use
 * median, but why? 
 * See Michael Hardy's answer, which is really beautiful:
 * http://math.stackexchange.com/questions/113270/the-median-minimizes-the-sum-of-absolute-deviations
 *
 * As to find median, we could either sort the array (since we have to traverse the
 * whole matrix, so the time complexity is at least O(M*N)). Or, more neatly,
 * use quick select. Or more advanced, use linear time select.
 *
 * 2.2 [Time complexity] - O(M*N)
 * 2.3 [Space complexity] - O(M + N)
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

public class P296BestMeetingPoint{
    public int minTotalDistance(int[][] grid) {
        if(grid == null) {
            throw new NullPointerException();
        }
        if(grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        List<Integer> rows = new ArrayList<Integer>();
        List<Integer> cols = new ArrayList<Integer>();
        for(int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[0].length; c++) {
                if(grid[r][c] == 1) {
                    rows.add(r);
                    cols.add(c);
                }
            }
        }
        
        Collections.sort(rows);
        Collections.sort(cols);
        int minRow = rows.get((rows.size() - 1) / 2);
        int minCol = cols.get((cols.size() - 1) / 2);
        
        int rst = 0;
        for(int r : rows) {
            rst += Math.abs(r - minRow);
        }
        for(int c : cols) {
            rst += Math.abs(c - minCol);
        }
        
        return rst;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
