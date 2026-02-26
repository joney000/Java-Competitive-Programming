import java.util.LinkedList;

class Pair {
    int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Solution {

    // 4 directions
    private static int[] dx = new int[] { 1, -1, 0, 0 };
    private static int[] dy = new int[] { 0, 0, 1, -1 };

    private boolean isValid(boolean[][] vis, int x, int y, int rows, int cols) {
        return x >= 0 && y >= 0 && x <= rows - 1 && y <= cols - 1 && !vis[x][y];
    }

    // Time: O(grid_size), Space: O(grid_size)
    public int numConnectedComponent(char[][] grid) {
        if (grid == null) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        boolean vis[][] = new boolean[rows][cols];
        int regions = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!vis[row][col] && grid[row][col] == '1') {
                    regions++;
                    fillSegment(vis, grid, row, col, rows, cols);
                }
            }
        }
        return regions;
    }

    // BFS
    private void fillSegment(boolean[][] vis, char[][] grid, int row, int col, int rows, int cols) {
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col));
        vis[row][col] = true;
        while (!queue.isEmpty()) {
            Pair node = (Pair) queue.removeFirst();
            for (int dir = 0; dir < 4; dir++) {
                int nextX = node.x + dx[dir];
                int nextY = node.y + dy[dir];
                if (isValid(vis, nextX, nextY, rows, cols) && grid[nextX][nextY] == '1') {
                    vis[nextX][nextY] = true;
                    queue.add(new Pair(nextX, nextY));
                }
            }
        }
    }
}