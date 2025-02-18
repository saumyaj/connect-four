package model;

import java.util.Arrays;
import java.util.Optional;

class Board {
    private int[][] board;
    private final int HEIGHT = 6;
    private final int WIDTH = 7;

    int getHeight() {
        return HEIGHT;
    }

    int getWidth() {
        return WIDTH;
    }

    Board() {
        initializeBoard();
    }

    Board(Board sourceBoard) {
        initializeBoard();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[j][i] = sourceBoard.getEntry(j, i);
            }
        }
    }

    int getEntry(int row, int column) {
        return board[row][column];
    }

    private void initializeBoard() {
        this.board = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            Arrays.fill(this.board[i], -1);
        }
    }

    boolean isColumnFull(int col) {
        return board[0][col] != -1;
    }

    int columnSelected(int col, int turn) throws IllegalArgumentException {
        if (col < 0 || col >= WIDTH) {
            throw new IllegalArgumentException("invalid column value; column value should be between 0 and " +
                    WIDTH);
        }
        if (isColumnFull(col)) {
            throw new IllegalArgumentException("invalid column selection; column " + col + " is full");
        }
        int row = nextEmptyRow(col).get();
        board[row][col] = turn;
        return row;
    }

    void removeMostRecentEntryFromColumn(int col) {
        for (int i = 0; i < HEIGHT; i++) {
            if (board[i][col] != -1) {
                board[i][col] = -1;
                break;
            }
        }
    }

    private Optional<Integer> nextEmptyRow(int col) {
        for (int i = this.HEIGHT - 1; i >= 0; i--) {
            if (board[i][col] == -1) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    boolean isBoardFull() {
        for (int j = 0; j < WIDTH; j++) {
            if (board[0][j] == -1) {
                return false;
            }
        }
        return true;
    }

    boolean findContiguousSymbols(int n, int symbol) {

        // Check if n in a row
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (board[row][col] == symbol && col + (n - 1) < WIDTH) {
                    boolean flag = true;
                    for (int k = 1; k < n; k++) {
                        if (board[row][col + k] != symbol) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        return true;
                    }
                }
            }
        }

        // Check if n in a column
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                if (board[row][col] == symbol && row + (n - 1) < HEIGHT) {
                    boolean flag = true;
                    for (int k = 1; k < n; k++) {
                        if (board[row + k][col] != symbol) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        return true;
                    }
                }
            }
        }

        // Check for n consecutive symbols in ascending diagonal.
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (canMove(i + (n - 1), j + (n - 1))) {
                    if (board[i][j] == board[i + 1][j + 1]
                            && board[i][j] == board[i + 2][j + 2]
                            && board[i][j] == board[i + 3][j + 3]
                            && board[i][j] == symbol) {
                        return true;
                    }
                }
            }
        }

        // Check for n consecutive symbols in descending diagonal.
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (canMove(i - (n - 1), j + (n - 1))) {
                    if (board[i][j] == board[i - 1][j + 1]
                            && board[i][j] == board[i - 2][j + 2]
                            && board[i][j] == board[i - 3][j + 3]
                            && board[i][j] == symbol) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void reset() {
        initializeBoard();
    }

    private boolean canMove(int row, int col) {
        if ((row <= -1) || (col <= -1) || (row >= HEIGHT) || (col >= WIDTH)) {
            return false;
        }
        return true;
    }
}
