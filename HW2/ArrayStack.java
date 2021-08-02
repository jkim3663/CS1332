import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
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
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayStack.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack with a backing array with capacity
     * INITIAL_CAPACITY.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the top of the stack.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }

        if (size == backingArray.length) {
            doubleCapacity();
        }
        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any spots that you pop from with null.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the array, so nothing can be removed.");
        }

        T removeData = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;

        return removeData;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the array, so nothing can be returned.");
        }

        return backingArray[size - 1];
    }

    /**
     * Returns the backing array of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
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
        T[] newArray = (T[]) new Object[newCapacity];

        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }

        backingArray = newArray;
    }
}
