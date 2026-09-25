package daa;

public class Metrics {

    private long comparisons;
    private int maxDepth;
    private long timeNanos;

    public Metrics() {
        reset();
    }

    public void reset() {
        comparisons = 0;
        maxDepth = 0;
        timeNanos = 0;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void updateDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getTimeNanos() {
        return timeNanos;
    }

    public double getTimeMillis() {
        return timeNanos / 1_000_000.0;
    }

    public void setTimeNanos(long timeNanos) {
        this.timeNanos = timeNanos;
    }
}