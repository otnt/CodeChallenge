/*
 * leetcode problem 84. Largest Rectangle in Histogram My Submissions Question
 *
 * Given n non-negative integers representing the histogram's bar height where 
 * the width of each bar is 1, find the area of largest rectangle in the histogram.
 *
 * For example,
 * Given heights = [2,1,5,6,2,3],
 * return 10.
 */

/*
 * Thought
 * 
 * First let's think about the very basic strategy, an O(N^2) method. For each
 * bar, if it is the height of maximum rectangular, then the width of maximum
 * rectangular is, from this bar, look ahead and look back, the range in which
 * all bars are not shorter than itself. Using this method, for each bar, we loop
 * over forward and backward to find the range and update maximum area.
 *
 * Now let's analyze this method. There are two parts.
 * First, look ahead. The maximum range to ahead is when it first meet a bar
 * that is shorter than itself. Second, look back, the similar condition holds.
 * But for looking back, we could use the opposite way to handle it. For each
 * new bar, we try to find all larger bars before it.
 * The second way is much better because it simplify our condition to only
 * look forward.
 *
 * So the pattern we find here is we want to have a data structure/algorithm
 * that could let us find easily
 * 1. the first existed element smaller than new element
 * 2. all existed element larger than new element
 *
 * Also, notice, for the second finding, since we only need the first element
 * that is smaller than a previous element. This is to say, only we find all
 * existed element larger than a new element, for any newer element, these 
 * larger elements are not needed.
 *
 * So, a little refinement on our strategy:
 * We want to have a data structure/algorithm that could let us find easily
 * 1. the first existed element smaller than new element
 * 2. all existed element larger than new element, and throw them away
 *
 * Could you design such a data structure? If you write some examples, you'll
 * find all elements in the array are monotonically increasing!
 * So when insert a new element, it walks through all existing elements backforwardly,
 * for each existed element, it either throw it away because the existed elemnt
 * is larger, or stop walking, because the existed element is smaller or equal.
 *
 * Actually, this data structure has a name: monotonical priority queue.
 * It's underlying data structure is just a stack, but instead of only allow
 * FILO, it also requires all elements in the stack to be monotonically increasing
 * or decreasing.
 */

/*
 * Pseudocode:
 * input is int[] bars 
 * output is int area
 * 
 * mpq := empty stack
 * area := 0
 * for index, bar := range bars:
 *   area = max(area, push_to_mpq(mpq, index, bar))
 *
 * area = max(area, push_to_mpq(mpq, len(bars), -1))
 * return area
 *
 * func push_to_mpq(mpq, index, bar):
 *   area := 0
 *   first_index := index //this is the first index that is smaller than new bar
 *   while(mpq is not empty && mpq.last_element > bar) {
 *    old_bar, old_index = mpq.pop()
 *    area = max(area, (index - old_index) * old_bar)
 *   }
 *   mpq.push(<bar, first_index>)
 *   return area
 */

public class P84LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        List<Bar> mpq = new ArrayList<Bar>();
        int area = 0;
        
        for(int i = 0; i < heights.length; i++) {
            area = Math.max(area, pushToMpq(mpq, i, heights[i]));
        }
        area = Math.max(area, pushToMpq(mpq, heights.length, -1));
        
        return area;
    }

    private int pushToMpq(List<Bar> mpq, int index, int height) {
        int area = 0;
        int firstIndex = index;
        while(!mpq.isEmpty() && mpq.get(mpq.size() - 1).height > height) {
            Bar oldBar = mpq.remove(mpq.size() - 1);
            area = Math.max(area, (index - oldBar.index) * oldBar.height);
            firstIndex = oldBar.index;
        }
        mpq.add(new Bar(firstIndex, height));

        return area;
    }

    class Bar {
        int index, height;
        public Bar(int index, int height) {
            this.height = height;
            this.index = index;
        }
    }
}
