import java.util.NoSuchElementException;

/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty and has no"
                    + " top-most element.");
        }
        T item = head.getData();
        if (size == 1) {
            head = null;
        } else {
            head = head.getNext();
        }
        size--;
        return item;

    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements"
                    + " to Stack.");
        }
        LinkedNode<T> frente = new LinkedNode<T>(data, head);
        head = frente;
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
     * Returns the head of this stack.
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
}