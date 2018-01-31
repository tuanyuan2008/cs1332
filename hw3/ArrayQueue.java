import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed queue.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[QueueInterface.INITIAL_CAPACITY];
        front = 0;
        size = 0;
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you should
     * explicitly reset front to 0.
     *
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     *
     * See the homework pdf for more information on implementation details.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        if (size == 0 ) {
            throw new NoSuchElementException("Queue is empty and has no first element.");
        }
        T elem = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        if (size == 0) {
            front = 0;
        }
        return elem;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current length. If a regrow is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to Queue.");
        }
        if (size == backingArray.length) {
            Object[] tempArray = new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[(i + front) % backingArray.length];
            }
            backingArray = (T[]) tempArray;
            backingArray[size] = data;
            front = 0;
        } else {
            backingArray[(size + front) % backingArray.length] = data;
        }
        size++;
    }

    @Override
    public T peek() {
        if (size != 0) {
            return backingArray[front];
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
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}