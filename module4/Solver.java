/* *****************************************************************************
 *  Name: Daniel Diego Wildin
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> solution = new Stack<>();
    private final boolean isSolvable;

    private final class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final int priority;
        private final Board board;
        private final SearchNode prev;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            if (prev == null) this.moves = 0;
            else this.moves = prev.moves + 1;


            this.priority = moves + board.manhattan();

        }

        public int compareTo(SearchNode other) {
            return this.priority - other.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        if (initial.isGoal()) {
            solution.push(initial);
            isSolvable = true;
            return;
        }
        if (initial.twin().isGoal()) {
            isSolvable = false;
            return;
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        SearchNode sn = new SearchNode(initial, null);
        SearchNode snTwin = new SearchNode(initial.twin(), null);
        pq.insert(sn);
        pqTwin.insert(snTwin);

        while (true) {
            SearchNode tmp = pq.delMin();
            SearchNode tmpTwin = pqTwin.delMin();
            if (tmp.board.isGoal()) {
                isSolvable = true;
                createSolution(tmp);
                break;
            }
            if (tmpTwin.board.isGoal()) {
                isSolvable = false;
                break;
            }
            for (Board board : tmp.board.neighbors()) {
                if (tmp.prev == null) {
                    SearchNode newNode = new SearchNode(board, tmp);
                    pq.insert(newNode);
                    continue;
                }
                if (!board.equals(tmp.prev.board)) { // avoid going back to 2 moves ago
                    SearchNode newNode = new SearchNode(board, tmp);
                    pq.insert(newNode);
                }


            }
            for (Board board : tmpTwin.board.neighbors()) {
                if (tmpTwin.prev == null) {
                    SearchNode newNode = new SearchNode(board, tmp);
                    pqTwin.insert(newNode);
                    continue;
                }
                if (!board.equals(tmpTwin.prev.board)) { // avoid going back to 2 moves ago
                    SearchNode newNode = new SearchNode(board, tmp);
                    pqTwin.insert(newNode);
                }


            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable /**&& pqTwin.min().board.isGoal()*/;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution.size() == 0 ? null : solution;
    }

    private void createSolution(SearchNode node) {

        while (node.prev != null) {
            solution.push(node.board);
            node = node.prev;
        }
        solution.push(node.board);
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
