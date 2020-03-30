import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
	
	private int numTrials;
	private static double[] opened;
	private double mean, std, confLow, confHi;
	
	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	
    	if (n <= 0 || trials <= 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	numTrials = trials;
    	opened = new double[numTrials];
    	
    	for (int t = 0; t < numTrials; t++) {
    		Percolation percolation = new Percolation(n);
    		
    		while (!percolation.percolates()) {
    			int row = StdRandom.uniform(0, n);
    			int col = StdRandom.uniform(0, n);
    			
    			percolation.open(row+1, col+1);
    		}
    		
    		opened[t] = (double)percolation.numberOfOpenSites()/(n*n);
    		
    	}
    	
    	mean = StdStats.mean(opened);
    	std = StdStats.stddev(opened);
    	confLow = mean - ((1.96 * std) / Math.sqrt(numTrials));
    	confHi = mean + ((1.96 * std) / Math.sqrt(numTrials));
    	
    }

    // sample mean of percolation threshold
    public double mean() {
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return confLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return confHi;
    }

   // test client (see below)
   public static void main(String[] args) {
	   int gridSize = Integer.parseInt(args[0]);
	   int trials = Integer.parseInt(args[1]);
	   
	   PercolationStats pStats = new PercolationStats(gridSize, trials);
	   
	   StdOut.println("mean = " + pStats.mean());
	   StdOut.println("stddev = " + pStats.stddev());
	   StdOut.println("95% confidence interval = [" + pStats.confidenceLo() + 
			   ", " + pStats.confidenceHi() + "]");
	   
   }

}
