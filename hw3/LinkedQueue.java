import java.util.NoSuchElementException;

/**
 * Your implementation of a linked queue. It should NOT be circular.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty and has no first element.");
        }
        T item = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }
        size--;
        return item;
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to Queue.");
        }
        LinkedNode<T> fin = new LinkedNode<T>(data);
        if (size == 0) {
            head = fin;
            tail = fin;
        } else {
            tail.setNext(fin);
            tail = fin;
        }
        size++;
    }

    @Override
    public T peek() {
        if (size != 0) {
            return head.getData();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        // DO NOT MODIFY THIS METHOD!
        return size == 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }
}