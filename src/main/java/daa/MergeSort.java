package daa;

public class MergeSort {

    private static final int CUTOFF = 15;

    private MergeSort() {
    }

    public static void sort(int[] a, Metrics metrics) {
        if (a == null) {
            throw new IllegalArgumentException("Array must not be null.");
        }

        if (metrics == null) {
            throw new IllegalArgumentException("Metrics must not be null.");
        }

        metrics.reset();

        long start = System.nanoTime();

        if (a.length > 1) {
            int[] buffer = new int[a.length];
            mergeSort(a, buffer, 0, a.length - 1, metrics, 1);
        }

        metrics.setTimeNanos(System.nanoTime() - start);
    }

    private static void mergeSort(
            int[] a,
            int[] buffer,
            int left,
            int right,
            Metrics metrics,
            int depth) {

        metrics.updateDepth(depth);

        int size = right - left + 1;

        if (size <= CUTOFF) {
            insertionSort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(a, buffer, left, mid, metrics, depth + 1);
        mergeSort(a, buffer, mid + 1, right, metrics, depth + 1);

        merge(a, buffer, left, mid, right, metrics);
    }

    private static void merge(
            int[] a,
            int[] buffer,
            int left,
            int mid,
            int right,
            Metrics metrics) {

        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incrementComparisons();

            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            a[k++] = buffer[i++];
        }

        while (j <= right) {
            a[k++] = buffer[j++];
        }
    }

    private static void insertionSort(
            int[] a,
            int left,
            int right,
            Metrics metrics) {

        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.incrementComparisons();

                if (a[j] <= key) {
                    break;
                }

                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }
}