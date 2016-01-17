/*
 * lintcode problem Heapify, http://www.lintcode.com/en/problem/heapify/
 * Problem:
 * Given an integer array, heapify it into a min-heap array.
 * For a heap array A, A[0] is the root of heap, and for each A[i], A[i * 2 + 1] is the left child of A[i] and A[i * 2 + 2] is the right child of A[i].
 *
 * Example
 * Given [3,2,1,4,5], return [1,2,3,4,5] or any legal heap array.
 *
 * Challenge
 * O(n) time complexity
 *
 * @author: pufan jiang, jiangpufan@gmail.com
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - what data type? integer
 * 1.2 [Q2] - store in memory? yes
 * 1.3 [Q3] - return any possible answer? yes
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - From the end to the head of array, do a "sink" operation
 * 2.2 [Time complexity] - O(n)
 * 2.3 [Space complexity] - O(1)
 * 2.4 [If huge amount of data] - not suitable for heap?
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

public class Heapify{
    public void heapify(int[] A) {
        if(A == null) {
            throw new NullPointerException();
        }
        if(A.length == 0) {
            throw new IllegalArgumentException();
        }

        int length = A.length;
        for(int i = length - 1; i >= 0; i--) {
            heapSink(A, i);
        }
    }

    private void heapSink(int[] A, int pos) {
        if(A == null) {
            throw new NullPointerException();
        }
        if(A.length == 0) {
            throw new IllegalArgumentException();
        }

        int leftChildPos = getLeftChildPos(A, pos);
        int rightChildPos = getRightChildPos(A, pos);
        int leftChildVal = leftChildPos == -1 ? Integer.MAX_VALUE : A[leftChildPos];
        int rightChildVal = rightChildPos == -1 ? Integer.MAX_VALUE : A[rightChildPos];
        int currVal = A[pos];

        int minPos = currVal <= leftChildVal ? (currVal <= rightChildVal ? pos : rightChildPos)
            : (leftChildVal <= rightChildVal ? leftChildPos : rightChildPos);
        if(minPos != pos) {
            swap(A, pos, minPos);
            heapSink(A, minPos);
        }
    }

    private int getLeftChildPos(int[] A, int pos) {
        int leftChildPos = (pos + 1) * 2 - 1;
        return leftChildPos >= A.length ? -1 : leftChildPos;
    }

    private int getRightChildPos(int[] A, int pos) {
        int rightChildPos = (pos + 1) * 2;
        return rightChildPos >= A.length ? -1 : rightChildPos;
    }

    private void swap(int[] A, int p1, int p2) {
        int tmp = A[p1];
        A[p1] = A[p2];
        A[p2] = tmp;
    }

    public static void test() {
        Heapify h = new Heapify();
        int[] a = new int[]{45, 39, 32, 11};
        h.heapify(a);
    }

    public static void main(String[] argv) {
        test();
    }
}
