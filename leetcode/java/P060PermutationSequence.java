/*
 * The set [1,2,3,…,n] contains a total of n! unique permutations.
 * 
 * By listing and labeling all of the permutations in order,
 * We get the following sequence (ie, for n = 3):
 * 
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * Given n and k, return the kth permutation sequence.
 * 
 * Note: Given n will be between 1 and 9 inclusive.
 */

public class Solution {
    public String getPermutation(int n, int k) {
        //factorial list from 0 to n-1
        int[] factorial = new int[n];
        factorial[0] = 1;
        for(int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        //selection list, from 1 to n
        List<Integer> selection = new ArrayList<Integer>();
        for(int i = 1; i <= n; i++) {
            selection.add(i);
        }

        //iterate and fix every position
        StringBuilder result = new StringBuilder();
        k--;
        for(int i = n - 1; i >= 0; i--) {
            int position = k / factorial[i];
            k -= position * factorial[i];
            result.append(selection.get(position));
            selection.remove(position);
        }
        
        return result.toString();
    }
}
