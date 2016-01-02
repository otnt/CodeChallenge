/*
 * Zillow: Find median in an array with a threshold 
 *
 * Problem Description:
 * Given a sorted array and minimum threshold value, find the
 * median of the subarray which contains values that are greater than or equal 
 * to the minimum threshold. 
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - integer? float? double? -- Generic
 * 1.2 [Q2] - input data structure? -- Array
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 1.Use binary search find minimum of subarray 2.Find median
 * 2.2 [Time complexity] - O(log(N) + 1)
 * 2.3 [Space complexity] - O(1)
 * 2.4 [If huge amount of data] - Split into several files. 1.Find the first 
 * file that contain subarray. 2.Find the minimum of subarray in this file.
 * 3.Calculate length of all number. 4.Find median.
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

public class FindMedianWithThreshold {
    /*
     * Find median given sorted array
     */
    public Number findMedianWithThreshold(Integer[] array, Integer thres) {
        //check input correctness
        //null or empty
        if(array == null || array.length == 0)
            throw new IllegalArgumentException();
        //median not exist
        if(array[array.length - 1] < thres)
            throw new IllegalArgumentException();

        //use binary search find minumum in subarray
        int start = binarySearch(array, thres);

        //find median
        int end = array.length;
        return getMedian(array, start, end);
    }

    /*
     * Find median given array and start(inclusive) end(exclusive) point
     */
    protected Integer getMedian(Integer[] array, int start, int end) {
        int num = end - start;
        //even number of digits
        if(num / 2 * 2 == num) {
            return (array[(start + end - 1) / 2] + array[(start + end - 1) / 2 + 1]) 
                / 2;
        }
        else {
            return array[(start + end - 1) / 2];
        }
    }

    /*
     * Find position to insert target value in a sorted array
     */
    protected int binarySearch(Integer[] array, Integer target) {
        int start = 0, mid = 0, end = array.length - 1;
        while(start <= end) {
            mid = (start + end) / 2;
            if(array[mid].equals(target)) {
                return mid;
            }
            else if(array[mid] > target) {
                end = mid - 1;
            }
            else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static void test() {
        FindMedianWithThreshold fmwt = new FindMedianWithThreshold();
        
        //input null
        //System.out.println(fmwt.findMedianWithThreshold(null, null));

        //input empty
        //System.out.println(fmwt.findMedianWithThreshold(new Integer[]{}, 0));

        //input median not exist
        //System.out.println(fmwt.findMedianWithThreshold(
        //            new Integer[]{1, 2, 3, 4}, 5));
        
        //Integer, 1,2,3,8,9 ~ 2
        System.out.println(fmwt.findMedianWithThreshold(
                    new Integer[]{1, 2, 3, 8, 9}, 2));
        
        //Integer, 1,3,8,9 ~ 2
        System.out.println(fmwt.findMedianWithThreshold(
                    new Integer[]{1, 3, 8, 9}, 2));
    }

    public static void main(String[] argv) {
        test();
    }
}
