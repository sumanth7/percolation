import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] site;// 0 if site is closed and 1 if site is opened
	private static int top = 0;
	private final int bottom;
	private final int size;
	private int opensites;
	private final WeightedQuickUnionUF quf;

	public Percolation(int n) {// create n-by-n grid, with all sites blocked
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		size = n;
		opensites = 0;
		bottom = n * n + 1;
		quf = new WeightedQuickUnionUF(n * n + 2);
		site = new boolean[n][n];
	}

	public void open(int row, int col) {// open site (row, col) if it is not
										// open already
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new IllegalArgumentException();
		}
		if (isOpen(row, col)) {
			return;
		} else {
			site[row - 1][col - 1] = true;
			opensites++;
			if (row == 1) {
				quf.union(qfIndex(row, col), top);
			}
			if (row == size) {
				quf.union(qfIndex(row, col), bottom);
			}
			if (col > 1 && isOpen(row, col - 1)) {
				quf.union(qfIndex(row, col), qfIndex(row, col - 1));
			}
			if (col < size && isOpen(row, col + 1)) {
				quf.union(qfIndex(row, col), qfIndex(row, col + 1));
			}
			if (row > 1 && isOpen(row - 1, col)) {
				quf.union(qfIndex(row, col), qfIndex(row - 1, col));
			}
			if (row < size && isOpen(row + 1, col)) {
				quf.union(qfIndex(row, col), qfIndex(row + 1, col));
			}
		}
	}

	private int qfIndex(int i, int j) {
		return ((4 * (i - 1)) + j);
	}

	public boolean isOpen(int row, int col) {// is site (row, col) open?
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new IllegalArgumentException();
		}
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {// is site (row, col) full?
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new IllegalArgumentException();
		}
		return quf.connected(qfIndex(row, col), top);
	}

	public int numberOfOpenSites() {// number of open sites
		return opensites;
	}

	public boolean percolates() {
		return quf.connected(top, bottom);
	}

	public static void main(String[] args) {// test client (optional)
	}
}
