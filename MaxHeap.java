import java.util.ArrayList;
/**
 * Your implementation of a max heap.
 *
 * @author Tyler Jones
 * @userid tjones329
 * @GTID 903283773
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }


    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the passed in ArrayList before you start the Build Heap Algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        T[] tmparray = (T[]) new Comparable[2 * data.size() + 1];
        for (T o: data) {
            if (o == null) {
                throw new IllegalArgumentException(
                        "there was a null element in data");
            }
            tmparray[++size] = o;
        }
        backingArray = tmparray;
        for (int i = size / 2; i >= 1; i--) {
            downHeap(i);
        }
    }

    /**
     *
     * @param index the current index we are checking
     * @return the index of the left child of the current index
     */
    public int leftChild(int index) {
        return (index * 2);
    }

    /**
     *
     * @param index the current index we are checking
     * @return the index of the right child of the current index
     */
    public int rightChild(int index) {
        return (index * 2) + 1;
    }

    /**
     * returns the parent of the current index you are checking
     * @param index the index of the child we are finding the child of
     * @return int representing index of the parent
     */
    public int parent(int index) {
        return index / 2;
    }
    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("the item is null");
        } else if (size + 1 == backingArray.length) {
            T[] newarr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                newarr[i] = backingArray[i];
            }
            backingArray = newarr;
        }
        backingArray[++size] = item;
        int tmp = size;
        if (backingArray[parent(tmp)] != null & backingArray[tmp] != null) {
            while (backingArray[tmp].compareTo(backingArray[parent(tmp)]) > 0) {
                swap(tmp, parent(tmp));
                tmp = parent(tmp);
                if (backingArray[parent(tmp)] == null) {
                    break;
                }
            }
        }
    }

    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The heap is emptayyy");
        }
        T removeddata = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        downHeap(1);
        size--;
        return removeddata;
    }

    /**
     * a helper method to keep the order property of the heap
     * @param index the current index we are checking
     */
    public void downHeap(int index) {
        if (index > size - 1 || index < 1) {
            return;
        }
        if (backingArray[leftChild(index)] == null
                & backingArray[rightChild(index)] == null) {
            return;
        }
        if (backingArray[leftChild(index)] == null
                & backingArray[rightChild(index)] != null) {
            if (backingArray[index]
                    .compareTo(backingArray[rightChild(index)]) < 0) {
                swap(index, rightChild(index));
                downHeap(rightChild(index));
            }
        } else if (backingArray[rightChild(index)] == null
                & backingArray[leftChild(index)] != null) {
            if (backingArray[index]
                    .compareTo(backingArray[leftChild(index)]) < 0) {
                swap(index, leftChild(index));
                downHeap(leftChild(index));
            }
        } else if (backingArray[index]
                .compareTo(backingArray[leftChild(index)]) < 0
                || backingArray[index]
                .compareTo(backingArray[rightChild(index)]) < 0) {

            if (backingArray[rightChild(index)]
                    .compareTo(backingArray[leftChild(index)]) < 0) {

                swap(index, leftChild(index));
                downHeap(leftChild(index));

            } else {
                swap(index, rightChild(index));
                downHeap(rightChild(index));
            }
        }
    }

    /**
     * a helper method to swap two elements in the array
     * @param curr the current index we are swapping
     * @param swapme the index of what we are swapping with
     */
    public void swap(int curr, int swapme) {
        T tmp = backingArray[curr];
        backingArray[curr] = backingArray[swapme];
        backingArray[swapme] = tmp;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            return null;
        }
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}