import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Slider puzzle solver
 * 
 * @author petra
 *
 */
public class Solver {
    private MinPQ<SearchNode> originalQueue = new MinPQ<Solver.SearchNode>();
    private MinPQ<SearchNode> swappedQueue = new MinPQ<Solver.SearchNode>();

    private SearchNode solution = null;
    private SearchNode swappedSolution = null;

    /**
     * finds a solution to the initial board (using the A* algorithm)
     * 
     * @param initial
     *            initial board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        SearchNode initialSearchNode = new SearchNode();
        initialSearchNode.board = initial;
        initialSearchNode.moves = 0;
        initialSearchNode.previous = null;
        originalQueue.insert(initialSearchNode);

        SearchNode initialSwappedSearchNode = new SearchNode();
        initialSwappedSearchNode.board = initial.twin();
        initialSwappedSearchNode.moves = 0;
        initialSwappedSearchNode.previous = null;
        swappedQueue.insert(initialSwappedSearchNode);

        while (solution == null && swappedSolution == null) {
            search(originalQueue, false);
            search(swappedQueue, true);
        }
    }

    private void search(MinPQ<SearchNode> queue, boolean isSwapped) {
        SearchNode dequeuedNode = queue.delMin();

        if (dequeuedNode.board.isGoal()) {
            swappedSolution = isSwapped ? dequeuedNode : null;
            solution = isSwapped ? null : dequeuedNode;
            return;
        }
        Iterable<Board> neighbors = dequeuedNode.board.neighbors();

        for (Board neighbor : neighbors) {
            if (dequeuedNode.previous == null
                    || !(neighbor.equals(dequeuedNode.previous.board))) {
                SearchNode newNode = new SearchNode();
                newNode.board = neighbor;
                newNode.moves = dequeuedNode.moves + 1;
                newNode.previous = dequeuedNode;
                queue.insert(newNode);
            }
        }
    }

    /**
     * Tells whether the initial board is solvable
     * 
     * @return True if initial board is solvable, false otherwise
     */
    public boolean isSolvable() {
        return solution == null ? false : true;
    }

    /**
     * Computes min number of moves to solve initial board; -1 if unsolvable
     * 
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return isSolvable() ? solution.moves : -1;
    }

    /**
     * Creates sequence of boards in a shortest solution; null if unsolvable
     * 
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (solution == null) {
            return null;
        }

        List<Board> returnSolution = new ArrayList<Board>();
        SearchNode solutionEndNode = solution;

        while (solutionEndNode != null) {
            returnSolution.add(0, solutionEndNode.board);
            solutionEndNode = solutionEndNode.previous;
        }
        return returnSolution;
    }

    /**
     * solves a slider puzzle
     * 
     * @param args
     *            Path to file containing initial board
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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

    private static class SearchNode implements Comparable<SearchNode> {
        private SearchNode previous;
        private int moves;
        private Board board;

        // @Override
        // public int compareTo(SearchNode o) {
        // return Integer.compare(SearchNode.this.board.manhattan() +
        // SearchNode.this.moves,
        // o.board.manhattan() + o.moves);
        // }
        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(board.manhattan() + moves,
                    o.board.manhattan() + o.moves);
        }
    }
}
