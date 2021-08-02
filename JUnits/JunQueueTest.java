import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;


public class JunArrayQueueTest {
    private static final int TIMEOUT = 200;
    private ArrayQueue<String> array;

    @Before
    public void setup() {
        array = new ArrayQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayQueue.INITIAL_CAPACITY],
                array.getBackingArray());
    }

    @Test (timeout = TIMEOUT)
    public void enqueue() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");

        assertEquals(9, array.size());

        array.enqueue("9a");
        assertEquals(10, array.size());
        assertEquals("0a", array.dequeue());

    }

    @Test(expected = IllegalArgumentException.class)
    public void enqueueExceptionCheck() {
        array.enqueue(null);
    }

    @Test (timeout = TIMEOUT)
    public void dequeue() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");

        assertEquals(9, array.size());

        assertEquals("0a", array.dequeue());

        assertEquals(8, array.size());

        array.dequeue();
        array.dequeue();
        array.dequeue();

        array.enqueue("new one");
        array.enqueue("new two");

        assertEquals("4a", array.peek());

        assertEquals("4a", array.dequeue());
        assertEquals("5a", array.dequeue());
        assertEquals("6a", array.dequeue());
        assertEquals("7a", array.dequeue());
        assertEquals("8a", array.dequeue());
        assertEquals("new one", array.dequeue());
        assertEquals("new two", array.dequeue());


    }

    @Test(timeout = TIMEOUT)
    public void dequeueDoubleCapacityTest() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");

        assertEquals(9, array.size());

        array.enqueue("9a");
        assertEquals(10, array.size());

        assertEquals("0a", array.dequeue());
        assertEquals("1a", array.dequeue());
        assertEquals("2a", array.dequeue());
        assertEquals("3a", array.dequeue());

        assertEquals("4a", array.peek());

        array.enqueue("new one");
        array.enqueue("new two");

        for (int i = 0; i < 6; i++) {
            array.dequeue();
        }

        assertEquals("new one", array.peek());
        assertEquals("new one", array.dequeue());
        assertEquals("new two", array.dequeue());

        assertEquals(0, array.size());

    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueExceptionCheck() {
        array.dequeue();
    }

    @Test (timeout = TIMEOUT)
    public void peek() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, array.size());

        assertSame(temp, array.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void peekExceptionCheck() {
        array.peek();
    }
}
 Jun-Yeop-Kim-HW2-ArrayStackTest
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class JunArrayStackTest {
    private static final int TIMEOUT = 200;
    private ArrayStack<String> array;

    @Before
    public void setup() {
        array = new ArrayStack<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayStack.INITIAL_CAPACITY],
                array.getBackingArray());
    }

    @Test (timeout = TIMEOUT)
    public void push() {
        array.push("0a");   // 0a
        array.push("1a");   // 0a, 1a
        array.push("2a");   // 0a, 1a, 2a
        array.push("3a");   // 0a, 1a, 2a, 3a
        array.push("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals("4a", array.peek());
        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());

        array.push("5a");
        array.push("6a");
        array.push("7a");
        array.push("8a");
        array.push("9a");

        assertEquals(10, array.size());

        assertEquals("9a", array.peek());
    }

    @Test (timeout = TIMEOUT)
    public void pop() {
        array.push("0a");   // 0a
        array.push("1a");   // 0a, 1a
        array.push("2a");   // 0a, 1a, 2a
        array.push("3a");   // 0a, 1a, 2a, 3a
        array.push("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());

        array.push("5a");
        array.push("6a");
        array.push("7a");
        array.push("8a");
        array.push("9a");

        assertEquals(10, array.size());

        assertEquals("9a", array.pop());
        assertEquals(9, array.size());

        assertEquals("8a", array.pop());
        assertEquals(8, array.size());

        assertEquals("7a", array.pop());
        assertEquals(7, array.size());

        assertEquals("6a", array.pop());
        assertEquals(6, array.size());

        array.push("new one");
        array.push("new two");

        assertEquals("new two", array.peek());
        assertEquals(8, array.size());
    }

    @Test (timeout = TIMEOUT)
    public void peek() {
        array.push("0a");   // 0a
        array.push("1a");   // 0a, 1a
        array.push("2a");   // 0a, 1a, 2a
        array.push("3a");   // 0a, 1a, 2a, 3a
        array.push("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals("4a", array.peek());
    }
}
