
import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is smaller than 0 or bigger than data structure size.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (index == 0) {
            addToFront(data);
        } else if (index >= size) {
            addToBack(data);
        } else {
            SinglyLinkedListNode<T> currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            SinglyLinkedListNode<T> temp = currentNode.getNext();
            currentNode.setNext(newNode);
            currentNode.getNext().setNext(temp);
            size++;
        }

    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        newNode.setNext(head);
        head = newNode;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is smaller than 0 or bigger than data structure size.");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            SinglyLinkedListNode<T> currentNode = head;

            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            SinglyLinkedListNode<T> tempNode = currentNode.getNext();
            currentNode.setNext(tempNode.getNext());
            size--;

            return tempNode.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the list, so nothing can be removed.");
        }

        SinglyLinkedListNode<T> tempNode = head;
        head = tempNode.getNext();
        if (head == null) {
            tail = null;
        }
        size--;

        return tempNode.getData();
    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is nothing in the list, so nothing can be removed.");
        } else if (head.getNext() == null) {
            SinglyLinkedListNode<T> tempNode = head;
            head = null;
            tail = null;
            size--;

            return tempNode.getData();
        } else {
            SinglyLinkedListNode<T> currentNode = head;

            while (currentNode.getNext().getNext() != null) {
                currentNode = currentNode.getNext();
            }
            SinglyLinkedListNode<T> tempNode = tail;
            tail = currentNode;
            tail.setNext(null);
            size--;

            return tempNode.getData();
        }
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is smaller than 0 or bigger than data structure size.");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            SinglyLinkedListNode<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNext();
            }
            return currentNode.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    @SuppressWarnings("checkstyle:CommentsIndentation")
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        int index = 0;
        int countData = 0;
        SinglyLinkedListNode<T> currentNode = head;

        for (int i = 0; i < size; i++) {
            if (currentNode.getData().equals(data)) {
                index = i;
                countData++;
            }
            currentNode = currentNode.getNext();
        }

        if (countData == 0) {
            throw new NoSuchElementException("The data was not found from the data structure.");
        }

        return removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        Object[] listInArray = new Object[size];
        SinglyLinkedListNode<T> newNode = head;
        for (int i = 0; i < size; i++) {
            listInArray[i] = newNode.getData();
            newNode = newNode.getNext();
        }

        return (T[]) listInArray;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
