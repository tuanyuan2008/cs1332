import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sarah
 * @userid schen475
 * @GTID 903190753
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
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
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array/"
                    + "using null comparator.");
        }
        boolean modify = true;
        int j = arr.length - 1;
        while (modify && j > 0) {
            modify = false;
            for (int i = 0; i < j; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    modify = true;
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
            j--;
        }
    }

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
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array/"
                    + "using null comparator.");
        }
        for (int i = 1; i < arr.length; i++) {
            boolean sort = true;
            int j = i;
            while (sort && j > 0) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    sort = false;
                }
                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array/"
                    + "using null comparator.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[i], arr[j]) >= 0) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
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
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot sort null array/"
                    + "using null comparator or rand value.");
        }

        quickSort(0, arr.length - 1, arr, comparator, rand);
    }

    /**
     * Implement quick sort without duplicating array.
     *
     * @param greatInd the left bound of arr on which to run the algorithm
     * @param lessInd the right bound of arr on which to run the algorithm
     * @param arr the array being sorted
     * @param comparator determines how the values are compared
     * @param rand the random generator
     * @param <T> the values' data type
     */
    private static <T> void quickSort(int greatInd, int lessInd, T[] arr,
                                      Comparator<T> comparator, Random rand) {
        if (lessInd - greatInd > 0) {
            int pivotIndex = rand.nextInt(lessInd - greatInd) + greatInd;
            T temp = arr[greatInd];
            arr[greatInd] = arr[pivotIndex];
            arr[pivotIndex] = temp;

            int k = greatInd;
            int l = lessInd; // stored for recursion later
            greatInd++;
            while (greatInd <= lessInd) {
                while (greatInd <= lessInd
                        && comparator.compare(arr[greatInd], arr[k]) <= 0) {
                    greatInd++;
                }

                while (greatInd <= lessInd
                        && comparator.compare(arr[lessInd], arr[k]) >= 0) {
                    lessInd--;
                }

                if (greatInd < lessInd) {
                    T temp1 = arr[greatInd];
                    arr[greatInd] = arr[lessInd];
                    arr[lessInd] = temp1;
                    greatInd++;
                    lessInd--;
                }
            }
            T temp1 = arr[k];
            arr[k] = arr[lessInd];
            arr[lessInd] = temp1;

            quickSort(k, lessInd - 1, arr, comparator, rand);

            quickSort(lessInd + 1, l, arr, comparator, rand);
        }
    }
    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
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
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array/"
                    + "using null comparator.");
        }
        if (arr.length > 1) {
            T[] leftArr = (T[]) new Object[arr.length / 2];
            for (int i = 0; i < arr.length / 2; i++) {
                leftArr[i] = arr[i];
            }
            mergeSort(leftArr, comparator);
            T[] rightArr = (T[]) new Object[arr.length - arr.length / 2];
            for (int i = arr.length / 2; i < arr.length; i++) {
                rightArr[i - arr.length / 2] = arr[i];
            }
            mergeSort(rightArr, comparator);

            int leftInd = 0;
            int rightInd = 0;
            int i = 0;
            while (leftInd < arr.length / 2
                    && rightInd < arr.length - arr.length / 2) {
                if (comparator.compare(leftArr[leftInd],
                        rightArr[rightInd]) <= 0) {
                    arr[i] = leftArr[leftInd];
                    leftInd++;
                } else {
                    arr[i] = rightArr[rightInd];
                    rightInd++;
                }
                i++;
            }
            while (leftInd < arr.length / 2) {
                arr[i] = leftArr[leftInd];
                leftInd++;
                i++;
            }
            while (rightInd < arr.length - arr.length / 2) {
                arr[i] = rightArr[rightInd];
                rightInd++;
                i++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array.");
        }
        if (arr.length > 0) {
            int greatest = 0;
            for (int a : arr) {
                if (Math.abs(a) > greatest) {
                    greatest = Math.abs(a);
                }
            }
            int digits = 1;
            while (greatest / 10 > 0) {
                digits++;
                greatest /= 10;
            }
            int divisor = 1;
            for (int i = 0; i < digits; i++) {
                ArrayList<Integer>[] buckets = new ArrayList[19];
                for (int a : arr) {
                    if (buckets[(a / divisor) % 10 + 9] == null) {
                        buckets[(a / divisor) % 10 + 9] = new ArrayList<>();
                    }
                    buckets[(a / divisor) % 10 + 9].add(a);
                }
                int k = 0;
                for (int j = 0; j < 19; j++) {
                    if (buckets[j] != null) {
                        for (int l = 0; l < buckets[j].size(); l++) {
                            arr[k] = buckets[j].get(l);
                            k++;
                        }
                    }
                }
                divisor *= 10;
            }
        }
    }
}
