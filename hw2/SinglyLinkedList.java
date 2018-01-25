/**
 * Your implementation of a circular singly linked list.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.2
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(T data, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                + " negative or larger than the size of the Linked List.");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                + "Linked List");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            LinkedListNode<T> node2 = new LinkedListNode<>(node.getData(),
                node.getNext());
            node.setData(data);
            node.setNext(node2);
            size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                + "Linked List");
        }
        if (size != 0) {
            LinkedListNode<T> node = new LinkedListNode<T>(head.getData(),
                head.getNext());
            head.setData(data);
            head.setNext(node);
        } else {
            LinkedListNode<T> node = new LinkedListNode<T>(data);
            node.setNext(node);
            head = node;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                + "Linked List");
        }
        if (size != 0) {
            LinkedListNode<T> node = new LinkedListNode<T>(head.getData(),
                head.getNext());
            head.setData(data);
            head.setNext(node);
            head = node;
        } else {
            LinkedListNode<T> node = new LinkedListNode<T>(data);
            node.setNext(node);
            head = node;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                + " negative or larger than or equal to the size of the "
                + "Linked List.");
        }
        T info;
        if (index == 0) {
            info = removeFromFront();
        } else if (index == size - 1) {
            info = removeFromBack();
        } else {
            LinkedListNode<T> node = head;
            for (int i = 0; i < index - 1; i++) {
                node = node.getNext();
            }
            info = node.getNext().getData();
            node.setNext(node.getNext().getNext());
            size--;
        }
        return info;
    }

    @Override
    public T removeFromFront() {
        if (size != 0) {
            T info = head.getData();
            if (size == 1) {
                head = null;
            } else if (size == 2) {
                head.setData(head.getNext().getData());
                head.setNext(head);
            } else {
                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());
            }
            size--;
            return info;
        }
        return null;
    }

    @Override
    public T removeFromBack() {
        if (size != 0) {
            T info;
            if (size == 1) {
                info = head.getData();
                head = null;
            } else if (size == 2) {
                info = head.getNext().getData();
                head.setNext(head);
            } else {
                LinkedListNode<T> node = head;
                for (int i = 0; i < size - 1; i++) {
                    node = node.getNext();
                }
                info = node.getData();
                node.setNext(head);
            }
            size--;
            return info;
        }
        return null;
    }

    @Override
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elements to "
                + "Linked List");
        }
        if (size != 0) {
            T info;
            LinkedListNode<T> node = head;
            LinkedListNode<T> node2 = null;
            for (int i = 0; i < size - 1; i++) {
                if (node.getNext().getData().equals(data)) {
                    node2 = node;
                }
                node = node.getNext();
            }
            if (node2 == null) {
                if (head.getData().equals(data)) {
                    info = head.getData();
                    head.setData(head.getNext().getData());
                    head.setNext(head.getNext().getNext());
                } else {
                    return null;
                }
            } else {
                info = node2.getNext().getData();
                node2.setNext(node2.getNext().getNext());
            }
            size--;
            if (size == 0) {
                head = null;
            }
            return info;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index specified was either"
                + " negative or larger than or equal to the size of the "
                + "Linked List.");
        }
        LinkedListNode<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node.getData();
    }

    @Override
    public Object[] toArray() {
        Object[] listArray = new Object[size];
        if (size != 0) {
            LinkedListNode<T> node = head;
            for (int i = 0; i < size; i++) {
                listArray[i] = node.getData();
                node = node.getNext();
            }
        }
        return listArray;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}