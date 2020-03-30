import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private boolean[][] grid;
	private int countOpen;
	private int len;
	private int top = 0;
	private int bot;
	private WeightedQuickUnionUF wquf;
	
	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	
    	if (n <= 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	len=n;
    	grid = new boolean[n][n];
    	wquf = new WeightedQuickUnionUF((n*n)+2);
    	countOpen = 0;
    	bot=(n*n) + 1;
    	
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	
    	if (row <= 0 || row > len || col <= 0 || col > len) {
    		throw new IllegalArgumentException();
    	}
    	
    	if (!isOpen(row, col)) {
    		grid[row-1][col-1] = true;
    		countOpen++;
    		
    		// union with virtual top
        	if (row-1 == 0) {
        		wquf.union(wqufIndex(row, col), top);
        	}
        	
        	// union with virtual bottom
        	if (row == len) {
        		wquf.union(wqufIndex(row, col), bot);
        	}
        	
        	// union with upper neighbor
        	if (row > 1 && isOpen(row - 1, col)) {
        		wquf.union(wqufIndex(row, col), wqufIndex(row - 1, col));
        	}
        	
        	// union with left neighbor
        	if (col > 1 && isOpen(row, col - 1)) {
        		wquf.union(wqufIndex(row, col), wqufIndex(row, col - 1));
        	}
        	
        	// union with right neighbor
        	if (col < len && isOpen(row, col + 1)) {
        		wquf.union(wqufIndex(row, col), wqufIndex(row, col + 1));
        	}
        	
        	// union with lower neighbor
        	if (row < len && isOpen(row + 1, col)) {
        		wquf.union(wqufIndex(row, col), wqufIndex(row + 1, col));
        	}
        	
    	}
    	
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	
    	if (row <= 0 || row > len || col <= 0 || col > len) {
    		throw new IllegalArgumentException();
    	}
    	
    	return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	
    	if (row <= 0 || row > len || col <= 0 || col > len) {
    		throw new IllegalArgumentException();
    	}
    	
    	if (!isOpen(row, col)) {
    		return false;
    	}
    	
    	if (row == 1) {
    		return isOpen(row, col);
    	}
    	
    	return wquf.connected(wqufIndex(row, col), top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	
    	return countOpen;
    	
    }

    // does the system percolate?
    public boolean percolates() {
    	
    	return wquf.connected(top, bot);
    	
    }
    
    private int wqufIndex(int row, int col) {
    	return (len * (row-1)) + (col - 1) + 1;
    }
   

    // test client (optional)
    public static void main(String[] args) {
    	
    }

}
