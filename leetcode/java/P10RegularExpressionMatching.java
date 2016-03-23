/*
 * leetcode problem 10. Regular Expression Matching
 * Implement regular expression matching with support for '.' and '*'.
 *
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * 
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 */

/*
 * Thought
 *
 * Get rid of algorithm, think this matching problem in a human way. If I were
 * matching two string, I would first compare head of two strings, then second,
 * then third, and so on and so forth. So that each time matching a new 
 * position, it is based on the result of previous matching. 
 * Also, notice the problem ask for true or false, and the possiblity of letter
 * is finite, i.e. '.', '*'. or normal letter.
 * This leads us to dynamic programming.
 *
 * Now what is the dynamic programming function.
 * It is quite straightforward actually, we want to know if pattern [0...i](0<=i<=M) 
 * could match string [0...j] (0<=j<=N) or not.
 * We already said possibility of letter is finite. So lets think about them
 * one by one.
 * 1.If last chars are both letter for string and pattern, then check if they 
 * are the same, and if pattern[0...i-1] and string[0...j-1] is matching.
 * 2.If last char in pattern is '.', then just check whether pattern[0...i-1] and
 * string[0...j-1] is matching.
 * 3.If last char is in pattern is '*', it's a little bit more complicated
 *   - If the char before '*' is '.', then if pattern[0...i-2] could match with string[0...jj]
 *     for at least one jj <= j, then its okay, since we could use .* to match
 *     any sequence we want.
 *   - Else if char before '*' is a letter, then if pattern[0...i-2] could match with
 *     string[0...jj] for at least one jj<=j and string[jj+1...j] are all same letter
 *     as pattern[i] then its okay. Notice here if jj+1==j then we are using letter* to match
 *     empty.
 *
 * Not so complicated,right? Lets write the dynamic programming function explicitly.
 * F(i, j) means pattern[0...i] could match with string[0...j]
 * F(i, j) = F(i-1, j-1) && (pattern[i] == string[j]) //if pattern[i] is letter
 *           F(i-1, j-1)                              //if pattern[i] is .
 *           Or(F(i-2, 0...jj) for all 0<=jj<=j)      //if pattern[i] is .*
 *           Or(F(i-2, 0...jj) && (string[jj+1...j] = pattern[i]) for all 0<=jj<=j)
 *                                                    //if pattern[i] is letter*
 * 
 * Okay, two more things.
 *
 * First, the last two functions seem awkward, it would make the whole time complexity
 * to be O(M*(N^2)). 
 *
 * However, notice that we could use a value k, where
 * k = Or(F(i-1, 0...jj)) when we go through from F(i, 0) to F(i, j), 
 * to avoid re-calculate that value everytime. 
 *
 * For the letter* pattern, we could split it to two conditions, think
 * about letter*, it would be either letter equals to string[j] or not equal.
 * If they are not equal, its simple, we just need to check whether pattern[0...i-2]
 * could match string[0...j], i.e. we throw away letter*
 * If they are equal, we could either use letter* to match at least one char in
 * string, or throw away letter*, so we just check all these two choices, which
 * is F(i, j-1) || F(i-2, j)
 *
 * So after refine, the function becomes
 * F(i, j) = F(i-1, j-1) && (pattern[i] == string[j]) //if pattern[i] is letter
 *           F(i-1, j-1)                              //if pattern[i] is .
 *           k where k = Or(F(i-2, 0...j))            //if pattern[i] is .*
 *           F(i, j-1) || F(i-2, j)                   //if pattern[i] is string[j]*
 *           F(i-2, j)                                //if pattern[i] is letter*
 *                                                      where letter != string[j]
 *
 * Second, how to initialize the dynamic programming matrix? Here we show the
 * secret directly, instead of let F(0, 0) means matching pattern[0] and 
 * string[0], we allow an additional state -- empty -- for both pattern and string,
 * so that F(0, 0) means matching empty pattern and empty string.
 * We let F(0, 0) to be correct. And for each F(i, 0), we only need to consider
 * a sequence of letter* pattern, so 
 * F(i, 0) = (pattern[i] is '*') && F(i-2, 0) //if i is even number
 *           false                            //otherwise
 */

/*
 * Pseudocode:
 * pattern: string of size M
 * string: string of size N
 * 
 * //initialization
 * dp[M+1][N+1] := false
 * dp[0][0] := true //empty pattern matches empty string
 * dp[i][0] := pattern[i-1] == '*' && dp[i-2][0] for i = 2->M and i is even number
 *
 * //for pattern[0...i]
 * for i = 1 -> M:
 *   k = i>=2 && dp[i-2][0] //could pattern[0...i-2] matching any of string[0...N]
 *   //for string[0...j]
 *   for j= 1 -> N:
 *     k |= i>=2 && dp[i-2][j]
 *     dp[i][j] = dp[i-1][j-1] && pattern[i] == string[j] if pattern[i] is letter
 *                dp[i-1][j-1]                            if pattern[i] is .
 *                k                                       if pattern[i] is .*
 *                dp[i][j-1] || dp[i-2][j]                if pattern[i] is string[j]*
 *                dp[i-2][j]                              if pattern[i] is letter*
 *                                                           and letter != string[j]
 * 
 * return dp[M][N] //pattern[0...M] matching string[0...N]
 */
public class Solution {
	/* main routine */
	public boolean isMatch(String s, String p){
        int M = p.length();
        int N = s.length();

        boolean[][] dp = new boolean[M+1][N+1];
        dp[0][0] = true;
        for(int i = 2; i <= M; i += 2) {
            dp[i][0] = (p.charAt(i - 1) == '*' && dp[i-2][0]);
        }

        for(int i = 1; i <= M; i++) {
            boolean k = i >= 2 && dp[i-2][0];
            char pp = p.charAt(i - 1);//current pattern char
            
            for(int j = 1; j <= N; j++) {
                k |= (i >= 2 && dp[i-2][j]);
                
                //letter
                if(pp >= 'a' && pp <= 'z') {
                    dp[i][j] = dp[i-1][j-1] && (pp == s.charAt(j - 1));
                }
                //.
                else if(pp == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }
                //.*
                else if(p.charAt(i - 2) == '.') {
                    dp[i][j] = k;
                }
                //string[j]*
                else if(p.charAt(i - 2) == s.charAt(j - 1)) {
                    dp[i][j] = dp[i][j-1] || dp[i-2][j];
                }
                //letter*, letter != string[j]
                else {
                    dp[i][j] = dp[i - 2][j];
                }
            }
        }

        return dp[M][N];
    }
}




