/*
 * leetcode problem 267 Palindrome Permutation II
 *
 * Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.
 * 
 * For example:
 * 
 * Given s = "aabb", return ["abba", "baab"].
 * 
 * Given s = "abc", return [].
 */

public class Solution {
    public List<String> generatePalindromes(String s) {
        List<String> result = new ArrayList<String>();

        //get count of all different characters
        Map<Character, Integer> numberOfChar = new HashMap<Character, Integer>();
        for(char c : s.toCharArray()) {
            Integer number = numberOfChar.get(c);
            if(number == null) {
                numberOfChar.put(c, 1);
            }
            else {
                numberOfChar.put(c, number + 1);
            }
        }

        //generate base pattern, used to get permutation later
        List<Character> pattern = new ArrayList<Character>();
        //possibly single character
        Character single = null;
        for(Map.Entry<Character, Integer> entry : numberOfChar.entrySet()) {
            Integer number = entry.getValue();
            if(number / 2 * 2 != number) {
                //allow no more than one single character
                if(single != null) {
                    return result;
                }
                single = entry.getKey();
                for(int i = 0; i < (number - 1) / 2; i++) {
                    pattern.add(single);
                }
            }
            else {
                for(int i = 0; i < number / 2; i++) {
                    pattern.add(entry.getKey());
                }
            }
        }

        //finally, permutate pattern, and concate with possible single character
        List<String> permutations = permutate(pattern);
        for(String permutation : permutations) {
            if(single != null) {
                result.add(permutation + single + reverse(permutation));
            }
            else {
                result.add(permutation + reverse(permutation));
            }
        }
        return result;
    }

    private List<String> permutate(List<Character> pattern) {
        List<String> result = new ArrayList<String>();
        permutate(pattern, 0, result);
        return result;
    }

    private void permutate(List<Character> pattern, int begin, List<String> result) {
        int size = pattern.size();
        if(begin == size) {
            StringBuffer rst = new StringBuffer();
            for(Character c : pattern) {
                rst.append(c);
            }
            result.add(rst.toString());
            return;
        }

        Set<Character> visited = new HashSet<Character>();
        for(int i = begin; i < size; ) {
            swap(pattern, i, begin);
            permutate(pattern, begin + 1, result);
            swap(pattern, i, begin);
            visited.add(pattern.get(i));
            do {} while(++i < size && visited.contains(pattern.get(i)));
        }
    }

    private void swap(List<Character> list, int i1, int i2) {
        Character tmp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, tmp);
    }

    private String reverse(String s) {
        StringBuilder output = new StringBuilder(s);
        int length = output.length();
        for(int i = 0; i < length / 2; i++) {
            Character c = output.charAt(i);
            output.setCharAt(i, output.charAt(length - i - 1));
            output.setCharAt(length - i - 1, c);
        }
        return output.toString();
    }
}
