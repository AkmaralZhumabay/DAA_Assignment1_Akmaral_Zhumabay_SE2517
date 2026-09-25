package daa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            1_000,
            10_000,
            100_000,
            1_000_000
    };

    private static final String[] INPUT_TYPES = {
            "random",
            "sorted",
            "duplicates"
    };

    private static final int RUNS = 5;

    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) throws IOException {

        warmUp();

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("results.csv"))) {

            writer.write(
                    "algorithm,input,n,time_ms,comparisons,max_depth");
            writer.newLine();

            for (int n : SIZES) {
                for (String inputType : INPUT_TYPES) {

                    int[] baseArray =
                            generateInput(n, inputType);

                    benchmarkMergeSort(
                            baseArray,
                            inputType,
                            writer);

                    benchmarkQuickSort(
                            baseArray,
                            inputType,
                            writer);

                    benchmarkQuickSelect(
                            baseArray,
                            inputType,
                            writer);
                }
            }
        }

        System.out.println(
                "Benchmark complete. Results saved to results.csv");
    }

    private static void warmUp() {

        System.out.println("Warming up JVM...");

        for (int i = 0; i < 5; i++) {

            int[] base = generateRandomArray(20_000);

            int[] mergeArray = base.clone();
            MergeSort.sort(mergeArray, new Metrics());

            int[] quickArray = base.clone();
            QuickSort.sort(quickArray, new Metrics());

            int[] selectArray = base.clone();
            QuickSelect.select(
                    selectArray,
                    selectArray.length / 2,
                    new Metrics());
        }
    }

    private static void benchmarkMergeSort(
            int[] baseArray,
            String inputType,
            BufferedWriter writer) throws IOException {

        RunResult[] results = new RunResult[RUNS];

        for (int run = 0; run < RUNS; run++) {

            int[] copy = baseArray.clone();

            Metrics metrics = new Metrics();

            MergeSort.sort(copy, metrics);

            results[run] = new RunResult(
                    metrics.getTimeMillis(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth());
        }

        writeMedian(
                writer,
                "MergeSort",
                inputType,
                baseArray.length,
                results);
    }

    private static void benchmarkQuickSort(
            int[] baseArray,
            String inputType,
            BufferedWriter writer) throws IOException {

        RunResult[] results = new RunResult[RUNS];

        for (int run = 0; run < RUNS; run++) {

            int[] copy = baseArray.clone();

            Metrics metrics = new Metrics();

            QuickSort.sort(copy, metrics);

            results[run] = new RunResult(
                    metrics.getTimeMillis(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth());
        }

        writeMedian(
                writer,
                "QuickSort",
                inputType,
                baseArray.length,
                results);
    }

    private static void benchmarkQuickSelect(
            int[] baseArray,
            String inputType,
            BufferedWriter writer) throws IOException {

        RunResult[] results = new RunResult[RUNS];

        int k = baseArray.length / 2;

        for (int run = 0; run < RUNS; run++) {

            int[] copy = baseArray.clone();

            Metrics metrics = new Metrics();

            QuickSelect.select(copy, k, metrics);

            results[run] = new RunResult(
                    metrics.getTimeMillis(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth());
        }

        writeMedian(
                writer,
                "QuickSelect",
                inputType,
                baseArray.length,
                results);
    }

    private static void writeMedian(
            BufferedWriter writer,
            String algorithm,
            String inputType,
            int n,
            RunResult[] results) throws IOException {

        Arrays.sort(
                results,
                Comparator.comparingDouble(result -> result.timeMs));

        RunResult median = results[RUNS / 2];

        writer.write(
                algorithm + "," +
                        inputType + "," +
                        n + "," +
                        String.format(
                                java.util.Locale.US,
                                "%.6f",
                                median.timeMs) + "," +
                        median.comparisons + "," +
                        median.maxDepth);

        writer.newLine();

        System.out.printf(
                "%-12s %-12s n=%-8d time=%8.3f ms comparisons=%d depth=%d%n",
                algorithm,
                inputType,
                n,
                median.timeMs,
                median.comparisons,
                median.maxDepth);
    }

    private static int[] generateInput(
            int n,
            String inputType) {

        switch (inputType) {

            case "random":
                return generateRandomArray(n);

            case "sorted":
                return generateSortedArray(n);

            case "duplicates":
                return generateDuplicatesArray(n);

            default:
                throw new IllegalArgumentException(
                        "Unknown input type: " + inputType);
        }
    }

    private static int[] generateRandomArray(int n) {

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = RANDOM.nextInt();
        }

        return a;
    }

    private static int[] generateSortedArray(int n) {

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        return a;
    }

    private static int[] generateDuplicatesArray(int n) {

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = RANDOM.nextInt(10);
        }

        return a;
    }

    private static class RunResult {

        private final double timeMs;
        private final long comparisons;
        private final int maxDepth;

        private RunResult(
                double timeMs,
                long comparisons,
                int maxDepth) {

            this.timeMs = timeMs;
            this.comparisons = comparisons;
            this.maxDepth = maxDepth;
        }
    }
}