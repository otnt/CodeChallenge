/*
 * Find first unique char in a string
 * 
 * Problem Description:
 * Given a string, find the first non-repeating character in it. For example, if
 * the input string is “GeeksforGeeks”, then output should be ‘f’ and if input 
 * string is “GeeksQuiz”, then output should be ‘G’.
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - Distinguish upper and lower case? - Yes
 * 1.2 [Q2] - Only alphabets? - Yes
 * 1.3 [Q3] - If no unique char? - throw Exception
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - Use a 52 length array store position of char. Initially position
 * -1, use first position replace -1, if already met one, set position to -2.
 *  Then re-traverse the array, find first char.
 * 2.2 [Time complexity] - O(2N)
 * 2.3 [Space complexity] - O(1)
 * 2.4 [If huge amount of data] - Separate to several files, use same methods.
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

public class FirstUniqueChar {
    public char firstUniqueChar(String str) {
        //check input correctness
        if(str == null || str.length() == 0) {
            throw new IllegalArgumentException();
        }

        //first traverse, record first occurance of each letter
        Integer[] position = new Integer[52];
        for(int i = 0; i < 52; i++) {
            position[i] = -1;
        }
        int len = str.length(), index = -1;
        for(int i = 0; i < len; i++) {
            index = letterToIndex(str.charAt(i));
            if(position[index] == -1) {
                position[index] = i;
            }
            else {
                position[index] = -2;
            }
        }

        //second traverse, find first unique char
        int min = len;
        char rst = 'a';
        for(int p : position) {
            if(p >= 0 && p < min)  {
                rst = str.charAt(p);
                min = p;
            }
        }
        if(min == len)
            throw new IllegalArgumentException();
        return rst;
    }

    protected int letterToIndex(char c) {
        if('a' <= c && c <= 'z')
            return c - 'a';
        else if('A' <= c && c <= 'Z')
            return c - 'A' + 26;
        else
            throw new IllegalArgumentException();
    }

    public static void test() {
        FirstUniqueChar fuc = new FirstUniqueChar();
        
        //GeekforGeeks
        System.out.println(fuc.firstUniqueChar("GeekforGeeks"));
        
        //GeekQuizs
        System.out.println(fuc.firstUniqueChar("GeekQuizs"));
        
        //abccba
        System.out.println(fuc.firstUniqueChar("abccba"));
    }

    public static void main(String[] argv) {
        test();
    }
}
