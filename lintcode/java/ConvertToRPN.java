/*
 * Lintcode problem, convert to reverse polish notation, 
 * http://www.lintcode.com/en/problem/convert-expression-to-reverse-polish-notation/
 * Given an expression string array, return the Reverse Polish notation of this 
 * expression. (remove the parentheses)
 *
 * Example
 * For the expression [3 - 4 + 5] (which denote by ["3", "-", "4", "+", "5"]), 
 * return [3 4 - 5 +] (which denote by ["3", "4", "-", "5", "+"])
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] -
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * This is a good example to use a stack.
 * Basically,
 * When we met:
 * 1.number: print out
 * 2.(: push into stack
 * 3.): pop out everything in stack and print them out until we find a (
 * 4.+ - * /: pop out everything that has higher or equal priority,
 * then push new operand into stack
 * 5.after traverse the input array, pop out everything remaining in stack
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

public class ConvertToRPN{
    public ArrayList<String> convertToRPN(String[] expression) {
        if(expression == null) {
            throw new NullPointerException();
        }
        if(expression.length == 0) {
            return new ArrayList<String>();
        }

        LinkedList<String> operands = new LinkedList<String>();
        ArrayList<String> rst = new ArrayList<String>();
        for(String e : expression) {
            if(isNumber(e)) {
                rst.add(e);
            }
            else if(e.charAt(0) == '(') {
                operands.addLast(e);
            }
            else if(e.charAt(0) == ')') {
                String s = null;
                while((s = operands.removeLast()).charAt(0) != '(') {
                    rst.add(s);
                }
            }
            else {
                while(!operands.isEmpty() && isLessOrEqualPriority(e, operands.peekLast())) {
                    rst.add(operands.removeLast());
                }
                operands.addLast(e);
            }
        }
        //pop out evertying remaining
        while(!operands.isEmpty()) {
            rst.add(operands.removeLast());
        }

        return rst;
    }

    public boolean isNumber(String e) {
            return '0' <= e.charAt(0) && e.charAt(0) <= '9';
    }

    public boolean isLessOrEqualPriority(String e1, String e2) {
        //( could not be pop out
        if(e2.equals("(")) {
            return false;
        }
        //e1 is + -, then e1 <= e2
        if((e1.equals("+") || e1.equals("-"))) {
            return true;
        }
        //e1 is * / e2 is + -
        if(e2.equals("+") || e2.equals("-")) {
            return false;
        }
        //e1 is * / e2 is * /
        return true;
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
