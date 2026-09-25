package daa;

public class QuickSort {

    private QuickSort() {
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
            quickSort(a, 0, a.length - 1, metrics, 1);
        }

        metrics.setTimeNanos(System.nanoTime() - start);
    }

    private static void quickSort(
            int[] a,
            int left,
            int right,
            Metrics metrics,
            int depth) {

        while (left < right) {

            metrics.updateDepth(depth);

            Partition.Bounds bounds =
                    Partition.partition(a, left, right, metrics);

            int lt = bounds.getLt();
            int gt = bounds.getGt();

            int leftSize = lt - left;
            int rightSize = right - gt;

            if (leftSize < rightSize) {

                if (left < lt - 1) {
                    quickSort(
                            a,
                            left,
                            lt - 1,
                            metrics,
                            depth + 1);
                }

                left = gt + 1;

            } else {

                if (gt + 1 < right) {
                    quickSort(
                            a,
                            gt + 1,
                            right,
                            metrics,
                            depth + 1);
                }

                right = lt - 1;
            }
        }
    }
}