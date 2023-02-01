import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int permutations = StdIn.readInt();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (StdIn.hasNextLine()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        while (permutations >= 0) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
