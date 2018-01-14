/**
 * Your implementation of an ArrayList.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        Object[] newArray = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) newArray;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                    + "ArrayList");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                    + " negative or larger than the size of the ArrayList.");
        }
        if (size == backingArray.length) {
            Object[] newArray = new Object[2 * backingArray.length];
            for (int i = 0; i < index; i++) {
                newArray[i] = backingArray[i];
            }
            newArray[index] = data;
            for (int i = index + 1; i < size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = (T[]) newArray;
        } else {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
        }
        size++;
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                    + "ArrayList");
        }
        if (size == backingArray.length) {
            Object[] newArray = new Object[2 * backingArray.length];
            newArray[0] = data;
            for (int i = 1; i < size + 1; i++) {
                newArray[i] = backingArray[i - 1];
            }
            backingArray = (T[]) newArray;
        } else {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                    + "ArrayList");
        }
        if (size == backingArray.length) {
            Object[] newArray = new Object[2 * backingArray.length];
            for (int i = 0; i < size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = (T[]) newArray;
        }
        backingArray[size] = data;
        size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                + " negative or larger than or equal to the size of the "
                + "ArrayList.");
        }
        T elem = backingArray[index];
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return elem;
    }

    @Override
    public T removeFromFront() {
        if (!isEmpty()) {
            T headElem = backingArray[0];
            for (int i = 0; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
            return headElem;
        }
        return null;
    }

    @Override
    public T removeFromBack() {
        if (!isEmpty()) {
            T tailElem = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return tailElem;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                + " negative or larger than or equal to the size of the "
                + "ArrayList.");
        }
        return backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Object[] newArray = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) newArray;
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
