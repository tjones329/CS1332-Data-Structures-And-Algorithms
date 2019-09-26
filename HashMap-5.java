import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Your implementation of HashMap.
 * 
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 11;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of the initialCapacity parameter.
     *
     * You may assume the initialCapacity parameter will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        this.table = new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.

     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. As a
     * warning, be careful about using integer division in the LF calculation!
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException(
                    "the value for key can not be null");
        } else if (((double) (size + 1) / (double) table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable((table.length * 2) + 1);
        }

        MapEntry<K, V> head = table[getIndex(key)];

        // checks to see if the value is already in the chain
        while (head != null) {
            if (head.getKey().equals(key)) {
                V removed = head.getValue();
                head.setValue(value);
                return removed;
            }
            head = head.getNext();
        }

        // If the key is not in the chain, insert at front of chain
        size++;
        MapEntry<K, V> newNode = new MapEntry<>(key, value);
        newNode.setNext(head);
        table[getIndex(key)] = newNode;
        return null;
    }

    /**
     *
     * @param key the key we are trying to get the
     *            index of based on its hashCode
     * @return the index of where the key will be in the array
     */
    private int getIndex(K key) {
        int hash = key.hashCode();
        int index = java.lang.Math.abs(hash % table.length);
        return index;

    }

    /**
     * Resizes the backing table to the specified length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the new table
     * in the order in which they are traversed.
     *
     * Remember, you cannot just simply copy the entries over to the new array.
     * You will have to rehash all of the entries and add them to the new index
     * of the new table. Feel free to create new MapEntry objects to use when
     * adding to the new table to avoid pointer dependency issues between the
     * new and old tables.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates. This matters especially for external chaining since it can
     * cause the performance of resizing to go from linear to quadratic time.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    public void resizeBackingTable(int length) { //I'm so sorry this is so bad
        // I have no clue what is wrong
        if (length <= 1) {
            throw new IllegalArgumentException("the backing table "
                    + "can not be resized size of 1 or below");
        } else if (length <= size - 1) {
            throw new IllegalArgumentException("the backing table "
                    + "can not be resized to below size");
        } else {
            MapEntry<K, V>[] newMapTable = new MapEntry[length];
            MapEntry<K, V> tmp = null;
            for (MapEntry<K, V> entry:table) {
                if (entry != null) {
                    while (entry != null) {
                        int index = Math.abs(entry.getKey().hashCode()
                                % length);
                        newMapTable[index] = new MapEntry<>(entry.getKey(), entry.getValue(), newMapTable[index]);
                        entry = entry.getNext();
                    }
                }
            }
            table = newMapTable;
        }
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key can not be null");
        }
        MapEntry<K, V> indexHead = table[getIndex(key)];
        MapEntry<K, V> indexHeadPrev = null;
        V removedValue = null;
        if (indexHead == null) {
            throw new java.util.NoSuchElementException("the key was not found");
        }

        while (indexHead != null) {
            if (indexHead.getKey().equals(key)) {
                removedValue = indexHead.getValue();
                if (indexHeadPrev != null) {
                    indexHeadPrev.setNext(indexHead.getNext());
                } else {
                    table[getIndex(key)] = indexHead.getNext();
                }
                size--;
                return removedValue;
            }
            indexHeadPrev = indexHead;
            indexHead = indexHead.getNext();

        }
        throw new java.util.NoSuchElementException("the "
                + "key was not found in the HashTable");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key "
                    + "you are getting can not be null");
        }
        if (getIndex(key) > table.length) {
            throw new java.util.NoSuchElementException("index "
                    + "was greater than the table size");
        }
        MapEntry<K, V> keyIndex = table[getIndex(key)];
        if (keyIndex == null) {
            throw new java.util.NoSuchElementException("the key "
                    + "was null at that index");
        } else {
            while (keyIndex != null) {
                if (keyIndex.getKey().equals(key)) {
                    return keyIndex.getValue();
                }
                keyIndex = keyIndex.getNext();
            }
        }
        throw new java.util.NoSuchElementException("The key was not found!");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key you are "
                    + "searching for can not be null");
        }
        if (table[getIndex(key)] == null) {
            return false;
        }
        MapEntry<K, V> index = table[getIndex(key)];
        boolean found = false;
        if (index.getNext() == null) {
            if (table[getIndex(key)].getKey().equals(key)) {
                return true;
            }
        } else {
            while (index != null) {

                if (index.getKey().equals(key)) {
                    found = true;
                }
                index = index.getNext();
            }
        }
        return found;
    }

    /**
     * Returns a Set view of the keys contained in this map. The Set view is
     * used instead of a List view because keys are unique in a HashMap, which
     * is a property that elements of Sets also share.
     * 
     * Use java.util.HashSet.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> setofkeys = new HashSet<K>();
        for (MapEntry<K, V> entry: table) {
            MapEntry<K, V> tmp = entry;
            if (entry != null) {
                if (entry.getNext() == null) {
                    setofkeys.add(entry.getKey());
                } else {
                    while (tmp != null) {
                        setofkeys.add(tmp.getKey());
                        tmp = tmp.getNext();
                    }
                }
            }
        }
        return setofkeys;
    }

    /**
     * Returns a List view of the values contained in this map.
     * 
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the List in
     * the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> mapvalues = new ArrayList<V>();
        for (MapEntry<K, V> entry: table) {
            MapEntry<K, V> currententry = entry;
            while (currententry != null) {
                mapvalues.add(currententry.getValue());
                currententry = currententry.getNext();
            }
        }
        return mapvalues;
    }

    /**
     * Clears the table and resets it to a new table of length INITIAL_CAPACITY.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
    
    /**
     * Returns the backing table of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing table of the HashMap
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}