package daa;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {

    @Test
    void sorts100RandomArrays() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            int size = random.nextInt(1000);

            int[] actual = new int[size];

            for (int i = 0; i < size; i++) {
                actual[i] = random.nextInt(10_000) - 5_000;
            }

            int[] expected = actual.clone();

            Arrays.sort(expected);

            QuickSort.sort(actual, new Metrics());

            assertArrayEquals(expected, actual);
        }
    }

    @Test
    void sortsEmptyArray() {

        int[] actual = {};

        QuickSort.sort(actual, new Metrics());

        assertArrayEquals(new int[]{}, actual);
    }

    @Test
    void sortsOneElement() {

        int[] actual = {42};

        QuickSort.sort(actual, new Metrics());

        assertArrayEquals(new int[]{42}, actual);
    }

    @Test
    void sortsAllEqualElements() {

        int[] actual = {
                5, 5, 5, 5, 5,
                5, 5, 5, 5, 5
        };

        int[] expected = actual.clone();

        QuickSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }

    @Test
    void sortsAlreadySortedArray() {

        int[] actual = {
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10
        };

        int[] expected = actual.clone();

        QuickSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }

    @Test
    void sortedArrayHasBoundedRecursionDepth() {

        int n = 100_000;

        int[] actual = new int[n];

        for (int i = 0; i < n; i++) {
            actual[i] = i;
        }

        Metrics metrics = new Metrics();

        QuickSort.sort(actual, metrics);

        double limit =
                2.0 * (Math.log(n) / Math.log(2));

        assertTrue(
                metrics.getMaxDepth() <= limit,
                "Depth was " +
                        metrics.getMaxDepth() +
                        ", but limit was " +
                        limit
        );
    }
}