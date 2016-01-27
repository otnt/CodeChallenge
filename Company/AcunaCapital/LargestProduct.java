/*
 * Online Code Challenge of AcunaCapital
 * Given an array, return a product of two numbers that is the maximum product.
 * But there are two constraints:
 * 1.The first number should has smaller index/position than second number
 * 2.The first number should be strictly smaller than second number
 *
 * Example:
 * input -> [4,2,1,3]
 * return -> 6
 *
 * input -> [4, 9, 8, 5]
 * return -> 36
 *
 */
public class LargestProduct{
    /*
     * Find maximum product of two number in given array
     *
     * The idea is we use two pointer scanning the input array, from
     * two end to middle. For each possible combination we calculate
     * the maximum product. Then we move the smaller pointer to
     * the larger pointer, skipping all numbers that is smaller than
     * current number. Except that, we need to check whether product
     * previous left value and current left value could form a
     * maximum product.
     *
     * time complexity: O(N)
     * space complexity: O(1)
     */
    static int MaximumProduct(int[] input) {
        //check input correctness
        if(input == null) {
            throw new NullPointerException();
        }
        if(input.length < 2) {
            throw new IllegalArgumentException();
        }

        int l = 0, h = input.length - 1;
        int maxProduct = Integer.MIN_VALUE;
        int tmp = 0;
        while(l < h) {
            //get current maximum product
            tmp = getProduct(input, l, h);
            maxProduct = Math.max(maxProduct, tmp);

            //go to middle, and skip smaller numbers
            if(input[l] < input[h]) {
                tmp = input[l];
                while(l < h && input[l] <= tmp) {
                    l++;
                }
                //need additional check for product of current
                //max l value and previous l value
                maxProduct = Math.max(maxProduct, tmp * input[l]);
            }
            else {
                tmp = input[h];
                while(l < h && input[h] <= tmp) {
                    h--;
                }
            }
        }

        return maxProduct;
    }

    /*
     * Return product of numbers at position l and h of input array
     * If not follow rule 1 or 2, return minimum value
     */
    private static int getProduct(int[] array, int l, int h) {
        int lValue = array[l];
        int hValue = array[h];
        //if not follow rule 2
        if(lValue >= hValue) {
            return Integer.MIN_VALUE;
        }
        //it is guaranteed to follow rule 1, so we return the product
        return lValue * hValue;
    }

    public static void main(String[] argv){
        LargestProduct s = new LargestProduct();
        System.out.println(s.MaximumProduct(new int[]{4, 3, 10, 5}));
    }
}
