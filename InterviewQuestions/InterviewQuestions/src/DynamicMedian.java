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
        public PriorityQueue(int maxNodes, Comparator comparator) {
            this.maxNodes = maxNodes;
            this.keys = (Key[]) new Comparable[maxNodes + 1];
            this.comparator = comparator;
        }

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
         * Extract the root element from the binary tree and reconfigure the tree
         * @return root element of binary tree
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

        /**
         * Take a look at the root element
         * @return root element of the binary tree
         */
        public Key peek() {
            if (N == 0) {
                return null;
            }
            return keys[1];
        }
        public int size() {
            return N;
        }
    }


    /**
     * Constructor that inserts keys into the max pq or the min pq according to the following criteria:
     *      - If none of the other rules apply, insert by default into max pq
     *      - If the key to insert is bigger than the key obtained by peeking into min pq, insert into min pq
     *      - If one of the queues is bigger than the other by two keys or more, poll the bigger queue and insert into smaller queue
     * Time complexity: O(log n)
     * @param keys keys that we want to insert into the queues
     */
    public DynamicMedian(Key[] keys) {
        MoreComparator moreComparator = new MoreComparator();
        LessComparator lessComparator = new LessComparator();
        pqMax = new PriorityQueue(keys.length, moreComparator);
        pqMin = new PriorityQueue(keys.length, lessComparator);
        for (int i = 0; i < keys.length; i++) {
            if (pqMax.size() <= 1 && pqMin.size() == 0) { // if max pq has one element or less and min pq has none
                pqMax.insert(keys[i]); // insert into max pq
            }
            else {
                // if the minimum item is less than the key, insert in min pq
                if (pqMin.peek().compareTo(keys[i]) < 0) {
                    pqMin.insert(keys[i]);
                }
                // if not, insert in max pq
                else {
                    pqMax.insert(keys[i]);
                }
            }
            // Check sizes
            if (pqMax.size() - pqMin.size() >= 2) { // if max pq size is bigger
                pqMin.insert(pqMax.poll()); // insert max key in min pq
            }
            else if (pqMin.size() - pqMax.size() >=2) { // if min pq size is bigger
                pqMax.insert(pqMin.poll()); // insert min key in max pq
            }
        }
    }



    /**
     * Find the median.  If the priority queues are different sizes, the median is in the min priority queue.
     * If they are the same size, the median is in the max priority queue.
     * @return median of the array the object was created with
     */
    public Key findTheMedian() {
        if (pqMax.size() == pqMin.size()) {
            return pqMin.peek();
        }
        else {
            return pqMax.peek();
        }
    }
    public Key removeTheMedian() {
        Key ret;
        if (pqMax.size() == pqMin.size()) {
            ret = pqMin.poll();
        }
        else {
            ret = pqMax.poll();
        }
        return ret;
    }
    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian(new Integer[] {2, 1, 7, 6, 5, 4, 3});
        // 1 2 3 4 5 6 7
        System.out.println("Finding the median. Return value should be 4: " + dm.findTheMedian());
        System.out.println("Removing the median. Return value should be 4: " + dm.removeTheMedian());
    }
}
