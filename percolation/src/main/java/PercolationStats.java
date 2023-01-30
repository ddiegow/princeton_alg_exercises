import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    /**
     *
     */
    private static final double CONFIDENCE_95 = 1.96;
    /**
     *
     */
    private double[] percolationThresholds;
    /**
     *
     */
    private int trials;

    /**
     * perform independent trials on an n-by-n grid
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        percolationThresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            int numberOfOpens = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
            }
            percolationThresholds[i] = (double) percolation.numberOfOpenSites()/ (n*n);
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = null;
        if (args.length == 2) {
            percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        else {
            percolationStats = new PercolationStats(20, 10);
        }

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }

}
