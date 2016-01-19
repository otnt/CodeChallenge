/*
 * Lintcode problem Sliding Window Maximum, http://www.lintcode.com/en/problem/sliding-window-maximum/
 *
 * Given an array of n integer with duplicate number, and a moving window(size k),
 * move the window at each iteration from the start of the array, find the maximum 
 * number inside the window at each moving.
 *
 * Example
 * For array [1, 2, 7, 7, 8], moving window size k = 3. return [7, 7, 8]
 * At first the window is at the start of the array like this
 * [|1, 2, 7| ,7, 8] , return the maximum 7;
 * then the window move one step forward.
 * [1, |2, 7 ,7|, 8], return the maximum 7;
 * then the window move one step forward again.
 * [1, 2, |7, 7, 8|], return the maximum 8;
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
 * 1.Good Solution
 * A good solution is to use a hash heap, which supports O(logN) remove an element 
 * from heap. By using hash heap, we could get time complexity of O(NlogN), which 
 * is good.
 *
 * 2.Better Solution
 * However, we could get better solution with O(N) time compelexity.
 * The feature of this problem: find maximum, and remove elements only from single
 * side(remove elements before k), lead us to think about using Monotone Priority Queue.
 * In this case, we could implement the monotone priority queue using a
 * Deque, which supports push and pop operation on both side of the queue.
 *
 * So, basically we maintain a monotone decreasing priority queue. Each time we
 * met a new element, we insert the new element but make sure it is monotone decreasing(which
 * means if there are some smaller elements before the new inserting element, we pop
 * them out before inserting), and we need to check whether we need to remove
 * a out-of-date element at front of queue. And the current maximum is always the
 * head of the queue.
 *
 * 2.2 [Time complexity] - O(N)
 * 2.3 [Space complexity] - O(k)
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

public class SlidingWindowMaximum{
    public ArrayList<Integer> maxSlidingWindow(int[] nums, int k){
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0 || nums.length < k) {
            return new ArrayList<Integer>();
        }

        Deque<Element> deque = new ArrayDeque<Element>();
        ArrayList<Integer> rst = new ArrayList<Integer>();
        for(int i = 0; i < nums.length; i++) {
            //remove smaller elements
            while(!deque.isEmpty() && deque.getLast().getValue() <= nums[i]) {
                deque.removeLast();
            }
            //add new element
            deque.addLast(new Element(nums[i], i));
            //remove out-of-date elements
            if(i >= k && deque.getFirst().getPosition() <= i - k) {
                deque.removeFirst();
            }
            //output
            if(i >= k - 1) {
                rst.add(deque.getFirst().getValue());
            }
        }

        return rst;
    }

    class Element{
        private int value, position;
        public Element(int value, int position){
            setValue(value);
            setPosition(position);
        }
        public int getValue() {return value;}
        public int getPosition() {return position;}
        public void setValue(int value){this.value = value;}
        public void setPosition(int position){this.position = position;}
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
