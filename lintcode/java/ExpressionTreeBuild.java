/*
 * Lintcode problem Expression Tree Build, http://www.lintcode.com/en/problem/expression-tree-build/
 * The structure of Expression Tree is a binary tree to evaluate certain expressions.
 * All leaves of the Expression Tree have an number string value. All non-leaves of 
 * the Expression Tree have an operator string value.
 * Now, given an expression array, build the expression tree of this expression, 
 * return the root of this expression tree.
 *
 * For the expression (2*6-(23+7)/(1+2)) (which can be represented by 
 * ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]). 
 * The expression tree will be like
 *                  [ - ]
 *              /          \
 *         [ * ]              [ / ]
 *       /     \           /         \
 *     [ 2 ]  [ 6 ]      [ + ]        [ + ]
 *                      /    \       /      \
 *                    [ 23 ][ 7 ] [ 1 ]   [ 2 ] .
 * After building the tree, you just need to return root node [-].
 *
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - All integers? yes
 * 1.2 [Q2] - expression is guaranteed to be valid? yes
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * This is a further problem after build reverse polish expression. If you
 * are not familiar with polish expression, try solve this problem first:
 * http://www.lintcode.com/en/problem/convert-expression-to-reverse-polish-notation/
 * Thus, this is a typical case to use stack. Now suppose we got a
 * reverse polish expression. Then we just need to traverse the reverse polish
 * expression, use another stack to store the tree struture. Each time
 * we met a number, we build a new node containing this number and push to
 * stack. Each time we met a operant, we pop two elements, and put to this
 * operant's children, then push back to stack.
 * After we traverse the reverse polish expression, we could check whether
 * the stack has exactly one element, thus to check whether the expression
 * is valid.
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

public class ExpressionTreeBuild{
    public ExpressionTreeNode build(String[] expression) {
        if(expression == null) {
            throw new NullPointerException();
        }
        if(expression.length == 0) {
            throw new IllegalArgumentException();
        }

        //1.build a reverse polish expression
        LinkedList<String> stackRpe = new LinkedList<String>();
        List<String> rpe = new ArrayList<String>(expression.length);
        for(String e : expression) {
            switch(e.charAt(0)) {
                case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                    rpe.add(e);
                    break;
                case '+': case'-':
                    while(!stackRpe.isEmpty() && (stackRpe.peekLast().charAt(0) != '(')){
                        rpe.add(stackRpe.removeLast());
                    }
                    stackRpe.addLast(e);
                    break;
                case '*': case '/':
                    while(!stackRpe.isEmpty() && (stackRpe.peekLast().charAt(0) == '*' ||
                                stackRpe.peekLast().charAt(0) == '/')){
                        rpe.add(stackRpe.removeLast());
                    }
                    stackRpe.addLast(e);
                    break;
                case '(':
                    stackRpe.addLast(e);
                    break;
                case ')':
                    String s = null;
                    while((s = stackRpe.removeLast()).charAt(0) != '(') {
                        rpe.add(s);
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        //pop out all remaining elements
        while(!stackRpe.isEmpty()) {
            rpe.add(stackRpe.removeLast());
        }

        //2.build expressioin tree from reverse polish expression
        LinkedList<ExpressionTreeNode> stackEt = new LinkedList<ExpressionTreeNode>();
        for(String e : rpe) {
            switch(e.charAt(0)) {
                case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                    stackEt.addLast(new ExpressionTreeNode(e));
                    break;
                case '+': case '-': case '*': case '/':
                    ExpressionTreeNode etn = new ExpressionTreeNode(e);
                    etn.right = stackEt.removeLast();
                    etn.left = stackEt.removeLast();
                    stackEt.addLast(etn);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return stackEt.peekLast();
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
