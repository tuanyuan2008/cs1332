import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The initial array before the Build Heap algorithm takes place should
     * contain the data in the same order as it appears in the ArrayList.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot build max heap "
                    + "with null data.");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot build max heap "
                        + "with null data.");
            }
        }
        size = data.size();
        backingArray = (T[]) new Comparable[size * 2 + 1];
        for (int i = 0; i < size; i++) {
            backingArray[i + 1] = data.get(i);
        }
        int index = size / 2;
        for (int i = index; i > 0; i--) {
            remove(i);
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to heap.");
        }
        int index = size + 1;
        if (index >= backingArray.length) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        backingArray[index] = item;
        int parentInd = index / 2;
        while (index > 1
                && backingArray[parentInd].compareTo(backingArray[index]) < 0) {
            T temp = backingArray[parentInd];
            backingArray[parentInd] = backingArray[index];
            backingArray[index] = temp;
            index = parentInd;
            parentInd /= 2;
        }
        size++;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an "
                    + "empty heap.");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        remove(1);
        return temp;
    }

    /**
     * Helper method for remove that heapifies data after
     * root removal.
     *
     * @param ind the index currently being evaluated.
     */
    private void remove(int ind) {
        int child1 = ind * 2;
        int child2 = ind * 2 + 1;
        if (child2 <= size) {
            if (backingArray[child1].compareTo(backingArray[child2]) > 0
                    && backingArray[child1].compareTo(backingArray[ind]) > 0) {
                T temp = backingArray[ind];
                backingArray[ind] = backingArray[child1];
                backingArray[child1] = temp;
                remove(child1);
            } else if (backingArray[child2].compareTo(backingArray[child1]) > 0
                    && backingArray[child2].compareTo(backingArray[ind]) > 0) {
                T temp = backingArray[ind];
                backingArray[ind] = backingArray[child2];
                backingArray[child2] = temp;
                remove(child2);
            }
        } else if (child1 <= size) {
            if (backingArray[child1].compareTo(backingArray[ind]) > 0) {
                T temp = backingArray[ind];
                backingArray[ind] = backingArray[child1];
                backingArray[child1] = temp;
                remove(child1);
            }
        }
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

    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}