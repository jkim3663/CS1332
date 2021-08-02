
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 * @userid jkim3663
 * @GTID 903624126
 * <p>
 * Collaborators: None
 * <p>
 * Resources: YouTube, tutorialspoint, GeeksforGeeks
 */
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or the comparator in the parameter cannot be null.");
        }

        int length = arr.length;
        for (int i = 1; i <= length; i++) {
            int j = i - 1;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;

                j--;
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or the comparator in the parameter cannot be null.");
        }
        int length = arr.length;
        int midIndex = length / 2;

        if (arr.length > 1) {
            T[] left = (T[]) new Object[midIndex];
            T[] right = (T[]) new Object[length - midIndex];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + midIndex];
            }

            mergeSort(left, comparator);
            mergeSort(right, comparator);

            mergeHelper(arr, comparator, left, right);
        }
    }

    /**
     * Helper method for mergeSort method.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param left       left side of the array
     * @param right      right side of the array
     */
    public static <T> void mergeHelper(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0;
        int j = 0;

        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }

        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array or the comparator in the parameter cannot be null.");
        }

        List[] buckets = new LinkedList[19];
INSTRUCTOR
| 07/06 AT 9:31 PM
Need generics here on the List declaration.

        int count = 1;
        int max = arr[0];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }


        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }

        while (max >= 10) {
            count++;
            max /= 10;
        }

        int divide = 1;

        for (int i = 0; i < count; i++) {
            for (Integer datum : arr) {
                int digit = (datum / divide) % 10;
                buckets[digit + 9].add(datum);
            }

            int index = 0;

            for (int j = 0; j < buckets.length; j++) {
                while (!buckets[j].isEmpty()) {
                    arr[index] = (int) buckets[j].remove(0);
                    index++;
                }
            }

            divide *= 10;
        }

    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array in parameter causes error.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Null Comparator in parameter causes error.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Null Random object in parameter causes error.");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Number k is out of range of the array.");
        }

        return kthSelectHelper(k, arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Helper method for kthSelect method.
     *
     * @param <T>   data type to sort
     * @param k     k from the kthSelect method
     * @param arr   array from the kthSelect method
     * @param start start of the array
     * @param end   end of the array
     * @param rand random for the pivot
     * @param comparator the comparator to comapre
     * @return the kth element of the array
     */
    private static  <T> T kthSelectHelper(int k, T[] arr, int start, int end, Random rand, Comparator<T> comparator) {
        if ((end - start) < 1) {
            return arr[k - 1];
        }

        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIndex];

        //Swap
        T temp = arr[start];
        arr[start] = arr[pivotIndex];
        arr[pivotIndex] = temp;

        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }

            if (i <= j) {
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }

        temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;

        if (j == k - 1) {
            return arr[j];
        }

        if (j > k - 1) {
            return kthSelectHelper(k, arr, start, j - 1, rand, comparator);
        } else {
            return kthSelectHelper(k, arr, j + 1, end, rand, comparator);
        }
    }
}
