import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue = (Item[]) new Object[2];
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    private Item[] resize(Item[] queue, int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];
        for (int i = 0, j = 0; i < queue.length; i++) {
            if (queue[i] != null) {
                tmp[j++] = queue[i];
            }

        }
        return tmp;
    }

    private int getRandomPosition(Item[] queue) {
        int randomPosition = StdRandom.uniformInt(0, queue.length - 1);
        while (queue[randomPosition] == null) {
            randomPosition = StdRandom.uniformInt(0, queue.length - 1);
        }
        return randomPosition;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (queue[queue.length - 1] != null) {
            queue = resize(queue, queue.length * 2);
        }
        queue[size++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomPosition = getRandomPosition(queue);
        Item item = queue[randomPosition];
        queue[randomPosition] = null;
        size--;
        if (size <= queue.length / 4) {
            queue = resize(queue, queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomPosition = getRandomPosition(queue);
        Item item = queue[randomPosition];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        RandomizedQueue<Item> iteratorQueue = new RandomizedQueue<>();

        public RandomQueueIterator() {
            for (int i = 0; i < queue.length; i++) {
                if (queue[i] != null) {
                    iteratorQueue.enqueue(queue[i]);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !iteratorQueue.isEmpty();
        }

        @Override
        public Item next() {
            if (iteratorQueue.isEmpty()) {
                throw new java.util.NoSuchElementException();
            }
            return iteratorQueue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
        for (int a : randomizedQueue) {
            StdOut.println(a);
        }
        for (int a : randomizedQueue) {
            for (int b : randomizedQueue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        StdOut.println("Randomized sample: " + randomizedQueue.sample());
    }

}
