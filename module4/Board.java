/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] board;
    private int n;

    private class Coordinates {
        private int i;
        private int j;

        private Coordinates(int i, int j) {
            this.i = i;
            this.j = j;
        }

    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length == 0) {
            throw new IllegalArgumentException();
        }
        n = tiles.length;
        board = tiles.clone();
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((n * i + j + 1 == n * n && board[i][j] != 0)
                        || (n * i + j + 1 != n * n && board[i][j] != positionToNumber(i, j))) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int distanceX = j - numberToPosition(board[i][j]).j;
                int distanceY = i - numberToPosition(board[i][j]).i;
                manhattan += Integer.max(distanceX, -distanceX) + Integer.max(distanceY,
                                                                              -distanceY);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> iterable = new Stack<>();
        Coordinates zero = findZero();

        if (zero.i > 0) { // we can look below
            int[][] boardCopy = board.clone();
            int tmp = boardCopy[zero.i][zero.j];
            boardCopy[zero.i][zero.j] = boardCopy[zero.i - 1][zero.j];
            boardCopy[zero.i - 1][zero.j] = tmp;
            iterable.push(new Board(boardCopy));
        }
        if (zero.i < n - 1) { // we can look above
            int[][] boardCopy = board.clone();
            int tmp = boardCopy[zero.i][zero.j];
            boardCopy[zero.i][zero.j] = boardCopy[zero.i + 1][zero.j];
            boardCopy[zero.i + 1][zero.j] = tmp;
            iterable.push(new Board(boardCopy));
        }
        if (zero.j > 0) { // we can look to the left
            int[][] boardCopy = board.clone();
            int tmp = boardCopy[zero.i][zero.j];
            boardCopy[zero.i][zero.j] = boardCopy[zero.i][zero.j - 1];
            boardCopy[zero.i][zero.j - 1] = tmp;
            iterable.push(new Board(boardCopy));
        }
        if (zero.j < n - 1) { // we can look to the right
            int[][] boardCopy = board.clone();
            int tmp = boardCopy[zero.i][zero.j];
            boardCopy[zero.i][zero.j] = boardCopy[zero.i][zero.j + 1];
            boardCopy[zero.i][zero.j + 1] = tmp;
            iterable.push(new Board(boardCopy));
        }

        return iterable;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    private int positionToNumber(int i, int j) {
        return n * i + j + 1;
    }

    private Coordinates numberToPosition(int number) {
        if (number == 0) {
            return new Coordinates(n - 1, n - 1);
        }
        int i = (number - 1) / n;
        int j = (number - 1) % n;
        return new Coordinates(i, j);
    }

    private Coordinates findZero() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    return new Coordinates(i, j);
                }
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board);
        Iterable<Board> it = board.neighbors();
        for (Board b : it) {
            System.out.println(b);
        }
    }
}