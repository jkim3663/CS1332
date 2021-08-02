
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: Youtube, Textbook
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The elements should be added to the BST in the order in
     * which they appear in the Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for-loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        root = addHelper(data, root);
    }

    /**
     * Helper method to add the data to the tree.
     * <p>
     * Uses recursion for adding.
     *
     * @param data the data to add
     * @param node the current node for comparison
     * @return the node with the data that is going to be added
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        }

        return node;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }
        BSTNode<T> tempNode = new BSTNode<>(null);
        root = removeHelper(data, root, tempNode);

        return tempNode.getData();
    }

    /**
     * Helper method to remove the data inside the tree.
     * <p>
     *
     * @param data the data to remove
     * @param curr the current node that is used to compare
     * @param temp the temporary node that
     * @return the node containing the data that should be removed
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> temp) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) > 0) {
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
                BSTNode<T> dummy = new BSTNode<>(null);
                curr.setLeft(findPredecessor(curr.getLeft(), dummy));
                curr.setData(dummy.getData());
            }
        }
        return curr;
    }

    /**
     * Helper method to find the predecessor of the removed one when there are two children.
     * <p>
     * Uses recursion for searching.
     *
     * @param curr  the current node that is used to compare
     * @param dummy the dummy node that gets the data of the current node
     * @return the predecessor node
     */
    private BSTNode<T> findPredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(findPredecessor(curr.getRight(), dummy));
        }
        return null;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }

        BSTNode<T> current = getHelper(data, root);
        return current.getData();
    }

    /**
     * Helper method for returning the data inside the tree.
     * <p>
     * Uses recursion for searching and returning the node.
     *
     * @param data the data to search for
     * @param node the current node that is used for comparison
     * @return the data in the tree equal to the parameter
     */
    private BSTNode<T> getHelper(T data, BSTNode<T> node) {
        if (node.getData().compareTo(data) == 0) {
            return node;
        } else if (node.getData().compareTo(data) < 0) {
            return getHelper(data, node.getRight());
        } else {
            return getHelper(data, node.getLeft());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        return containsHelper(data, root);
    }

    /**
     * Helper method that returns the node that contains the value.
     *
     * @param data the data to search for
     * @param node the current node for data comparison
     * @return the node that contains the T data
     */
    private boolean containsHelper(T data, BSTNode<T> node) {
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
     * Generate a pre-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        return preorderHelper(root, list);
    }

    /**
     * Helper method to generate a pre-order traversal of the tree.
     * <p>
     *
     * @param node the node for recursion traversal
     * @param list the list that the node data is added
     * @return the inorder traversal of the tree
     */
    private List<T> preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }

        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        return inorderHelper(root, list);
    }

    /**
     * Helper method to generate an in-order traversal of the tree.
     * <p>
     *
     * @param node the node for recursion traversal
     * @param list the list that the node data is added
     * @return the inorder traversal of the tree
     */
    private List<T> inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }

        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        return postorderHelper(root, list);
    }

    /**
     * Helper method to generate a post-order traversal of the tree.
     * <p>
     *
     * @param node the node for recursion traversal
     * @param list the list that the node data is added
     * @return the inorder traversal of the tree
     */
    private List<T> postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }

        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use. You may import java.util.Queue as well as an implementing
     * class.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode<T> current = queue.poll();
            if (current != null) {
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
            list.add(current.getData());
        }

        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return heightSearchHelper(root);
        }
    }

    /**
     * Helper method to returns the height of the root of the tree.
     * <p>
     *
     * @param node the node that is used for recursion to calculate the height
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightSearchHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = heightSearchHelper(node.getLeft());
        int rightHeight = heightSearchHelper(node.getRight());

        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     * <p>
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     * <p>
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * <p>
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     * <p>
     * Hint: How can we use the order property of the BST to locate the deepest
     * common ancestor?
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("The data is not in the tree, so it cannot be removed.");
        }
        List<T> list = new LinkedList<>();

        if (data1.compareTo(data2) == 0) {
            list.add(data1);
        } else {
            BSTNode<T> temp = findCommonAncestor(data1, data2, root);
            list = addPathElementsFront(data1, temp, list);
            list = addPathElementsBack(data2, temp, list);
        }
        return list;
    }

    /**
     * Helper method to find the common ancestor node of the two data.
     * <p>
     * Uses recursion for searching the common ancestor node.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @param root the node that is used to find the common ancestor node
     * @return the lowest common ancestor node
     */
    private BSTNode<T> findCommonAncestor(T data1, T data2, BSTNode<T> root) {
        if (data1.compareTo(root.getData()) > 0 && data2.compareTo(root.getData()) > 0) {
            return findCommonAncestor(data1, data2, root.getRight());
        } else if (data1.compareTo(root.getData()) < 0 && data2.compareTo(root.getData()) < 0) {
            return findCommonAncestor(data1, data2, root.getLeft());
        } else {
            return root;
        }
    }

    /**
     * Helper method to add the data to the front of the list.
     * <p>
     * Uses recursion for adding the values.
     *
     * @param data1 the data to start the path from
     * @param curr the common ancestor node
     * @param list  the list that the values are added
     * @return the list that has the values added to the front
     */
    private List<T> addPathElementsFront(T data1, BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            if (data1.compareTo(curr.getData()) > 0) {
                addPathElementsFront(data1, curr.getRight(), list);
            } else if (data1.compareTo(curr.getData()) < 0) {
                addPathElementsFront(data1, curr.getLeft(), list);
            }
        }
        list.add(curr.getData());

        return list;
    }

    /**
     * Helper method to add the data to the back of the list.
     * <p>
     * Uses recursion for adding the values.
     *
     * @param data2 the data to end the path on
     * @param curr the common ancestor node
     * @param list the list that the values are added
     * @return the list that has the values added to the front
     */
    private List<T> addPathElementsBack(T data2, BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            if (data2.compareTo(curr.getData()) > 0) {
                list.add(curr.getRight().getData());
                addPathElementsBack(data2, curr.getRight(), list);
            } else if (data2.compareTo(curr.getData()) < 0) {
                list.add(curr.getLeft().getData());
                addPathElementsBack(data2, curr.getLeft(), list);
            }
        }
        return list;
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
