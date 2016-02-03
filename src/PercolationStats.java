import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Mari on 02/02/16.
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("array size " + N + " or quantity of experiments T" + T +
                    " is not valid (should be > 0)");
        }


        double[] thresholds = new double[T + 1];
        int xt = 0;
        for (int i = 1; i <= T; i++) {
            Percolation percolationModel = new Percolation(N);
            int p = StdRandom.uniform(1, N + 1);
            int q = StdRandom.uniform(1, N + 1);

            if (percolationModel.isOpen(p, q)) {
                thresholds[i] = (double) xt / T;
                continue;
            } else {
                percolationModel.open(p, q);
                ++xt;
                thresholds[i] = (double) xt / T;
//                PercolationVisualizer.draw(percolationModel, N);
//                StdDraw.show(20);
            }

            if (percolationModel.percolates()) {
                mean = StdStats.mean(thresholds);
                stddev = StdStats.stddev(thresholds);
                confidenceLo = mean - 1.96 * stddev / Math.sqrt(T);
                confidenceHi = mean + 1.96 * stddev / Math.sqrt(T);
                break;
            }

        }
    }    // perform T independent experiments on an N-by-N grid

    public double mean() { return mean; }                      // sample mean of percolation threshold

    public double stddev() { return stddev; }      // sample standard deviation of percolation threshold

    public double confidenceLo() { return confidenceLo; }    // low  endpoint of 95% confidence interval

    public double confidenceHi() { return confidenceHi; }             // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        // N-by-N percolation system (read from command-line, default = 10)
    }   // test client (described below)
}

