package daa;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {

    @Test
    void selectsCorrectValueOn100RandomArrays() {

        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {

            int size = random.nextInt(999) + 1;

            int[] actual = new int[size];

            for (int i = 0; i < size; i++) {
                actual[i] =
                        random.nextInt(10_000) - 5_000;
            }

            int[] sorted = actual.clone();

            Arrays.sort(sorted);

            int k = random.nextInt(size);

            int result = QuickSelect.select(
                    actual,
                    k,
                    new Metrics()
            );

            assertEquals(sorted[k], result);
        }
    }

    @Test
    void selectsFromOneElementArray() {

        int[] actual = {42};

        int result = QuickSelect.select(
                actual,
                0,
                new Metrics()
        );

        assertEquals(42, result);
    }

    @Test
    void selectsFromAllEqualArray() {

        int[] actual = {
                7, 7, 7, 7, 7, 7
        };

        int result = QuickSelect.select(
                actual,
                3,
                new Metrics()
        );

        assertEquals(7, result);
    }

    @Test
    void selectsFromSortedArray() {

        int[] actual = {
                1, 2, 3, 4, 5, 6, 7
        };

        int result = QuickSelect.select(
                actual,
                4,
                new Metrics()
        );

        assertEquals(5, result);
    }

    @Test
    void emptyArrayThrowsException() {

        int[] actual = {};

        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(
                        actual,
                        0,
                        new Metrics()
                )
        );
    }

    @Test
    void negativeKThrowsException() {

        int[] actual = {1, 2, 3};

        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(
                        actual,
                        -1,
                        new Metrics()
                )
        );
    }

    @Test
    void tooLargeKThrowsException() {

        int[] actual = {1, 2, 3};

        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(
                        actual,
                        3,
                        new Metrics()
                )
        );
    }
}