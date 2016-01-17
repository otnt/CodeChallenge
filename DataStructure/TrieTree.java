//My implementation of Trie Tree, a naive generic version
//This trie tree supports any number of node, and any class of node data
//For more information about trie tree:
//https://en.wikipedia.org/wiki/Trie

import java.util.List;
import java.util.ArrayList;

public class TrieTree<D> {
    class Node {
        private List<Node> nodes;
        private D data;

        /* initialize node list with all null elements */
        public Node() {
            nodes = new ArrayList<Node>(nodeNumber);
            for(int i = 0; i < nodeNumber; i++) {
                nodes.add(null);
            }
        }

        public Node get(int index) {
            return nodes.get(index);
        }

        /* insert sub-level node at given index */
        public Node insert(int index) {
            Node node;
            if((node = get(index)) != null) {
                return node;
            }
            node = new Node();
            nodes.set(index, node);
            return node;
        }

        public void setData(D data) {this.data = data;}
        public void removeData() {this.data = null;}
        public D getData() {return data;}
    }

    private int nodeNumber;
    private Node root;

    /* initialize dummy root node */
    public TrieTree(int nodeNumber) {
        this.nodeNumber = nodeNumber;
        root = new Node();
    }

    public void insert(int[] index, D data) {
        Node node = root;
        for(int i : index) {
            node = node.insert(i);
        }
        node.setData(data);
    }

    public D get(int[] index) {
        Node node = root;
        for(int i : index) {
            node = node.get(i);
            if(node == null) {
                return null;
            }
        }
        return node.getData();
    }

    public static void main(String argv[]) {
        TrieTree<Boolean> tt = new TrieTree<Boolean>(26);
        int[] abc = new int[]{0, 1, 2};
        int[] bcd = new int[]{1, 2, 3};
        int[] bdc = new int[]{1, 3, 2};
        int[] abb = new int[]{0, 1, 1};
        tt.insert(abc, new Boolean(true));
        tt.insert(bcd, new Boolean(true));
        tt.insert(bdc, new Boolean(true));
        System.out.println(tt.get(abc));
        System.out.println(tt.get(bcd));
        System.out.println(tt.get(bdc));
        System.out.println(tt.get(abb));
    }
}
