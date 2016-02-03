/**
 * Created by Mari on 31/01/16.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] sites;
    private int initialN;
    private WeightedQuickUnionUF weightedTree;

/*
* The constructor should take time proportional to N2
* */

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("array size " + N + " is not valid (should be > 0)");
        }

        initialN = N;
        sites = new boolean[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                sites[i][j] = false;
            }
        }

        weightedTree = new WeightedQuickUnionUF(N * N + 2);
    }               // create N-by-N grid, with all sites blocked


/*
* all methods should take constant time plus a constant number of calls
* to the union-find methods union(), find(), connected(), and count().
* */

    public void open(int i, int j) {
        if (i < 1 || i > initialN || j < 1 || j > initialN)
            throw new IndexOutOfBoundsException("some index(es) is(are) not between 1 and " + initialN);

        if (isOpen(i, j))
            return;
        sites[i][j] = true;
//        ToDo: connect to other sites. Check if it's upper or lower bound

        if (i == 1)
            weightedTree.union(0, (i - 1) * initialN + j);
        else if (isOpen(i - 1, j))
            weightedTree.union((i - 2) * initialN + j, (i - 1) * initialN + j);

        if (j == 1) {
            if (initialN > 1 && isOpen(i, j + 1))
                weightedTree.union((i - 1) * initialN + j + 1, (i - 1) * initialN + j);
        } else if (j == initialN) {
            if (initialN > 1 && isOpen(i, j - 1))
                weightedTree.union((i - 1) * initialN + j - 1, (i - 1) * initialN + j);
        } else {
            if (isOpen(i, j - 1))
                weightedTree.union((i - 1) * initialN + j - 1, (i - 1) * initialN + j);
            if (isOpen(i, j + 1))
                weightedTree.union((i - 1) * initialN + j + 1, (i - 1) * initialN + j);
        }

        if (i == initialN /*&& !percolates()*/) {
//            if (weightedTree.connected(0, (i - 1) * initialN + j))
            // stupid solution
            weightedTree.union(initialN * initialN + 1, (i - 1) * initialN + j);
        } else if (isOpen(i + 1, j))
            weightedTree.union(i * initialN + j, (i - 1) * initialN + j);

        /* ToDo another hypotheses for percolation */


    }      // open site (row i, column j) if it is not open already

    public boolean isOpen(int i, int j) {
        if (i < 1 || i > initialN || j < 1 || j > initialN)
            throw new IndexOutOfBoundsException("some index(es) is(are) not between 1 and " + initialN);

        return sites[i][j];
    }  // is site (row i, column j) open?

    public boolean isFull(int i, int j) {
        if (i < 1 || i > initialN || j < 1 || j > initialN)
            throw new IndexOutOfBoundsException("some index(es) is(are) not between 1 and " + initialN);

        return isOpen(i, j) && weightedTree.connected(0, (i - 1) * initialN + j);
    }    // is site (row i, column j) full?

    public boolean percolates() {
        return weightedTree.connected(0, initialN * initialN + 1);
    }            // does the system percolate?

    public static void main(String[] args) {

    } // test client (optional)
}
