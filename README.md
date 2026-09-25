# DAA Assignment 1

**Student:** Akmaral Zhumabay  
**Group:** SE-2517

This Maven project implements MergeSort, QuickSort, and QuickSelect in Java. It also includes performance metrics, JUnit 5 tests, CSV benchmarking, and plots.

## Project Structure

- `src/main/java/daa/MergeSort.java` — MergeSort with one reusable buffer and insertion-sort cutoff of 15
- `src/main/java/daa/QuickSort.java` — randomized 3-way QuickSort with smaller-side recursion
- `src/main/java/daa/QuickSelect.java` — finds the k-th smallest element using the same 3-way partition
- `src/main/java/daa/Partition.java` — shared 3-way partition implementation
- `src/main/java/daa/Metrics.java` — stores comparisons, maximum recursion depth, and execution time
- `src/main/java/daa/Benchmark.java` — runs benchmarks and creates `results.csv`
- `src/test/java/daa/` — JUnit 5 tests
- `plot_results.py` — creates the required plots from `results.csv`
- `plots/` — generated benchmark plots
- `REPORT.md` — asymptotic analysis and benchmark discussion

## Requirements

- Java 17 or newer
- Maven
- Python 3
- Matplotlib

## Build the Project

Open a terminal in the project folder and run:

```bash
mvn clean compile
```

## Run the Tests

```bash
mvn test
```

The tests check:

- MergeSort against `Arrays.sort`
- QuickSort against `Arrays.sort`
- at least 100 random arrays
- empty arrays
- one-element arrays
- arrays with equal elements
- already sorted arrays
- QuickSort recursion depth
- QuickSelect results against `sorted[k]`

## Run the Benchmark

Run:

```bash
mvn exec:java
```

The benchmark tests the following sizes:

- 1,000
- 10,000
- 100,000
- 1,000,000

For every size, three input types are used:

- `random`
- `sorted`
- `duplicates`

Each case is executed five times, and the median-time result is saved.

The benchmark creates:

```text
results.csv
```

The CSV columns are:

```text
algorithm,input,n,time_ms,comparisons,max_depth
```

## Generate the Plots

After generating `results.csv`, run:

```bash
python plot_results.py
```

This creates:

```text
plots/time.png
plots/depth.png
plots/ratio.png
```

The ratio plot uses:

- `comparisons / (n * log2(n))` for MergeSort and QuickSort
- `comparisons / n` for QuickSelect

## QuickSelect Indexing

QuickSelect uses zero-based indexing.

For example:

- `k = 0` returns the smallest element
- `k = 1` returns the second smallest element
- `k = a.length - 1` returns the largest element

## Algorithms

### MergeSort

MergeSort recursively divides the array into two halves and merges them. One helper buffer is allocated once and reused. Subarrays of size 15 or less are sorted with Insertion Sort.

### QuickSort

QuickSort uses a random pivot and 3-way partitioning into values smaller than, equal to, and greater than the pivot. It recursively processes the smaller side and handles the larger side with a loop to keep recursion depth small.

### QuickSelect

QuickSelect uses the same randomized 3-way partition as QuickSort. After partitioning, it continues only in the part containing index `k`.

## Submission

The final repository should use the `main` branch and contain the tag:

```text
v1.0
```

The final ZIP should contain the source code, tests, `results.csv`, plots, `README.md`, and `REPORT.md`.