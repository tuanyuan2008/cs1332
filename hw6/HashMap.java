import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.2
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Error,key is null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if ((size + 1.0) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
            index =  Math.abs(key.hashCode() % table.length);
        }
        MapEntry<K, V> temp = table[index];
        while (temp != null) {
            if (temp.getKey().equals(key)) {
                V val = temp.getValue();
                temp.setValue(value);
                return val;
            }
            temp = temp.getNext();
        }
        size++;
        MapEntry<K, V> temp2 = new MapEntry<>(key, value, table[index]);
        table[index] = temp2;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "value at null key.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new NoSuchElementException("Key does "
                    + "not exist inside hash map.");
        } else {
            if (table[index].getKey().equals(key)) {
                size--;
                V val = table[index].getValue();
                table[index] = table[index].getNext();
                return val;
            }
            MapEntry<K, V> temp = table[index];
            while (temp.getNext() != null) {
                if (temp.getNext().getKey().equals(key)) {
                    size--;
                    V val = temp.getNext().getValue();
                    temp.setNext(temp.getNext().getNext());
                    return val;
                }
                temp = temp.getNext();
            }
            throw new NoSuchElementException("Key does "
                    + "not exist inside hash map.");
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot get "
                    + "value at null key.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new NoSuchElementException("Key does "
                    + "not exist inside hash map.");
        } else {
            MapEntry<K, V> temp = table[index];
            while (temp != null) {
                if (temp.getKey().equals(key)) {
                    return temp.getValue();
                }
                temp = temp.getNext();
            }
            throw new NoSuchElementException("Key does "
                    + "not exist inside hash map.");
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            return false;
        } else {
            MapEntry<K, V> temp = table[index];
            while (temp != null) {
                if (temp.getKey().equals(key)) {
                    return true;
                }
                temp = temp.getNext();
            }
            return false;
        }
    }

    @Override
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (MapEntry<K, V> entry: table) {
            while (entry != null) {
                keys.add(entry.getKey());
                entry = entry.getNext();
            }
        }
        return keys;
    }

    @Override
    public List<V> values() {
        List<V> valueList = new ArrayList<>();
        for (MapEntry<K, V> entry: table) {
            while (entry != null) {
                valueList.add(entry.getValue());
                entry = entry.getNext();
            }
        }
        return valueList;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("Cannot resize "
                    + "backing table to a non-positive length "
                    + "or any length less than the current length.");
        }
        MapEntry<K, V>[] tempTable = new MapEntry[length];
        for (MapEntry<K, V> entry : table) {
            while (entry != null) {
                int index = Math.abs(entry.getKey().hashCode() % length);
                MapEntry<K, V> temp = new MapEntry<>(entry.getKey(),
                        entry.getValue(), tempTable[index]);
                tempTable[index] = temp;
                entry = entry.getNext();
            }
        }
        table = tempTable;
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }
}