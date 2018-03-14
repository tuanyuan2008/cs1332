import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot "
                    + "initialize BST with null data.");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot initialize "
                        + "BST with null data.");
            }
            add(item);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to tree.");
        }
        root = add(data, root);
    }

    /**
     * Add the data as a leaf in the BST.  Should traverse the tree to find the
     * appropriate location.
     *
     * @return the newly added node.
     * @param data the data to be added to the tree.
     * @param node the node currently being evaluated.
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        }
        recalculate(node);
        node = rotate(node);
        return node;
    }

    /**
     * Recalculates and updates the height and balance factor
     * of node
     *
     * @param node the node currently being evaluated
     */
    private void recalculate(AVLNode<T> node) {
        int left = node.getLeft() != null ? node.getLeft().getHeight() : -1;
        int right = node.getRight() != null ? node.getRight().getHeight() : -1;
        int height = left >= right ? left + 1 : right + 1;
        node.setHeight(height);
        node.setBalanceFactor(left - right);
    }

    /**
     * Decides based on balance factors of node and
     * its children whether or not to rotate the tree
     *
     * @return the new "root" of the rotated sub-tree
     * @param node the node currently being evaluated
     */
    private AVLNode<T> rotate(AVLNode<T> node) {
        if (node.getRight() != null) {
            if (node.getBalanceFactor() == -2
                    && (node.getRight().getBalanceFactor() == -1
                    || node.getRight().getBalanceFactor() == 0)) {
                node = leftRotation(node);
            } else if (node.getBalanceFactor() == -2
                    && node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotation(node.getRight()));
                node = leftRotation(node);
            }
        }
        if (node.getLeft() != null) {
            if (node.getBalanceFactor() == 2
                    && (node.getLeft().getBalanceFactor() == 1
                    || node.getLeft().getBalanceFactor() == 0)) {
                node = rightRotation(node);
            } else if (node.getBalanceFactor() == 2
                    && node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotation(node.getLeft()));
                node = rightRotation(node);
            }
        }
        return node;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        AVLNode<T> temp = new AVLNode<>(null);
        root = remove(data, root, temp);
        return temp.getData();
    }

    /**
     * Removes the data from the tree.
     *
     * @return the node replacing the one removed.
     * @param data the data being removed from the tree.
     * @param node the node currently being evaluated.
     * @param temp the placeholder node storing the data to be removed.
     */
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> temp) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        } else if (node == null) {
            throw new NoSuchElementException("Node not found in tree.");
        }
        if (node.getData().compareTo(data) == 0) {
            temp.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> placehold = new AVLNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), placehold));
                node.setData(placehold.getData());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), temp));
        } else {
            node.setRight(remove(data, node.getRight(), temp));
        }
        recalculate(node);
        node = rotate(node);
        return node;
    }

    /**
     * Finds the successor of a node with two children.
     *
     * @param node the node currently being evaluated.
     * @param temp the placeholder node storing the data to be removed.
     * @return the data in the node replacing the one removed.
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), temp));
            recalculate(node);
            node = rotate(node);
            return node;
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        }
        return get(data, root);
    }

    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @param data the data to search for in the tree.
     * @param node the node currently being evaluated.
     * @return the data in the tree equal to the parameter.
     */
    private T get(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Tree does not contain "
                    + "specified data.");
        }
        if (data.compareTo(node.getData()) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else {
            return get(data, node.getRight());
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data "
                    + "in tree.");
        }
        return contains(data, root);
    }

    /**
     * Returns whether or not data equivalent to the given parameter
     * is contained within the tree.
     *
     * @param data the data to search for in the tree.
     * @param node the node currently being evaluated for its data.
     * @return whether or not the parameter is contained within the tree.
     */
    private boolean contains(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        int val = node.getData().compareTo(data);
        if (val > 0) {
            return contains(data, node.getLeft());
        } else if (val < 0) {
            return contains(data, node.getRight());
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public T getSecondLargest() {
        if (size < 2) {
            throw new NoSuchElementException("No second "
                    + "largest element exists in this tree.");
        }
        AVLNode<T> temp = getSecondLargest(root);
        return temp.getData();
    }

    /**
     * Retrieves the second largest data from the tree.
     *
     * @param node the node currently being evaluated
     * @return the second largest data in the tree
     */
    private AVLNode<T> getSecondLargest(AVLNode<T> node) {
        if (node.getRight() == null) {
            return node.getLeft();
        } else if (node.getRight() != null
                && node.getRight().getRight() == null) {
            if (node.getRight().getLeft() != null) {
                return node.getRight().getLeft();
            }
            return node;
        } else {
            return getSecondLargest(node.getRight());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof AVL) {
            AVL<T> that = (AVL<T>) obj;
            return equals(root, that.root);
        }
        return false;
    }

    /**
     * Determines whether this tree is equal to the passed in object
     * by checking node equality using a pre-order traversal.
     *
     * @param node1 one of two nodes being checked for equality
     * @param node2 one of two nodes being checked for equality
     * @return true if the trees are equal, false otherwise
     */
    private boolean equals(AVLNode<T> node1, AVLNode<T> node2) {
        if (node1 == null && node2 == null) {
            return true;
        } else if (node1 != null && node2 != null) {
            return node1.getData() == node2.getData()
                    && equals(node1.getLeft(), node2.getLeft())
                    && equals(node1.getRight(), node2.getRight());
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Implements a left rotation on this tree
     *
     * @return the new "root" of the rotated sub-tree
     * @param node the node currently being evaluated
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        node.setRight(temp.getLeft());
        recalculate(node);
        temp.setLeft(node);
        recalculate(temp);
        return temp;
    }

    /**
     * Implements a right rotation on this tree
     *
     * @return the new "root" of the rotated subtree
     * @param node the node currently being evaluated
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        node.setLeft(temp.getRight());
        recalculate(node);
        temp.setRight(node);
        recalculate(temp);
        return temp;
    }
}