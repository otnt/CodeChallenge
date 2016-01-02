/*
 * Zillow coding problem
 * Reverse words in a given string
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 Any punctuation? -- No
 * 1.2 Only space and alphabets? -- Yes
 * 1.3 Space is one letter width? -- No
 * 1.4 Prefix or Suffix space? -- Yes
 * 1.5 Input output data type string? -- Yes
 * 1.6 Could be stored in memory? -- Yes, could also think how to modify 
 * solution if not able to store in memory.
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 This is a typical question, a good solution is first reverse and only 
 * reverse all words, then reverse the whole string.
 * 2.2 Time complexity - O(2N)
 * 2.3 Space complexity - O(1)
 * 2.4 If huge amount of data - How to reverse a very large word? Separate the 
 * large word into several small words, reverse them and store in file, then 
 * concate them in reverse order.
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
 */

import java.util.regex.*;

public class ReverseWords {
    /*
     * Reverse position of all words(only alphabet) and word boundary in src.
     */
    public String reverseWords(String src) {
        //input correctness check
        //input null
        if(src == null) {
            throw new IllegalArgumentException();
        }

        StringBuilder dst = new StringBuilder(src);

        //1.reverse all words
        Pattern p = Pattern.compile("\\b([a-zA-Z]+)\\b");
        Matcher m = p.matcher(src);
        while(m.find()) {
            reverse(dst, m.start(), m.end());
        }

        //2.reverse whole sentence
        reverse(dst, 0, dst.length());

        return dst.toString();
    }

    /*
     * Reverse characters from start(include) to end(exclude) in sb.
     */
    protected void reverse(StringBuilder sb, int start, int end) {
        end--;//end is excluded
        while(start < end) {
            swap(sb, start, end);
            start++;
            end--;
        }
    }

    /*
     * Reverse characters at position p1 and p2 in sb.
     */
    protected void swap(StringBuilder sb, int p1, int p2) {
        char c = sb.charAt(p1);
        sb.setCharAt(p1, sb.charAt(p2));
        sb.setCharAt(p2, c);
    }

    public static void test() {
        ReverseWords rw = new ReverseWords();

        //input is null
        //System.out.println(rw.reverseWords(null));
        //input is empty
        System.out.println(rw.reverseWords(""));
        //input only spaces
        System.out.println(rw.reverseWords("  "));
        //input one letter
        System.out.println(rw.reverseWords("a"));
        //input has prefix and suffix space
        System.out.println(rw.reverseWords(" abc def "));
        //input has no prefix neither suffix space
        System.out.println(rw.reverseWords("abc def"));
    }

    public static void main(String[] argv) {
        test();
    }
}
