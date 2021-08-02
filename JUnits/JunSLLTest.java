import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class SllTest {
    private static final int TIMEOUT = 200;
    private SinglyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new SinglyLinkedList<>();
    }

    @Test (timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test (timeout = TIMEOUT)
    public void addAtIndex() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(0, "1b");
        list.addAtIndex(2, "2c");
        list.addAtIndex(3, "3d");
        list.addAtIndex(4, "4e");

        assertEquals(5, list.size());

        SinglyLinkedListNode<String> newNode = list.getHead();
        assertNotNull(newNode);
        assertEquals("1b", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("0a", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("2c", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("3d", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("4e", newNode.getData());

        newNode = newNode.getNext();
        assertNull(newNode);

        list.clear();

        list.addAtIndex(0, "1a");
        list.addAtIndex(0, "2a");
        list.addAtIndex(2, "3a");
        list.addAtIndex(1, "4x");
        assertEquals("4x", list.get(1));


    }

    @Test (timeout = TIMEOUT)
    public void addToFront() {
        list.addToFront("0a");
        list.addToFront("1b");
        list.addToFront("2c");
        list.addToFront("3d");
        list.addToFront("4e");

        assertEquals(5, list.size());

        SinglyLinkedListNode<String> newNode = list.getHead();
        assertNotNull(newNode);
        assertEquals("4e", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("3d", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("2c", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("1b", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("0a", newNode.getData());

        newNode = newNode.getNext();
        assertNull(newNode);



    }

    @Test (timeout = TIMEOUT)
    public void addToBack() {
        list.addToBack("0a");
        list.addToBack("1b");
        list.addToBack("2c");
        list.addToBack("3d");
        list.addToBack("4e");

        assertEquals(5, list.size());

        SinglyLinkedListNode<String> newNode = list.getHead();
        assertNotNull(newNode);
        assertEquals("0a", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("1b", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("2c", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("3d", newNode.getData());

        newNode = newNode.getNext();
        assertNotNull(newNode);
        assertEquals("4e", newNode.getData());

        newNode = newNode.getNext();
        assertNull(newNode);
    }

    @Test (timeout = TIMEOUT)
    public void removeAtIndex() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        assertEquals(5, list.size());

        list.removeAtIndex(0);
        assertEquals(4, list.size());

        SinglyLinkedListNode<String> newNode = list.getHead();
        assertEquals("1a", newNode.getData());

        assertSame("3a", list.removeAtIndex(2));

        assertEquals(3, list.size());
        assertEquals("2a", list.removeAtIndex(1));
        assertEquals(2, list.size());

        newNode = newNode.getNext();
        assertEquals("4a", newNode.getData());
    }

    @Test (timeout = TIMEOUT)
    public void removeFromFront() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.removeFromFront();
        SinglyLinkedListNode<String> newNode = list.getHead();
        assertEquals("1a", newNode.getData());
        list.removeFromFront();
        newNode = list.getHead();
        assertEquals("2a", newNode.getData());
        list.removeAtIndex(1);
        list.removeAtIndex(0);
        newNode = list.getHead();
        assertEquals("4a", newNode.getData());
        newNode = list.getTail();
        assertEquals("4a", newNode.getData());

        list.clear();
        list.addToFront("1a");
        list.removeFromFront();
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test (timeout = TIMEOUT)
    public void removeFromBack() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.removeFromBack();
        SinglyLinkedListNode<String> newNode = list.getTail();
        assertEquals("3a", newNode.getData());

        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();

        assertEquals(1, list.size());

        newNode = list.getHead();
        assertEquals("0a", newNode.getData());
        assertNull(newNode.getNext());
        SinglyLinkedListNode<String> newNode2 = list.getTail();

        assertEquals(newNode, newNode2);

        assertNull(newNode2.getNext());

    }

    @Test (timeout = TIMEOUT)
    public void get() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        SinglyLinkedListNode<String> cur = list.getHead();
        assertEquals(cur.getData(), list.get(0));

        cur = cur.getNext();
        assertEquals(cur.getData(), list.get(1));

        cur = cur.getNext();
        assertEquals(cur.getData(), list.get(2));

        cur = cur.getNext();
        assertEquals(cur.getData(), list.get(3));

        cur = cur.getNext();
        assertEquals(cur.getData(), list.get(4));

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test (timeout = TIMEOUT)
    public void isEmpty() {
        list.addToFront("asdf");

        assertFalse(list.isEmpty());

        list.removeFromBack();

        assertTrue(list.isEmpty());
    }

    @Test (timeout = TIMEOUT)
    public void clear() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.clear();

        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test (timeout = TIMEOUT)
    public void removeLastOccurrence() {
        list.addAtIndex(0, "4a");
        list.addAtIndex(1, "4a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        assertEquals(list.get(4), list.removeLastOccurrence("4a"));
        assertEquals(4, list.size());

        assertEquals(list.get(1), list.removeLastOccurrence("4a"));
        assertEquals(3, list.size());

        assertEquals(list.get(1), list.removeLastOccurrence("2a"));
    }

    @Test (expected = NoSuchElementException.class)
    public void checkNoSuchElementException() {
        list.addAtIndex(0, "4a");
        list.addAtIndex(1, "4a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.removeLastOccurrence("14a");
    }

    @Test (expected = NoSuchElementException.class)
    public void checkNoSuchElementException2() {
        list.removeFromFront();
    }

    @Test (expected = NoSuchElementException.class)
    public void checkNoSuchElementException3() {
        list.removeFromBack();
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() {
        list.addAtIndex(1, "2d");
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException2() {
        list.addAtIndex(0, "4a");
        list.addAtIndex(1, "4a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.removeAtIndex(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException3() {
        list.addAtIndex(0, "4a");
        list.addAtIndex(1, "4a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.get(5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        list.addAtIndex(0, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgumentException2() {
        list.addToFront(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgumentException3() {
        list.addToBack(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgumentException4() {
        list.addAtIndex(0, "4a");
        list.addAtIndex(1, "4a");
        list.addAtIndex(2, "2a");
        list.addAtIndex(3, "3a");
        list.addAtIndex(4, "4a");

        list.removeLastOccurrence(null);
    }


    @Test (timeout = TIMEOUT)
    public void toArray() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        String[] expected = new String[5];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.toArray());
    }
}
