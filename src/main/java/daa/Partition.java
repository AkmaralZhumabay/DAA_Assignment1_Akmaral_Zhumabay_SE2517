package daa;

import java.util.concurrent.ThreadLocalRandom;

public class Partition {

    private Partition() {
    }

    public static Bounds partition(
            int[] a,
            int left,
            int right,
            Metrics metrics) {

        int pivotIndex =
                ThreadLocalRandom.current().nextInt(left, right + 1);

        int pivot = a[pivotIndex];

        int lt = left;
        int i = left;
        int gt = right;

        while (i <= gt) {
            metrics.incrementComparisons();

            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else {
                metrics.incrementComparisons();

                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }
        }

        return new Bounds(lt, gt);
    }

    private static void swap(int[] a, int i, int j) {
        if (i != j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public static class Bounds {

        private final int lt;
        private final int gt;

        public Bounds(int lt, int gt) {
            this.lt = lt;
            this.gt = gt;
        }

        public int getLt() {
            return lt;
        }

        public int getGt() {
            return gt;
        }
    }
}