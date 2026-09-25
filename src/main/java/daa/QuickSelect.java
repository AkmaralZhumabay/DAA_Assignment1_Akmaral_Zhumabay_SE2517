package daa;

public class QuickSelect {

    private QuickSelect() {
    }

    public static int select(int[] a, int k, Metrics metrics) {

        if (a == null) {
            throw new IllegalArgumentException("Array must not be null.");
        }

        if (a.length == 0) {
            throw new IllegalArgumentException("Array must not be empty.");
        }

        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k must be between 0 and " + (a.length - 1) + ".");
        }

        if (metrics == null) {
            throw new IllegalArgumentException("Metrics must not be null.");
        }

        metrics.reset();

        long start = System.nanoTime();

        int result = selectInternal(a, k, metrics);

        metrics.setTimeNanos(System.nanoTime() - start);

        return result;
    }

    private static int selectInternal(
            int[] a,
            int k,
            Metrics metrics) {

        int left = 0;
        int right = a.length - 1;
        int depth = 1;

        while (left <= right) {

            metrics.updateDepth(depth);

            if (left == right) {
                return a[left];
            }

            Partition.Bounds bounds =
                    Partition.partition(a, left, right, metrics);

            if (k < bounds.getLt()) {
                right = bounds.getLt() - 1;
            } else if (k > bounds.getGt()) {
                left = bounds.getGt() + 1;
            } else {
                return a[k];
            }

            depth++;
        }

        throw new IllegalStateException(
                "QuickSelect failed to find the requested element.");
    }
}