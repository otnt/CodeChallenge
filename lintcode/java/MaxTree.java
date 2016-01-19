/*
 * Lintcode problem Max Tree, http://www.lintcode.com/en/problem/max-tree/
 * Given an integer array with no duplicates. A max tree building on this array is defined as follow:
 * The root is the maximum number in the array
 * The left subtree and right subtree are the max trees of the subarray divided by the root number.
 * Construct the max tree by the given array.
 *
 * Example
 * Given [2, 5, 6, 0, 3, 1], the max tree constructed by this array is:
 *     6
 *    / \
 *   5   3
 *  /   / \
 * 2   0   1
 * Challenge
 * O(n) time and memory.
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
 * 1.One solution
 * A possible solution is very straight-forward. We traverse the array, for each
 * number, we back-trace the previous array to find the first number that is
 * greater than this array. Then, we put this new number as right child of the
 * greater number, and move everything between greater number and new number to 
 * be left child of this new number(the direct left child would be the largest
 * number between the greater number and new number).
 * If we could not find such a greater number, we simply move everthing to the
 * left child, the direct left child is as before.
 * This solution is not really good because if the input array is in sorted
 * order, we need O(N^2) time to do this. Thus, we do a random shuffle
 * before we build the tree.
 *
 * 2.Better Solution
 * Now let's examine our previous solution. It is generally nice, but the problem
 * is we need at most O(N) time to find the previous larger number, so could we
 * reduce this to O(1) or amortizely O(1)?
 * This problem could be generalized to 1.find a number that is larger than 
 * new incoming number and 2.we do not need to keep trace of previous number
 * if we met a new biggest number. This lead us to idea of Monotone Priority
 * Queue.
 * In this case, we need to maintain a monotone decreasing queue. When we met
 * a new number, we move everything previous that is smaller than this number
 * out of queue, and put then to left child of this number, where the direct
 * left child is last number that is smaller to new number. Then, if there is 
 * still a larger number, we put our new incoming number as right child of it,
 * otherwise, our new incoming number is the root.
 * So what's time complexity of this method? It's O(N). Why? Because everytime
 * we check one more number in monotone priority queue, we remove it. And there
 * are at most N number we could remove. So the overall time compleixty is O(N),
 * althgouth each step could take at most O(N), but amortizely each step is O(1).
 *
 * Since we add and remove element only at tail of queue, we could use a stack
 * to implement this monotone priority queue.
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

public class MaxTree{
    public TreeNode maxTree(int[] A) {
        if(A == null) {
            throw new NullPointerException();
        }
        if(A.length == 0) {
            return null;
        }

        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode node = null;//used to keep track of removed tree nodes
        TreeNode tmp = null;//new node
        TreeNode root = null;
        for(int i : A) {
            tmp = new TreeNode(i);
            node = null;
            //remove previous smaller number, save last smaller number
            while(!stack.isEmpty() && stack.peekLast().val < i) {
                node = stack.removeLast();
            }
            //save last smaller number as left child
            tmp.left = node;
            //save new node as root or right child of larger number
            if(stack.isEmpty()) {
                root = tmp;
            }
            else {
                stack.peekLast().right = tmp;
            }
            //add new node to stack
            stack.addLast(tmp);
        }

        return root;
    }

    class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
