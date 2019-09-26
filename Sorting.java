import java.util.*;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if(comparator == null) {
            throw new IllegalArgumentException("the comparator was null");
        } else if(arr == null) {
            throw new IllegalArgumentException("the array was null");
        }
        for(int i = 1; i < arr.length; i++) {
            for(int j = i; j > 0; j--) {
                if(comparator.compare(arr[j], arr[j-1]) < 0) {
                    T swap = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = swap;
                } else {
                    j = -1;
                }
            }

        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if(comparator == null) {
            throw new IllegalArgumentException("the comparator was null");
        } else if(arr == null) {
            throw new IllegalArgumentException("the array was null");
        }
        for(int i = 0; i < arr.length; i++) {
            int min = i;
            for(int j = i + 1; j < arr.length; j++) {
                if(comparator.compare(arr[min], arr[j]) > 0) {
                    min = j;
                }
            }
            T swap = arr[min];
            arr[min] = arr[i];
            arr[i] = swap;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if(comparator == null) {
            throw new IllegalArgumentException("the comparator was null");
        } else if(arr == null) {
            throw new IllegalArgumentException("the array was null");
        } else if (arr.length <= 1) {
            return;
        }
            // split the array in two
            T[] leftarr = (T[]) new Object[arr.length/2];
            T[] rightarr = (T[]) new Object[arr.length - arr.length/2];
            for (int i = 0; i < arr.length/2; i++) {
                leftarr[i] = arr[i];
            }
            for (int i = arr.length/2; i < arr.length; i++) {
                rightarr[i - arr.length/2] = arr[i];
            }

            // recursively split the array until it cant be split anymore
            mergeSort(leftarr, comparator);
            mergeSort(rightarr, comparator);

            //sort the split arrays
            int leftindex = 0;
            int rightindex = 0;
            int indx = 0;
            while (leftindex < arr.length/2
                    && rightindex < arr.length - arr.length/2) {
                if (comparator.compare(leftarr[leftindex],
                        rightarr[rightindex]) <= 0) {
                    arr[indx] = leftarr[leftindex];
                    leftindex++;
                } else {
                    arr[indx] = rightarr[rightindex];
                    rightindex++;
                }
                indx++;
            }

            while (leftindex < arr.length/2) {
                arr[indx] = leftarr[leftindex];
                leftindex++;
                indx++;
            }
            while (rightindex < arr.length - arr.length/2) {
                arr[indx] = rightarr[rightindex];
                rightindex++;
                indx++;
            }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if(comparator == null) {
            throw new IllegalArgumentException("the comparator can't be null");
        } else if(arr == null) {
            throw new IllegalArgumentException("the array can't be null");
        } else if(rand == null) {
            throw new IllegalArgumentException("rand can't be null");
        } else if(arr.length <= 1) {
            return;
        }
        recursiveQuickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    private static <T> void recursiveQuickSort(T[] arr, int left, int right, Comparator<T> comparator, Random rand) {
        if (left >= right) {
            return;
        }
        int lefttmp = left + 1;
        int righttmp = right;
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivottmp = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = pivottmp;
        while (lefttmp <= righttmp) {
            while (lefttmp <= righttmp
                    && comparator.compare(arr[lefttmp], pivottmp) <= 0) {
                lefttmp++;
            }
            while (lefttmp <= righttmp
                    && comparator.compare(arr[righttmp], pivottmp) >= 0) {
                righttmp--;
            }
            if (lefttmp <= righttmp) {
                T temp = arr[lefttmp];
                arr[lefttmp] = arr[righttmp];
                arr[righttmp] = temp;
                lefttmp++;
                righttmp--;
            }
        }
        T temp = arr[righttmp];
        arr[righttmp] = arr[left];
        arr[left] = temp;

        //recurse left and then recurse right.
        recursiveQuickSort(arr, left, righttmp - 1, comparator, rand);
        recursiveQuickSort(arr, righttmp + 1, right, comparator, rand);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {;
            throw new IllegalArgumentException("array cannot be null");
        }
        LinkedList<Integer>[] numberLine = buckets();
        int currpower = 1;
        int iterations = longestnum(arr);
        int length = arr.length;
        for (int i = 1; i < iterations + 1; i++) {
            for (int j = 0; j < length; j++) {
                int num = arr[j] / currpower;
                num = num % 10;
                numberLine[num + 10].addLast(arr[j]);
            }
            int k = 0;
            for (int l = 0; l < numberLine.length; l++) {
                while (!numberLine[l].isEmpty()) {
                    arr[k] = numberLine[l].remove();
                    k++;
                }
            }
            currpower *= 10;
        }
    }

    /**
     * calculates and returns the largest amount of iterations
     * that will have to be done in order for the array to be
     * completely sorted
     *
     * @param arr the array that is being sorted
     * @return the number of iterations that will have to be done
     */
    private static int longestnum(int[] arr) {
        int biggestInt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                biggestInt = Integer.MAX_VALUE;
            } else {
                int b = java.lang.Math.abs(arr[i]);
                if (b > biggestInt) {
                    biggestInt = b;
                }
            }
        }
        int numOfDigits = 0;
        int temp = 1;
        while (temp <= biggestInt) {
            if (numOfDigits == 10) {
                return 10;
            } else {
                numOfDigits++;
                temp *= 10;
            }
        }
        return numOfDigits;
    }

    /**
     * creates 19 different buckets that contain linked lists.
     * these buckets represent integers between -9 and 9(inclusive)
     * and the buckets are used to separate the ints in the arr by digit
     * place.
     *
     * @return the buckets of linked lists
     */
    private static LinkedList<Integer>[] buckets() {
        LinkedList<Integer>[] buckets = new LinkedList[20];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        return buckets;
    }
}