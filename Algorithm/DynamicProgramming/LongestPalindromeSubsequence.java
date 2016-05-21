/*
 * formula:
 * f(i, j) = 1 // if i == j
 *           2 + f(i + 1, j - 1) // if word[i] == word[j]
 *           max(f(i + 1, j), f(i, j - 1)) // otherwise
 *
 * r(i, j) = word[i] // if i == j
 *           word[i] + r(i + 1, j - 1) + word[j]
 *           r(i + 1, j) or r(i, j - 1)
 */

public class LongestPalindromeSubsequence {
    public String longestPalindromeSubsequence(String input) {
        int length = input.length();
        Cell[][] dpMatrix = new Cell[length][length];

        for(int len = 1; len <= length; len++) {
            for(int b = 0; b <= length - len; b++) {
                int e = b + len - 1;
                if(input.charAt(b) == input.charAt(e)) {
                    int subLength = 0;
                    String subString = "";
                    if(b + 1 <= e - 1) {
                        Cell cell = dpMatrix[b + 1][e - 1];
                        subLength = cell.length;
                        subString = cell.word;
                    }
                    dpMatrix[b][e] = new Cell(
                            subLength + (b == e ? 1 : 2), 
                            input.charAt(b) + subString + (b == e ? "" : input.charAt(e)));
                }
                else {
                    int length1 = dpMatrix[b + 1][e].length;
                    int length2 = dpMatrix[b][e - 1].length;
                    dpMatrix[b][e] = new Cell(
                            Math.max(length1, length2),
                            length1 > length2 ? dpMatrix[b + 1][e].word :
                                                dpMatrix[b][e - 1].word);
                }
            }
        }

        return dpMatrix[0][length - 1].word;
    }

    class Cell {
        public int length;
        public String word;
        public Cell(int length, String word) {
            this.length = length;
            this.word = word;
        }
    }

    public static void main(String[] argv) {
        System.out.println(new LongestPalindromeSubsequence().longestPalindromeSubsequence(argv[0]));
    }
}

