import java.util.Comparator;

/**
 * Priority Queue important stuff to remember
 * Node k's parent is k/2
 * Node k's children are k*2 and k*2 + 1
 * @param <Key>
 */

public class DynamicMedian<Key extends Comparable> {
    PriorityQueue pqMax;
    PriorityQueue pqMin;

    private class LessComparator implements Comparator<Key> { // need to figure out why this comparator works this way
        public int compare(Key a, Key b) {
            return b.compareTo(a);
        }
    }

    private class MoreComparator implements Comparator<Key> { // need to figure out why this comparator works this way
        public int compare(Key a, Key b) {
            return a.compareTo(b);
        }
    }
    private class PriorityQueue {
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

        private final Comparator comparator;

        /**
         * Create priority queue
         * @param keys keys we want to insert in the queue
         * @param comparator comparator we want to use to compare keys
         */
        public PriorityQueue(Key[] keys, Comparator comparator) {
            this.maxNodes = keys.length;
            this.keys = (Key[]) new Comparable[maxNodes + 1];
            this.comparator = comparator;
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
        private boolean compare(int a, int b) {
            return comparator.compare(keys[a], keys[b]) < 0;
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
         * Move element at pos position up the tree according to comparator resolution
         * @param pos Element position we want to check
         */
        private void swim(int pos) {
            int k = pos; // we don't want to modify pos
            while (k > 1 && compare(k/2, k)) { // while we haven't reached the root and the parent is less than the child
                exchange(k/2, k); // exchange them
                k = k / 2; // go up the tree
            }
        }

        /**
         *
         * @return
         */
        Key poll() {
            if (N == 0) {
                return null;
            }
            if (N == 1) {
                return keys[N--];
            }
            Key ret = keys[1];
            exchange(1, N--);
            keys[N + 1] = null;
            sink(1);
            return ret;
        }
        /**
         * Move a key down the tree according to comparator resolution
         * @param pos Position of the key we want to check
         */
        private void sink(int pos) {
            int k = pos; // we don't want to modify pos
            while (2*k <= N) { // while we haven't reached the last node of the tree
                int child = 2*k; // first child node is 2*k
                if (child < N && compare(child, child + 1)) { // if we're not at the last node and the current child is less than the next child
                    child++; // move on to the next child
                }
                if (compare(child, k)) { // if the child is less than the parent
                    break; // we're done
                }
                exchange(k, child); // exchange parent and node
                k = child; // repeat with child
            }
        }
        public int size() {
            return N - 1;
        }
    }


    /**
     * Constructor that creates two binary trees to hold max and min values
     * @param keys keys that we want to insert in the binary trees
     */
    public DynamicMedian(Key[] keys) {
        MoreComparator moreComparator = new MoreComparator();
        LessComparator lessComparator = new LessComparator();
        pqMax = new PriorityQueue(keys, moreComparator);
        pqMin = new PriorityQueue(keys, lessComparator);
    }



    /**
     * Find the median, remove it from the list and return it to user
     * @return
     */
    public Key findTheMedian() {
        Key max = null;
        Key min = null;
        if (pqMax.size() < 1) {
            return pqMax.poll();
        }
        if (pqMax.size() % 2 == 0) {
            max = pqMax.poll();
            min = pqMin.poll();
            while (max.compareTo(min) != 0) {
                max = pqMax.poll();
                min = pqMin.poll();
            }
        }
        else {
            max = pqMax.poll();
            max = pqMax.poll();
            min = pqMin.poll();
            while (max.compareTo(min) != 0) {
                max = pqMax.poll();
                min = pqMin.poll();
            }
        }
        return max;
    }

    public static void main(String[] args) {
        DynamicMedian dm =  null;
        dm = new DynamicMedian(new Integer[] {1, 2});
        System.out.println("Median should be 1: " + dm.findTheMedian());
        dm = new DynamicMedian(new Integer[] {1, 2, 3});
        System.out.println("Median should be 2: " + dm.findTheMedian());
        dm = new DynamicMedian(new Integer[] {1, 2, 3, 4});
        System.out.println("Median should be 2: " + dm.findTheMedian());
        dm = new DynamicMedian(new Integer[] {1, 2, 3, 4, 5});
        System.out.println("Median should be 3: " + dm.findTheMedian());
        dm = new DynamicMedian(new Integer[] {1, 2, 3, 4, 5, 6});
        System.out.println("Median should be 3: " + dm.findTheMedian());
        dm = new DynamicMedian(new Integer[] {1, 2, 3, 4, 5, 6, 7});
        System.out.println("Median should be 4: " + dm.findTheMedian());

    }
}
