import sun.awt.image.ImageWatched;

/**
 * Your implementation of a circular singly linked list.
 *
 * @author Tyler Jones
 * @userid Tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 | index > size) {
            throw new java.lang.IndexOutOfBoundsException("index is not valid");
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("data can not be null");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> n = new LinkedListNode<T>(data);
            LinkedListNode<T> curr = head;
            int count = 0;
            while (count < index - 1) {
                curr = curr.getNext();
                count++;
            }
            n.setNext(curr.getNext());
            curr.setNext(n);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("data can not be null");
        } else if (size == 0) {
            LinkedListNode<T> n = new LinkedListNode<T>(data);
            head = n;
            n.setNext(head);
            size++;
        } else {
            LinkedListNode<T> n = new LinkedListNode<T>(data);
            n.setNext(head.getNext());
            head.setNext(n);
            n.setData(head.getData());
            head.setData(data);
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("data can not be null");
        } else if (size == 0) {
            head = new LinkedListNode<T>(data);
            head.setNext(head);
            size++;
        } else {
            LinkedListNode<T> n = new LinkedListNode<T>(head.getData());
            n.setNext(head.getNext());
            head.setNext(n);
            head.setData(data);
            head = head.getNext();
            size++;
        }
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 | index >= size) {
            throw new java.lang.IndexOutOfBoundsException("not a valid index");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            LinkedListNode<T> curr = head;
            int count = 0;
            while (count < index - 1) {
                curr = curr.getNext();
                count++;
            }
            T removedData = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            if (size == 0) {
                head = null;
            }
            return removedData;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        T headData = head.getData();
        head.setData(head.getNext().getData());
        head.setNext(head.getNext().getNext());
        size--;
        if (size == 0) {
            head = null;
        }
        return headData;
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        LinkedListNode<T> curr = head;
        int count = 0;
        while (count < size - 2) {
            curr = curr.getNext();
            count++;
        }
        T backData = curr.getNext().getData();
        curr.setNext(head);
        size--;
        if (size == 0) {
            head = null;
        }
        return backData;
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("data can not be null");
        }
        LinkedListNode<T> curr = head;
        LinkedListNode<T> latest = null;
        for (int i = 0; i < size; i++) {
            if (curr.getNext().getData().equals(data)) {
                latest = curr;
            }
            curr = curr.getNext();
        }
        if (latest == null & head.getData().equals(data)) {
            return removeFromFront();
        } else if (latest == null) {
            return null;
        }
        T removed = latest.getNext().getData();
        latest.setNext(latest.getNext().getNext());
        if (size == 0) {
            return null;
        }
        size--;
        return removed;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 | index >= size) {
            throw new java.lang.IndexOutOfBoundsException("not a valid index");
        }
        LinkedListNode<T> curr = head;
        int count = 0;
        while (count < index) {
            curr = curr.getNext();
            count++;
        }
        return curr.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        LinkedListNode<T> curr = head;
        Object[] linkedArray = new Object[size];
        int count = 0;
        while (count < size) {
            linkedArray[count] = curr.getData();
            curr = curr.getNext();
            count++;
        }
        return linkedArray;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}