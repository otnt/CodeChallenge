/*
 * lintcode problem, Max Tree
 * http://www.lintcode.com/en/problem/max-tree/#
 *
 * Given an integer array with no duplicates. A max tree building on this array is defined as follow:
 * 
 * The root is the maximum number in the array
 * The left subtree and right subtree are the max trees of the subarray divided by the root number.
 * Construct the max tree by the given array.
 */

/*
 * Thoughts
 *
 * The most naive way, first find maximum element in array, use it as root,
 * then recursive do the similar operation to its left and right subtree. This
 * gives us O(N^2) time complexity.
 *
 * Lets think about what's the problem here. The key point is, when we meet a 
 * new element, it won't affect tree structure on previously larger elements,
 * just append to right. And for previously smaller elements, it will put them
 * as its left subtree. So we do not need to re-search the whole array each time.
 *
 * Refine our claim:
 * When meet a new element:
 * 1.the tree is empty, then it is root
 * 2.walk backforwardly until find the first element that is larger than itself,
 * put all elements in between as left subtree, and make self right subtree
 * of the larger element. Or if could not find a larger element, make it self 
 * as the root.
 *
 * So, we need a data structure/algorithm that could let us easily find the
 * first element that is larger than itself. So we could use a monotonically
 * decreasing array to do this. This is called monotonical priority queue.
 */

/*
 * Pseudocode:
 * input: nodes []int
 * output: root node
 *
 * mpq := empty list
 * root := nil
 *
 * for node := range nodes:
 *   largest := nil //largest element in all elements small than node
 *   while(mpq not empty && mpq.last_element < node):
 *     largest = mpq.pop()
 *   node.left = largest
 *   if(mpq not empty):
 *     mpq.last_element.right = node
 *   else
 *     root = node
 *   mpq.push(node)
 * 
 * return root
 */

public class MaxTree {
    public TreeNode maxTree(int[] A) {
        List<TreeNode> mpq = new ArrayList<TreeNode>();
        TreeNode root = null;

        for(int v : A) {
            TreeNode tmp = new TreeNode(v);
            TreeNode largest = null;
            while(!mpq.isEmpty() && mpq.get(mpq.size() - 1).val < v) {
                largest = mpq.remove(mpq.size() - 1);
            }
            tmp.left = largest;
            if(mpq.isEmpty()) {
                root = tmp;
            }
            else {
                mpq.get(mpq.size() - 1).right = tmp;
            }
            mpq.add(tmp);
        }

        return root;
    }
}

