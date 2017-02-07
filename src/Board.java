import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author petra
 *
 */
public class Board {
    private int[][] board;

    /**
     * construct a board from an N-by-N array of blocks
     * 
     * @param blocks
     *            where blocks[i][j] = block in row i, column j
     */
    public Board(int[][] blocks) {
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * Returns board dimension N
     * 
     * @return board dimension N
     */
    public int dimension() {
        return board.length;
    }

    /**
     * Computes the number of blocks out of place
     * 
     * @return number of blocks out of place
     */
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != i * dimension() + j + 1) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    }

    /**
     * Computes sum of Manhattan distances between blocks and goal
     * 
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    int rightNumber = i * dimension() + j + 1;
                    if (board[i][j] != rightNumber) {
                        int rightI = (board[i][j] - 1) / dimension();
                        int rightJ = (board[i][j] - 1) % dimension();
                        manhattan += Math.abs(rightI - i);
                        manhattan += Math.abs(rightJ - j);
                    }
                }
            }
        }
        return manhattan;
    }

    /**
     * Tells whether this board is the goal board
     * 
     * @return True if this is this board the goal board?, false otherwise
     */
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int position = i * dimension() + j + 1;
                if (position == dimension() * dimension()) {
                    if (board[i][j] == 0) {
                        return true;
                    }
                    return false;
                }
                if (board[i][j] != position) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates a board that is obtained by exchanging two adjacent blocks in the
     * same row
     * 
     * @return a board that is obtained by exchanging two adjacent blocks in the
     *         same row
     */
    public Board twin() {
        Board twinBoard = new Board(board);
        if (board[0][0] == 0 || board[0][1] == 0) {
            int tmp = board[1][0];
            twinBoard.board[1][0] = twinBoard.board[1][1];
            twinBoard.board[1][1] = tmp;
        } else {
            int tmp = board[0][0];
            twinBoard.board[0][0] = twinBoard.board[0][1];
            twinBoard.board[0][1] = tmp;
        }
        return twinBoard;
    }

    /**
     * Tells whether this board is equal y?
     * 
     * @return True if y is equal to this board, false otherwise
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass().equals(this.getClass())) {
            Board boardY = (Board) y;
            if (boardY.dimension() == dimension()) {
                for (int i = 0; i < dimension(); i++) {
                    for (int j = 0; j < dimension(); j++) {
                        if (boardY.board[i][j] != board[i][j]) {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Finds all neighboring boards
     * 
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    if (i < dimension() - 1) {
                        Board neighbor = new Board(board);
                        neighbor.board[i][j] = neighbor.board[i + 1][j];
                        neighbor.board[i + 1][j] = 0;
                        neighbors.add(neighbor);
                    }
                    if (i > 0) {
                        Board neighbor = new Board(board);
                        neighbor.board[i][j] = neighbor.board[i - 1][j];
                        neighbor.board[i - 1][j] = 0;
                        neighbors.add(neighbor);
                    }
                    if (j < dimension() - 1) {
                        Board neighbor = new Board(board);
                        neighbor.board[i][j] = neighbor.board[i][j + 1];
                        neighbor.board[i][j + 1] = 0;
                        neighbors.add(neighbor);
                    }
                    if (j > 0) {
                        Board neighbor = new Board(board);
                        neighbor.board[i][j] = neighbor.board[i][j - 1];
                        neighbor.board[i][j - 1] = 0;
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Creates string representation of this board (in the output format
     * specified below)
     * 
     * @return string representation of this board (in the output format
     *         specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
