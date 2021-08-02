import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a ExternalChainingHashMap.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: YouTube
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int initialCapacity) {
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     * <p>
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value is null.");
        }
        double newLF = (size + 1) / (double) table.length;

        if (newLF > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        int index = key.hashCode() % table.length;
        index = index >= 0 ? index : index * -1;

        if (table[index] == null) {
            table[index] = new ExternalChainingMapEntry<>(key, value);
            size++;

            return null;
        } else if (key.equals(table[index].getKey())) {
            V temp = table[index].getValue();
            table[index].setValue(value);

            ExternalChainingMapEntry<K, V> check = table[index];

            while (check.getNext() != null) {
                if (key.equals(check.getNext().getKey())) {
                    temp = check.getNext().getValue();
                    check.getNext().setValue(value);
                    return temp;
                }
                check = check.getNext();
            }

            return temp;
        } else {
            ExternalChainingMapEntry<K, V> check = table[index];
            while (check.getNext() != null) {
                if (key.equals(check.getNext().getKey())) {
                    V temp = check.getNext().getValue();
                    check.getNext().setValue(value);
                    return temp;
                }
                check = check.getNext();
            }

            ExternalChainingMapEntry<K, V> temp = table[index];
            table[index] = new ExternalChainingMapEntry<>(key, value);
            table[index].setNext(temp);
            size++;

            return null;
        }
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        V ans;
        int index = key.hashCode() % table.length;
        index = index >= 0 ? index : index * -1;

        if (index >= table.length || table[index] == null) {
            throw new NoSuchElementException("The key is not in the map.");
        } else if (key.equals(table[index].getKey())) {
            ans = table[index].getValue();

            if (table[index].getNext() != null) {
                table[index] = table[index].getNext();
            } else {
                table[index] = null;
            }
            size--;

            return ans;
        } else {
            ExternalChainingMapEntry<K, V> head = table[index];

            while (head.getNext() != null) {
                if (key.equals(head.getNext().getKey())) {
                    ExternalChainingMapEntry<K, V> temp = head.getNext();
                    head.setNext(temp.getNext());
                    size--;

                    return temp.getValue();
                }
                head = head.getNext();
            }
        }

        throw new NoSuchElementException("The key is not inside the map.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot insert null key to data structure.");
        }
        int index = key.hashCode() % table.length;
        index = index >= 0 ? index : index * -1;

        if (index >= table.length || table[index] == null) {
            throw new NoSuchElementException("The key does not exist inside the map.");
        } else if (table[index].getKey().equals(key)) {
            return table[index].getValue();
        } else {
            ExternalChainingMapEntry<K, V> head = table[index];

            while (head.getNext() != null) {
                if (head.getNext().getKey().equals(key)) {
                    return head.getNext().getValue();
                }

                head = head.getNext();
            }

            throw new NoSuchElementException("The key does not exist inside the map.");

        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot insert null key to data structure.");
        }

        int index = key.hashCode() % table.length;
        index = index >= 0 ? index : index * -1;

        if (index >= table.length || table[index] == null) {
            return false;
        } else if (table[index].getKey().equals(key)) {
            return true;
        } else {
            boolean bool = false;
            ExternalChainingMapEntry<K, V> head = table[index];
            while (head.getNext() != null) {
                if (head.getNext().getKey().equals(key)) {
                    bool = true;
                    break;
                }
                head = head.getNext();
            }
            return bool;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        if (size == 0) {
            return keys;
        }

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                keys.add(table[i].getKey());

                if (table[i].getNext() != null) {
                    ExternalChainingMapEntry<K, V> head = table[i];
                    while (head.getNext() != null) {
                        head = head.getNext();
                        keys.add(head.getKey());
                    }
                }
            }
        }

        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> list = new ArrayList<>();

        if (size == 0) {
            return list;
        }

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                list.add(table[i].getValue());

                if (table[i].getNext() != null) {
                    ExternalChainingMapEntry<K, V> head = table[i];

                    while (head.getNext() != null) {
                        head = head.getNext();
                        list.add(head.getValue());
                    }
                }
            }
        }
        return list;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length should not be smaller than the size.");
        }

        ExternalChainingMapEntry<K, V>[] newTable = (ExternalChainingMapEntry<K, V>[]) new
                ExternalChainingMapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                int index = table[i].getKey().hashCode() % length;
                index = index >= 0 ? index : index * -1;

                if (table[i].getNext() == null) {
                    if (newTable[index] == null) {
                        newTable[index] = table[i];
                    } else {
                        ExternalChainingMapEntry<K, V> temp = newTable[index];
                        newTable[index] = table[i];
                        newTable[index].setNext(temp);
                    }
                } else {
                    ExternalChainingMapEntry<K, V> head = table[i];
                    while (head != null) {
                        int newIdx = head.getKey().hashCode() % length;
                        if (newTable[newIdx] == null) {
                            newTable[newIdx] = new ExternalChainingMapEntry<>(head.getKey(), head.getValue());
                        } else {
                            ExternalChainingMapEntry<K, V> temp = newTable[newIdx];
                            newTable[newIdx] = new ExternalChainingMapEntry<>(head.getKey(), head.getValue());
                            newTable[newIdx].setNext(temp);
                        }

                        head = head.getNext();
                    }
                }
            }
        }

        table = newTable;
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        size = 0;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
