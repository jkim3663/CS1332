

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: YouTube (Fall 2020 CS1332 recitation video)
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structrue.");
        }

        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException("Null data cannot be inserted into data structrue.");
            }
            add(datum);
        }
    }


    /**
     * Helper method for left rotation.
     *
     * @param node the current node
     * @return the root for rotation
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> newNode = node.getRight();
        node.setRight(newNode.getLeft());
        newNode.setLeft(node);

        updateHBF(node);
        updateHBF(newNode);

        return newNode;
    }

    /**
     * Helper method for right rotation.
     *
     * @param node the current node
     * @return the root for rotation
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> newNode = node.getLeft();
        node.setLeft(newNode.getRight());
        newNode.setRight(node);

        updateHBF(node);
        updateHBF(newNode);

        return newNode;
    }

    /**
     * Helper method for updating height and balance factor.
     *
     * @param node the current node
     */
    private void updateHBF(AVLNode<T> node) {
        int leftHeight = getNodeHeight(node.getLeft());
        int rightHeight = getNodeHeight(node.getRight());

        node.setBalanceFactor(leftHeight - rightHeight);
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    /**
     * Helper method for balancing nodes.
     *
     * @param node the current node
     * @return the new root after rotation
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1) {
            if (node.getBalanceFactor() > 0) {
                if (node.getLeft() != null && node.getLeft().getBalanceFactor() < 0) {
                    node.setLeft(leftRotation(node.getLeft()));

                    node = rightRotation(node);
                } else {
                    node = rightRotation(node);
                }
            } else if (node.getBalanceFactor() < 0) {
                if (node.getRight() != null && node.getRight().getBalanceFactor() > 0) {
                    node.setRight(rightRotation(node.getRight()));

                    node = leftRotation(node);
                } else {
                    node = leftRotation(node);
                }
            }
        }

        return node;
    }

    /**
     * Helper method to get the node height.
     *
     * @param node the node to get the height
     * @return the node's height
     */
    private int getNodeHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structrue.");
        }

        root = addHelper(data, root);
    }

    /**
     * Helper method for add using recursion.
     *
     * @param data the data to add
     * @param root the current node for search and add
     * @return the current node
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> root) {
        if (root == null) {
            size++;
            return new AVLNode<>(data);
        }

        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(addHelper(data, root.getLeft()));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(addHelper(data, root.getRight()));
        } else {
            return root;
        }

        updateHBF(root);
        root = balance(root);

        return root;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node. Do NOT use the provided public
     * predecessor method to remove a 2-child node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structure.");
        }
        AVLNode<T> tempNode = new AVLNode<>(null);
        root = removeHelper(data, root, tempNode);

        if (tempNode.getData() == null) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }

        return tempNode.getData();
    }

    /**
     * Helper method for remove method.
     *
     * @param data that data to remove
     * @param curr the current data that is used to compare
     * @param temp the temporary node
     * @return the node containing the data that should be removed
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr, AVLNode<T> temp) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }

        if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), temp));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), temp));
        } else {
            temp.setData(curr.getData());
            size--;

            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null || curr.getRight() == null) {
                if (curr.getLeft() == null) {
                    return curr.getRight();
                } else {
                    return curr.getLeft();
                }
            } else {
                AVLNode<T> dummy = new AVLNode<>(null);
                curr.setRight(findSuccessor(curr.getRight(), dummy));
                curr.setData(dummy.getData());
            }
        }

        updateHBF(curr);

        curr = balance(curr);
        return curr;
    }

    /**
     * Helper method to find the successor value.
     *
     * @param curr  the current node
     * @param dummy the dummy node that gets the data of current node
     * @return the successor node
     */
    private AVLNode<T> findSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(findSuccessor(curr.getLeft(), dummy));
            updateHBF(curr);
            curr = balance(curr);
            return curr;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structure.");
        }

        AVLNode<T> current = getHelper(data, root);

        return current.getData();
    }

    /**
     * Helper method for the get method.
     *
     * @param data the data to search inside the tree
     * @param node the current node
     * @return the node that contains the data
     */
    private AVLNode<T> getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }

        if (node.getData().compareTo(data) > 0) {
            return getHelper(data, node.getLeft());
        } else if (node.getData().compareTo(data) < 0) {
            return getHelper(data, node.getRight());
        } else {
            return node;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structure.");
        }

        return containsHelper(data, root);
    }

    /**
     * Helper method for the contains method.
     *
     * @param data the data to search for in the tree
     * @param node the current node
     * @return true or false
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        } else if (node.getData().compareTo(data) > 0) {
            return containsHelper(data, node.getLeft());
        } else if (node.getData().compareTo(data) < 0) {
            return containsHelper(data, node.getRight());
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root == null ? -1 : root.getHeight();
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     * <p>
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 3 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * 3: If the data passed in is the minimum data in the tree, return null.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into data structure.");
        }

        AVLNode<T> temp = findData(data, root);
        if (temp.getLeft() != null) {
            temp = findPredecessor(temp.getLeft());
        } else {
            temp = findDeepestAncestor(data, root, temp);
        }

        if (temp == null) {
            return null;
        }

        return temp.getData();
    }

    /**
     * Helper method for predecessor method.
     *
     * @param data the data to find its predecessor
     * @param curr the current node
     * @param dummy the dummy node that saves predecessor
     * @return the predecessor node
     */
    private AVLNode<T> findDeepestAncestor(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            return dummy;
        }

        if (data.compareTo(curr.getData()) > 0) {
            dummy = curr;
            return findDeepestAncestor(data, curr.getRight(), dummy);
        } else if (data.compareTo(curr.getData()) < 0) {
            return findDeepestAncestor(data, curr.getLeft(), dummy);
        } else {
            if (data.compareTo(dummy.getData()) == 0) {
                return null;
            }
            return dummy;
        }
    }

    /**
     * Helper method for the predecessor method.
     *
     * @param data the data to find its predecessor
     * @param curr the current node
     * @return the node that has data
     */
    private AVLNode<T> findData(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }

        if (data.compareTo(curr.getData()) > 0) {
            return findData(data, curr.getRight());
        } else if (data.compareTo(curr.getData()) < 0) {
            return findData(data, curr.getLeft());
        } else {
            return curr;
        }
    }

    /**
     * Helper method for predecessor method.
     *
     * @param curr the current node
     * @return the predecessor node
     */
    private AVLNode<T> findPredecessor(AVLNode<T> curr) {
        if (curr.getRight() != null) {
            return findPredecessor(curr.getRight());
        } else {
            return curr;
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 50
     * /    \
     * 25      75
     * /  \     / \
     * 13   37  70  80
     * /  \    \      \
     * 12  15    40    85
     * /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty, so there are no smallest elements.");
        }
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("The input is smaller than 0 or bigger than the number of data.");
        }
        List<T> list = new ArrayList<>();
        if (k == 0) {
            return list;
        } else {
            list = kHelper(root, list, k);

            return list;
        }
    }

    /**
     * Helper method for kSmallest method.
     *
     * @param curr the current node
     * @param list the list that data is added
     * @param k    number of add needed
     * @return the predecessor node
     */
    private List<T> kHelper(AVLNode<T> curr, List<T> list, int k) {
        if (curr == null) {
            return list;
        } else {
            kHelper(curr.getLeft(), list, k);
            if (list.size() < k) {
                list.add(curr.getData());
            } else {
                return list;
            }
            kHelper(curr.getRight(), list, k);
            return list;
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
