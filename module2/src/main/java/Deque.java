import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    Node first;
    Node last;
    Node beforeLast;
    int size = 0;
    private class Node {
        Item item = null;
        Node next = null;
    }
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        beforeLast = null;
    }
    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node tmp = first;
            Node newElement = new Node();
            newElement.item = item;
            newElement.next = tmp;
            first = newElement;
            if (size() == 1) {
                last = first.next;
                beforeLast = first;
            }
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else if (size() == 1){
            Node newElement = new Node();
            newElement.item = item;
            first.next = newElement;
            last = newElement;
            beforeLast = first;
        }
        else {
            Node newElement = new Node();
            newElement.item = item;
            last.next = newElement;
            beforeLast = last;
            last = newElement;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item  item= first.item;
        if (size() == 1) {
            first = null;
            last = null;
            beforeLast = null;
        }
        else if (size() == 2) {
            beforeLast = null;
            first = first.next;

        }
        else {
            first = first.next;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item  item= last.item;
        if (size() == 1) {
            first = null;
            last = null;
            beforeLast = null;
        }
        else if (size() == 2) {
            last = first;
            beforeLast = null;
        }
        else {
            Node iterator= first;
            while (iterator.next != beforeLast) {
                iterator = iterator.next;
            }
            last = beforeLast;
            beforeLast = iterator;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque= new Deque<Integer>();
        System.out.println("Adding elements...");
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addFirst(0);
        deque.addLast(4);
        System.out.println("Removing elements from beginning of the list");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println("Adding elements...");
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addFirst(0);
        deque.addLast(4);
        Iterator iterator = deque.iterator();
        System.out.println("------");
        System.out.println("Deque content: ");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("------");
        System.out.println("Removing elements from end of the list");
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }


}
