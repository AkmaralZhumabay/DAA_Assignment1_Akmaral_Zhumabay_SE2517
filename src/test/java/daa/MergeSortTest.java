package daa;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

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

            MergeSort.sort(actual, new Metrics());

            assertArrayEquals(expected, actual);
        }
    }

    @Test
    void sortsEmptyArray() {
        int[] actual = {};

        MergeSort.sort(actual, new Metrics());

        assertArrayEquals(new int[]{}, actual);
    }

    @Test
    void sortsOneElement() {
        int[] actual = {42};

        MergeSort.sort(actual, new Metrics());

        assertArrayEquals(new int[]{42}, actual);
    }

    @Test
    void sortsAllEqualElements() {
        int[] actual = {7, 7, 7, 7, 7, 7, 7};

        int[] expected = actual.clone();
        Arrays.sort(expected);

        MergeSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }

    @Test
    void sortsAlreadySortedArray() {
        int[] actual = {1, 2, 3, 4, 5, 6, 7};

        int[] expected = actual.clone();

        MergeSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }
}