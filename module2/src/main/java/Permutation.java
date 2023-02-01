import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 1) {
            int permutations = Integer.parseInt(args[0]);
            RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                randomizedQueue.enqueue(StdIn.readString());
            }
            while (permutations >= 0) {
                StdOut.println(randomizedQueue.dequeue());
            }
        }
    }
}
