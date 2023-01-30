import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
public class Percolation {
    /**
     * Grid is size n*n + 2. The extra 2 cells correspond to the virtual top and bottom cells
     */
    private WeightedQuickUnionUF wqu, wqu2;
    /**
     * used to save the size of each tree using the "root" position
     */
    private int[] sz;
    /**
     * saves the "open" state of each cell. we cannot use the typical grid[i] == i because a cell can be open but not connected to any other cell
     */
    private boolean[] open;
    /**
     * number of open sites
     */
    private int openSites = 0;
    /**
     * number of rows/columns
     */
    private int n;

    /**
     * creates n-by-n grid, with all sites initially blocked
     * @param n number of rows/columns
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        wqu = new WeightedQuickUnionUF(n*n + 2);
        wqu2 = new WeightedQuickUnionUF(n*n+1);
        this.n = n;
        sz = new int[n*n + 2];
        open = new boolean[n*n + 2];
        sz[0] = 1;
        sz[n*n + 1] = 1;
        open[0] = true;
        open[n*n + 1] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int pos = rowColumnTo1D(i, j);
                sz[pos] = 1;
                open[pos] = false;
            }
        }
    }

    /**
     * opens the site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validateCoordinates(row, col);
        // if already open
        if (isOpen(row, col)) {
            return; // do nothing
        }
        openSites++;
        open[rowColumnTo1D(row, col)] = true;
        // connect to left cell
        if ((col - 1) >= 1 && isOpen(row, col-1)) {
            wqu.union(rowColumnTo1D(row, col-1), rowColumnTo1D(row, col));
            wqu2.union(rowColumnTo1D(row, col-1), rowColumnTo1D(row, col));
        }

        // connect to right cell
        if ((col + 1) <= n && isOpen(row, col+1)) {
            wqu.union(rowColumnTo1D(row, col+1), rowColumnTo1D(row, col));
            wqu2.union(rowColumnTo1D(row, col+1), rowColumnTo1D(row, col));
        }

        // connect to bottom cell
        if (row == n) {
            wqu.union(n*n+1, rowColumnTo1D(row, col));
        }
        else {
            if (isOpen(row+1, col) && wqu.find(n*n+1) != 0) {
                wqu.union(rowColumnTo1D(row+1, col), rowColumnTo1D(row, col));
                wqu2.union(rowColumnTo1D(row+1, col), rowColumnTo1D(row, col));
            }
        }
        // connect to top cell
        if ((row - 1) == 0) {
            wqu.union(0, rowColumnTo1D(row, col));
            wqu2.union(0, rowColumnTo1D(row, col));
        }
        else {
            if (isOpen(row-1, col)) {
                wqu.union(rowColumnTo1D(row-1, col), rowColumnTo1D(row, col));
                wqu2.union(rowColumnTo1D(row-1, col), rowColumnTo1D(row, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCoordinates(row, col);
        return open[rowColumnTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateCoordinates(row, col);
        return wqu2.find(rowColumnTo1D(row, col)) == wqu2.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.find(n*n+1) == wqu.find(0);
    }

    private int rowColumnTo1D(int row, int column) {
        return ((row - 1) * n + column);
    }
    private boolean inRange(int x, int low, int high) {
        return x >= low && x <= high;
    }
    private void validateCoordinates(int row, int column) {
        if (!inRange(row, 1, n) || !inRange(column, 1, n)) {
            System.err.println("Invalid coordinates: (" + row + ", " + column + ")");
            throw new IllegalArgumentException();
        }
    }
    // test client (optional)
    public static void main(String[] args) {
        if (args.length == 1) {
            In in = new In(args[0]);
            int n = in.readInt();
            Percolation percolation = new Percolation(n);
            while (!in.isEmpty()) {
                int row = in.readInt();
                int column = in.readInt();
                percolation.open(row, column);
                System.out.printf("Opened (%d, %d)\n", row, column);
                System.out.println("Position is full: " + percolation.isFull(row, column));
                System.out.println("System percolates: " + percolation.percolates());
            }
        }

    }
}
