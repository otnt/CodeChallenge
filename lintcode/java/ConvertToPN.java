/*
 * Lintcode problem Convert Expression to Polish Notation, http://www.lintcode.com/en/problem/convert-expression-to-polish-notation/
 *
 * Given an expression string array, return the Polish notation of this expression. (remove the parentheses)
 * Example
 * For the expression [(5 − 6) * 7] (which represented by ["(", "5", "−", "6", ")", "*", "7"]), the corresponding polish notation is [* - 5 6 7] (which the return value should be ["*", "−", "5", "6", "7"]).
 *
 * Clarification
 * Definition of Polish Notation:
 * http://en.wikipedia.org/wiki/Polish_notation
 * http://baike.baidu.com/view/7857952.htm
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - all expressions are valid? - yes
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * Most of us are familiar with reverse polish expression. The building of polish expression
 * is really similar to reverse polish expression. If you are not familiar with reverse
 * polish expression, try solve this problem first: 
 * http://www.lintcode.com/en/problem/convert-expression-to-reverse-polish-notation/
 *
 * But one tricky thing is:
 * In reverse polish expression, when we met + - * / we should pop out
 * previous operands that has higher OR EQUAL priority. But in polish
 * expression, we should only pop out operands that has higher priority.
 * The reason is we are doing calculation in computer, where order of
 * same-priority operands DO MATTER!
 *
 * So, here, we traverse the input array from back to front.
 * 1.when met number, print out
 * 2.when met * or /, push new * or /
 * 3.when met + or -, pop out previous * /, and push new + or -
 * 4.when met ), push it!
 * 5.when met (, pop out all previous operands until find a )
 *
 * In conclusion, we could do it like this.
 * We define priority of all operands:
 * (, ) : 0
 * + - : 1
 * * / : 2
 * when we met + - * / we pop out everything that has higher priority, then push it into stack
 * when we met ) we push it into stack
 * when we met ( we pop everything out until find a )
 *
 * 2.2 [Time complexity] - O(N)
 * 2.3 [Space complexity] - O(N)
 * 2.4 [If huge amount of data] - 
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

public class ConvertToPN{
    public ArrayList<String> convertToPN(String[] expression) {
        if(expression == null) {
            throw new NullPointerException();
        }
        if(expression.length == 0) {
            return new ArrayList<String>();
        }

        LinkedList<String> operands = new LinkedList<String>();
        ArrayList<String> rst = new ArrayList<String>();
        for(int i = expression.length - 1; i >= 0; i--) {
            String e = expression[i];

            if(isNumber(e)) {
                rst.add(e);
            }
            else if(e.charAt(0) == ')') {
                operands.addLast(e);
            }
            else if(e.charAt(0) == '(') {
                String s = null;
                while((s = operands.removeLast()).charAt(0) != ')') {
                    rst.add(s);
                }
            }
            else {
                while(!operands.isEmpty() && isLessPriority(e, operands.peekLast())) {
                    rst.add(operands.removeLast());
                }
                operands.addLast(e);
            }
        }
        //pop out evertying remaining
        while(!operands.isEmpty()) {
            rst.add(operands.removeLast());
        }

        Collections.reverse(rst);
        return rst;
    }

    public boolean isNumber(String e) {
            return '0' <= e.charAt(0) && e.charAt(0) <= '9';
    }

    public boolean isLessPriority(String e1, String e2) {
        //e1 is + -, e2 is * / then e1 < e2
        if((e1.equals("+") || e1.equals("-")) &&
            (e2.equals("*") || e2.equals("/"))) {
            return true;
        }
        return false;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
