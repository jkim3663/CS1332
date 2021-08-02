import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayQueue.
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
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue with a backing array with capacity
     * INITIAL_CAPACITY.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the back of the queue.
     * Remember that the queue MUST behave circularly.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array. Do not forget to reset front to index 0 of
     * the new array.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        if (size == backingArray.length) {
            doubleCapacity();
        }
        int back = (front + size) % backingArray.length;
        backingArray[back] = data;
        size++;

    }

    /**
     * Removes and returns the data from the front of the queue.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any spots that you dequeue from with null.
     * <p>
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the array, so nothing can be removed.");
        }

        T removeData = backingArray[front];
        backingArray[front] = null;
        front++;
        if (front >= backingArray.length) {
            front = 0;
        }
        size--;

        return removeData;
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
            throw new NoSuchElementException("There is nothing in the array, so nothing can be returned.");
        }

        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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

    /**
     * Doubles the capacity of the backing array by making a new array with the data of backing array.
     */
    private void doubleCapacity() {
        int newCapacity = backingArray.length * 2;
        int count = 0;
        T[] newArray = (T[]) new Object[newCapacity];

        for (T t : backingArray) {
            if (t != null) {
                newArray[count] = t;
                count++;
            }
        }

        front = 0;
        backingArray = newArray;
    }
}
