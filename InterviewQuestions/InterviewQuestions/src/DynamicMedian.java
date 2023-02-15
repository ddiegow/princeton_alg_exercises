/**
 * Priority Queue important stuff to remember
 * Node k's parent is k/2
 * Node k's children are k*2 and k*2 + 1
 * @param <Key>
 */

public class DynamicMedian<Key extends Comparable<Key>> {
    /**
     * Array of keys that form the binary tree
     */
    private final Key[] keys;
    /**
     * Current number of elements
     */
    private int N = 0;
    /**
     * Max number of nodes
     */
    private final int maxNodes;

    /**
     * Constructor that creates an empty binary tree with max number of nodes
     * @param maxNodes Max number of nodes
     */
    public DynamicMedian(int maxNodes) {
        this.keys = (Key[]) new Comparable[maxNodes + 1];
        this.maxNodes = maxNodes;
    }

    /**
     * Constructor that creates a binary tree out of
     * @param keys keys that we want to insert in the binary tree
     */
    public DynamicMedian(Key[] keys) {
        this.maxNodes = keys.length;
        this.keys = (Key[]) new Comparable[maxNodes + 1];
        for (int i = 0; i < maxNodes; i++) { // N
            insert(keys[i]); // log N
        }
    }

    /**
     * Insert a key in the binary tree
     * @param key The key we want to insert
     */
    public void insert(Key key) {
        if (N == maxNodes) {
            System.out.println("Max limit reached.  Cannot insert more values");
            return;
        }
        keys[++N] = key;
        swim(N); // log N
    }

    /**
     * Compare to elements of the tree
     * @param a first element to compare
     * @param b second element to compare
     * @return a < b
     */
    private boolean less(int a, int b) {
        return keys[a].compareTo(keys[b]) < 0;
    }

    /**
     * Exchange element at pos1 with element at pos2
     * @param pos1 first element we want to exchange
     * @param pos2 second element we want to exchange
     */
    private void exchange(int pos1, int pos2) {
        Key tmp = keys[pos1];
        keys[pos1] = keys[pos2];
        keys[pos2] = tmp;
    }

    /**
     * Move element at pos position up the tree while its parents are smaller
     * @param pos Element position we want to check
     */
    private void swim(int pos) {
        int k = pos; // we don't want to modify pos
        while (k > 1 && less(k/2, k)) { // while we haven't reached the root and the parent is less than the child
            exchange(k/2, k); // exchange them
            k = k / 2; // go up the tree
        }
    }

    /**
     * Move a key down the tree while it's smaller than its children
     * @param pos Position of the key we want to check
     */
    private void sink(int pos) {
        int k = pos; // we don't want to modify pos
        while (2*k <= N) { // while we haven't reached the last node of the tree
            int child = 2*k; // first child node is 2*k
            if (child < N && less(child, child + 1)) { // if we're not at the last node and the current child is less than the next child
                child++; // move on to the next child
            }
            if (less(child, k)) { // if the child is less than the parent
                break; // we're done
            }
            exchange(k, child); // exchange parent and node
            k = child; // repeat with child
        }
    }

    /**
     * Find the median, remove it from the list and return it to user
     * @return
     */
    public Key findTheMedian() {
        System.out.println("N: " + N);
        System.out.println("N % 2: " + N%2);
        return N % 2 == 0 ? keys[N/2 - 1] : keys[N/2 + 1];
    }
    public void printOut() {
        for (int i = 0; i < keys.length; i ++) {
            System.out.print(keys[i] + " ");
        }
    }
    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian(new Integer[] {2,3,1,4}); // 1 2 3 4
        dm.printOut();
        System.out.println();
        System.out.println(dm.findTheMedian());

        dm = new DynamicMedian(new Integer[] {3,2,1,5,4}); // 1 2 3 4 5;
        dm.printOut();
        System.out.println();
        System.out.print(dm.findTheMedian());

    }
}
