/*
 * Lintcode problem, Sliding Window Median, http://www.lintcode.com/en/problem/sliding-window-median/
 * Given an array of n integer, and a moving window(size k), move the window at each
 * iteration from the start of the array, find the median of the element inside the 
 * window at each moving. (If there are even numbers in the array, return the N/2-th
 * number after sorting the element in the window. )
 *
 * Example
 * For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]
 * At first the window is at the start of the array like this
 * [ | 1,2,7 | ,8,5] , return the median 2;
 * then the window move one step forward.
 * [1, | 2,7,8 | ,5], return the median 7;
 * then the window move one step forward again.
 * [1,2, | 7,8,5 | ], return the median 7;
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - is number N guaranteed greater than k? - No, throw exception
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - From problem "data stream median" we know that such problem
 * is good to use two heaps, i.e. a maximum heap and a minimum heap. However,
 * unlike "data stream median" problem, this problem requires delete previous
 * elements, thus we come to hash heap, which supports O(NlogN) delete operation.
 * 2.2 [Time complexity] - O(NlogN)
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

public class SlidingWindowMedian{
    public ArrayList<Integer> medianSlidingWindow(int[] nums, int k) {
        if(nums == null) {
            throw new NullPointerException();
        }
        if(nums.length == 0 || nums.length < k) {
            return new ArrayList<Integer>();
        }

        MedianHeap<Element> medianHeap = new MedianHeap<Element>(k, new Comparator<Element>(){
            public int compare(Element e1, Element e2) {
                return e1.getValue() - e2.getValue();
            }
        });
        ArrayList<Integer> rst = new ArrayList<Integer>(nums.length - k + 1);
        for(int i = 0; i < nums.length; i++) {
            medianHeap.offer(new Element(nums[i], i));
            if(i >= k) {
                medianHeap.remove(new Element(nums[i - k], i - k));
            }
            if(i >= k - 1) {
                rst.add(medianHeap.poll().getValue());
            }
        }

        return rst;
    }

    class MedianHeap<T> {
        private PriorityQueue<T> maxHeap, minHeap;

        public MedianHeap(int capacity, final Comparator<T> comp) {
            maxHeap = new PriorityQueue<T>(capacity, new Comparator<T>(){
                public int compare(T e1, T e2) {
                    return - comp.compare(e1, e2);
                }
            });
            minHeap = new PriorityQueue<T>(capacity, comp);
        }

        public void offer(T e) {
            if(maxHeap.isEmpty() || maxHeap.peek().getValue() <= e.getValue()) {
                maxHeap.offer(e);
            }
            else {
                minHeap.offer(e);
            }
            rebalance();
        }

        public void remove(T e) {
            if(maxHeap.remove(e) || minHeap.remove(e)) {
                rebalance();
            }
        }

        public T poll() {
            return maxHeap.peek();
        }

        private void rebalance() {
            int maxSize = maxHeap.size(),
                minSize = minHeap.size();
            if(maxSize > minSize + 1) {
                minHeap.offer(maxHeap.poll());
            }
            else if(maxSize < minSize) {
                maxHeap.offer(minHeap.poll());
            }
        }
    }

    class Element{
        private int value, position;
        public Element(int value, int position) {
            setValue(value);
            setPosition(position);
        }
        public boolean equals(Object e) {
            return value == (Element)e.getValue() 
                && position == (Element)e.getPosition();
        }
        public int hashCode() {
            return String.format("%d%d", value, position).hashCode();
        }

        public int getValue(){return value;}
        public int getPosition(){return position;}
        public void setValue(int value){this.value = value;}
        public void setPosition(int position){this.position = position;}
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
