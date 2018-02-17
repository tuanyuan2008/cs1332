import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Sarah Chen
 * @userid schen475
 * @GTID 903190753
 * @version 1.1
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {

    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
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
        if (!contains(data)) {
            if (root == null) {
                root = new BSTNode<>(data);
                size++;
            } else {
                add(data, root);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST.  Should traverse the tree to find the
     * appropriate location.
     *
     * @return the newly added node.
     * @param data the data to be added to the tree.
     * @param node the node currently being evaluated.
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        }
        return node;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        BSTNode<T> temp = new BSTNode<>(null);
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
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> temp) {
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
                BSTNode<T> placehold = new BSTNode<>(null);
                node.setLeft(removePredecessor(node.getLeft(), placehold));
                node.setData(placehold.getData());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), temp));
        } else {
            node.setRight(remove(data, node.getRight(), temp));
        }
        return node;
    }

    /**
     * Finds the predecessor of a node with two children.
     *
     * @param node the node currently being evaluated.
     * @param temp the placeholder node storing the data to be removed.
     * @return the data in the node replacing the one removed.
     */
    private BSTNode<T> removePredecessor(BSTNode<T> node, BSTNode<T> temp) {
        if (node.getRight() == null) {
            temp.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(removePredecessor(node.getRight(), temp));
            return node;
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        }
        return getHelper(data, root);
    }

    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @param data the data to search for in the tree.
     * @param node the node currently being evaluated.
     * @return the data in the tree equal to the parameter.
     */
    private T getHelper(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Tree does not contain "
                    + "specified data.");
        }
        if (data.compareTo(node.getData()) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return getHelper(data, node.getRight());
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data "
                    + "in tree.");
        }
        return containsHelper(data, root);
    }

    /**
     * Returns whether or not data equivalent to the given parameter
     * is contained within the tree.
     *
     * @param data the data to search for in the tree.
     * @param node the node currently being evaluated for its data.
     * @return whether or not the parameter is contained within the tree.
     */
    private boolean containsHelper(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        }
        int val = node.getData().compareTo(data);
        if (val > 0) {
            return containsHelper(data, node.getLeft());
        } else if (val < 0) {
            return containsHelper(data, node.getRight());
        } else {
            return true;
        }
    }

    @Override
    public List<T> preorder() {
        List<T> nodes = new ArrayList<>();
        preorderHelper(root, nodes);
        return nodes;
    }

    /**
     * Returns the preorder traversal of the tree.
     *
     * @param node the node currently being evaluated.
     * @param nodes the list of ordered nodes.
     */
    private void preorderHelper(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            nodes.add(node.getData());
            preorderHelper(node.getLeft(), nodes);
            preorderHelper(node.getRight(), nodes);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> nodes = new ArrayList<>();
        postorderHelper(root, nodes);
        return nodes;
    }

    /**
     * Returns the postorder traversal of the tree.
     *
     * @param node the node currently being evaluated.
     * @param nodes the list of ordered nodes.
     */
    private void postorderHelper(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            postorderHelper(node.getLeft(), nodes);
            postorderHelper(node.getRight(), nodes);
            nodes.add(node.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> nodes = new ArrayList<>();
        inorderHelper(root, nodes);
        return nodes;
    }

    /**
     * Returns the inorder traversal of the tree.
     *
     * @param node the node currently being evaluated.
     * @param nodes the list of ordered nodes.
     */
    private void inorderHelper(BSTNode<T> node, List<T> nodes) {
        if (node != null) {
            inorderHelper(node.getLeft(), nodes);
            nodes.add(node.getData());
            inorderHelper(node.getRight(), nodes);
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> dataset = new ArrayList<>();
        Queue<BSTNode<T>> nodes = new LinkedList<BSTNode<T>>();
        if (size != 0) {
            nodes.add(root);
            while (!nodes.isEmpty()) {
                BSTNode<T> node = nodes.remove();
                dataset.add(node.getData());
                if (node.getLeft() != null) {
                    nodes.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    nodes.add(node.getRight());
                }
            }
        }
        return dataset;
    }

    @Override
    public int distanceBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot find distance between "
                    + "null nodes.");
        }
        BSTNode<T> ancestor = nearestAncestor(data1, data2, root);
        return distanceBetweenHelper(ancestor, data1)
                + distanceBetweenHelper(ancestor, data2);
    }

    /**
     * Finds the nearest ancestor between two nodes on a tree.
     *
     * @param data1 one node's data.
     * @param data2 the other node's data.
     * @param node the node currently being evaluated.
     * @return the node that represents the nearest ancestor between two nodes.
     */
    private BSTNode<T> nearestAncestor(T data1, T data2, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("One or more pieces of data "
                    + "not found in tree.");
        }
        int val1 = node.getData().compareTo(data1);
        int val2 = node.getData().compareTo(data2);
        if (val1 < 0 && val2 < 0) {
            return nearestAncestor(data1, data2, node.getRight());
        } else if (val1 > 0 && val2 > 0) {
            return nearestAncestor(data1, data2, node.getLeft());
        } else {
            return node;
        }
    }

    /**
     * Calculates the shortest distance between two nodes in the tree.
     *
     * @param node the (ancestor) node to start the path from
     * @param data the data of the node to end the path on
     * @return the shortest distance between two nodes in the tree.
     */
    private int distanceBetweenHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("One or more pieces of data "
                    + "not found in tree.");
        }
        if (node.getData().compareTo(data) == 0) {
            return 0;
        } else if (node.getData().compareTo(data) < 0) {
            return distanceBetweenHelper(node.getRight(), data) + 1;
        } else {
            return  distanceBetweenHelper(node.getLeft(), data) + 1;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Calculate and return the height of the root of the tree. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * @param node the node currently being evaluated.
     * @return the height of the root of the tree, -1 if the tree is empty.
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        return Math.max(heightHelper(node.getLeft()),
                heightHelper(node.getRight())) + 1;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}