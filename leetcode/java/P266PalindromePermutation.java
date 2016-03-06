/*
 * leetcode problem 266. Palindrome Permutation
 *
 * Given a string, determine if a permutation of the string could form a palindrome.
 * 
 * For example,
 * "code" -> False, "aab" -> True, "carerac" -> True.
 */

public class Solution {
    public boolean canPermutePalindrome(String s) {
        Map<Character, Integer> numberOfChar = new HashMap<Character, Integer>();

        //iterate all possible chars and calculate occurence of each char
        for(char c : s.toCharArray()) {
            Integer number = numberOfChar.get(c);
            if(number == null) {
                numberOfChar.put(c, 1);
            }
            else {
                numberOfChar.put(c, number + 1);
            }
        }
        
        //at most one char could have odd number of occurence
        boolean hasOdd = false;
        for(Integer number : numberOfChar.values()) {
            if(number / 2 * 2 != number) {
                if(hasOdd) {
                    return false;
                }
                hasOdd = true;
            }
        }

        return true;
    }
}
