import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double thres[];
	private final int trials;

	public PercolationStats(int n, int trials) { // perform trials independent
													// experiments on an n-by-n
													// grid
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		this.trials = trials;
		this.thres = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation perco = new Percolation(n);
			while(!perco.percolates()) {
				int r = StdRandom.uniform(1, n + 1);
				int c = StdRandom.uniform(1, n + 1);
				perco.open(r, c);
			}
			double prr = (((double) perco.numberOfOpenSites()) / (n * n));
			thres[i] = prr;
		}
	}

	public double mean() {// sample mean of percolation threshold
		return StdStats.mean(thres);
	}

	public double stddev() {// sample standard deviation of percolation
							// threshold
		return StdStats.stddev(thres);
	}

	public double confidenceLo() {// low endpoint of 95% confidence interval
		return (mean() - (stddev() * 1.96) / Math.sqrt(trials));
	}

	public double confidenceHi() {// high endpoint of 95% confidence interval
		return (mean() + (stddev() * 1.96) / Math.sqrt(trials));
	}

	public static void main(String[] args) {
		int n = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats perst = new PercolationStats(n, T);
		StdOut.println("% java PercolationStats " + n + " " + T);
		StdOut.println("mean        = " + perst.mean());
		StdOut.println("stddev        = " + perst.stddev());
		StdOut.println("95% confidence interval        = [" + perst.confidenceLo() + ", " + perst.confidenceHi() + "]");

	}
}
