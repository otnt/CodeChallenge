/*
 * Leetcode problem Largest Rectangle in Histogram
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * Given n non-negative integers representing the histogram's bar height where the
 * width of each bar is 1, find the area of largest rectangle in the histogram.
 * For example,
 * Given heights = [2,1,5,6,2,3],
 * return 10.
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - all heights are integer? - yes
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - use the idea of Monotone Priority Queue(https://en.wikipedia.org/wiki/Monotone_priority_queue).
 * This data structure supports O(1) find minimum and O(1) update. The difference between a monotone priority
 * queue and a normal priority queue could be found online. In this problem, we use a monotone increasing
 * priority queue and key is height of each retangular. Each element in queue maintains two value, its height
 * and the very beginning that we could draw a rectangular using this rectangular. When a higher rectangular
 * comes, we push it into queue from tail. When a shorter rectangular comes, we pop items from tail until we
 * met a rectangular that is shorter than the new rectangular. For each rectangular we pop, calculate the 
 * maximum area with it by height * distance between this rectangular and new rectangular. After poping all
 * higher rectangular, we update the new rectangular's beginning point to the lastest poped rectangular position.
 * 
 * For this problem, we do not need a deque to implement monotone priority queue, since we do not need to
 * remove elements from head. So what we need is a stack.
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

public class P084LargestRectangle{
    public int largestRectangleArea(int[] heights) {
        if(heights == null) {
            throw new NullPointerException();
        }

        LinkedList<Pair> stack = new LinkedList<Pair>();
        int area = 0;
        for(int i = 0; i < heights.length; i++) {
            area = Math.max(area, updateArea(stack, new Pair(heights[i], i)));
        }
        area = Math.max(area, updateArea(stack, new Pair(-1, heights.length)));
        
        return area;
    }

    private int updateArea(LinkedList<Pair> stack, Pair pair) {
        int height = pair.getHeight(), begin = pair.getBegin();
        int area = -1, newBegin = begin;
        while(!stack.isEmpty() && stack.peek().getHeight() >= height) {
            Pair tail = stack.pop();
            area = Math.max(area, tail.getHeight() * (begin - tail.getBegin()));
            newBegin = tail.getBegin();
        }
        pair.setBegin(newBegin);
        stack.push(pair);
        return area;
    }

    class Pair{
        private int height, begin;

        public Pair(int height, int begin) {
            setHeight(height);
            setBegin(begin);
        }

        public void setHeight(int height) {this.height = height;}
        public void setBegin(int begin) {this.begin = begin;}
        public int getHeight() {return height;}
        public int getBegin() {return begin;}
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
