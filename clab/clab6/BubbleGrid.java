public class BubbleGrid {
    private int[][] grid;
    private int rowNum;
    private int colNum;

    /**
     * Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space.
     */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        rowNum = grid.length;
        colNum = grid[0].length;
    }

    /**
     * Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown(popped bubbles do not fall).
     * Assume all elements of darts are unique, valid locations in the grid.
     * Must be non-destructive and have no side-effects to grid.
     */
    public int[] popBubbles(int[][] darts) {
        // TODO

        UnionFind uf = new UnionFind(rowNum * colNum + 1);
        for (int[] dart : darts) {
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }


        return null;
    }
}
