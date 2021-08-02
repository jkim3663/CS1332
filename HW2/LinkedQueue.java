import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedQueue. It should NOT be circular.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: Youtube
 */
public class LinkedQueue<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the back of the queue.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        if (head == null) {
            head = new LinkedNode<>(data);
            tail = head;
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(data);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the list, so nothing can be removed.");
        }

        LinkedNode<T> tempNode = head;
        head = tempNode.getNext();
        if (head == null) {
            tail = null;
        }
        size--;

        return tempNode.getData();
    }

    /**
     * Returns the data from the front of the queue without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the list, so nothing can be removed.");
        }

        return head.getData();
    }

    /**
     * Returns the head node of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
