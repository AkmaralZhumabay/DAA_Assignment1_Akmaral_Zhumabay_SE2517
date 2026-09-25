import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


RESULTS_FILE = "results.csv"
PLOTS_DIRECTORY = "plots"

ALGORITHMS = [
    "MergeSort",
    "QuickSort",
    "QuickSelect"
]

INPUT_TYPES = [
    "random",
    "sorted",
    "duplicates"
]

COLORS = {
    "MergeSort": "tab:blue",
    "QuickSort": "tab:orange",
    "QuickSelect": "tab:green"
}

LINE_STYLES = {
    "random": "-",
    "sorted": "--",
    "duplicates": ":"
}

MARKERS = {
    "random": "o",
    "sorted": "s",
    "duplicates": "^"
}


def load_results():
    data = pd.read_csv(RESULTS_FILE)

    required_columns = {
        "algorithm",
        "input",
        "n",
        "time_ms",
        "comparisons",
        "max_depth"
    }

    missing = required_columns - set(data.columns)

    if missing:
        raise ValueError(
            "Missing columns in results.csv: "
            + ", ".join(sorted(missing))
        )

    return data


def prepare_plot_directory():
    os.makedirs(PLOTS_DIRECTORY, exist_ok=True)


def plot_lines(data, y_column, ylabel, title):

    for algorithm in ALGORITHMS:

        for input_type in INPUT_TYPES:

            subset = data[
                (data["algorithm"] == algorithm)
                & (data["input"] == input_type)
            ].sort_values("n")

            if subset.empty:
                continue

            label = f"{algorithm} / {input_type}"

            plt.plot(
                subset["n"],
                subset[y_column],
                color=COLORS[algorithm],
                linestyle=LINE_STYLES[input_type],
                marker=MARKERS[input_type],
                linewidth=1.8,
                markersize=6,
                label=label
            )

    plt.xlabel("n (input size)")
    plt.ylabel(ylabel)
    plt.title(title)
    plt.grid(
        True,
        which="both",
        linestyle="--",
        alpha=0.3
    )
    plt.legend(
        bbox_to_anchor=(1.02, 1),
        loc="upper left"
    )


def create_time_plot(data):

    plt.figure(figsize=(12, 7))

    plot_lines(
        data,
        "time_ms",
        "Time (ms)",
        "Time vs n"
    )

    # Same idea as the provided example:
    # logarithmic n and logarithmic running time.
    plt.xscale("log")
    plt.yscale("log")

    plt.tight_layout()

    plt.savefig(
        os.path.join(PLOTS_DIRECTORY, "time.png"),
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()


def create_depth_plot(data):

    plt.figure(figsize=(12, 7))

    plot_lines(
        data,
        "max_depth",
        "Max recursion depth",
        "Max recursion depth vs n"
    )

    # n = 10^3, 10^4, 10^5, 10^6
    plt.xscale("log")

    plt.tight_layout()

    plt.savefig(
        os.path.join(PLOTS_DIRECTORY, "depth.png"),
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()


def create_ratio_plot(data):

    ratio_data = data.copy()

    ratio_data["ratio"] = np.where(
        ratio_data["algorithm"] == "QuickSelect",
        ratio_data["comparisons"]
        / ratio_data["n"],
        ratio_data["comparisons"]
        / (
            ratio_data["n"]
            * np.log2(ratio_data["n"])
        )
    )

    plt.figure(figsize=(12, 7))

    plot_lines(
        ratio_data,
        "ratio",
        "Comparisons ratio",
        (
            "Comparisons ratio vs n\n"
            "(sorts: comparisons / (n·log2(n)), "
            "QuickSelect: comparisons / n)"
        )
    )

    plt.xscale("log")

    plt.tight_layout()

    plt.savefig(
        os.path.join(PLOTS_DIRECTORY, "ratio.png"),
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()


def main():

    prepare_plot_directory()

    data = load_results()

    create_time_plot(data)
    create_depth_plot(data)
    create_ratio_plot(data)

    print("Plots created successfully:")
    print("  plots/time.png")
    print("  plots/depth.png")
    print("  plots/ratio.png")


if __name__ == "__main__":
    main()