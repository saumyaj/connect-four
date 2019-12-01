package edu.nyu.cs.pqs.assignment4;

import java.util.Arrays;

public class Board {
    private int[][] board;
    private final int NUM_OF_ROWS;

    public int getNUM_OF_ROWS() {
        return NUM_OF_ROWS;
    }

    public int getNUM_OF_COLUMNS() {
        return NUM_OF_COLUMNS;
    }

    private final int NUM_OF_COLUMNS;

    Board(int NUM_OF_COLUMNS, int NUM_OF_ROWS) {
        this.NUM_OF_ROWS = NUM_OF_ROWS;
        this.NUM_OF_COLUMNS = NUM_OF_COLUMNS;
        initializeBoard(NUM_OF_COLUMNS, NUM_OF_ROWS);
    }

    private void initializeBoard(int cols, int rows) {
        this.board = new int[rows][cols];
        for(int i=0;i<rows;i++) {
            Arrays.fill(this.board[i], -1);
        }
    }

    boolean isColumnFull(int col) {
        return board[0][col]!=-1;
    }

    int columnSelected(int col, int turn) throws IllegalArgumentException {
        if (col < 0 || col >= NUM_OF_COLUMNS) {
            throw new IllegalArgumentException("invalid column value; column value should be between 0 and " +
                    NUM_OF_COLUMNS);
        }
        if (isColumnFull(col)) {
            throw new IllegalArgumentException("invalid column selection; column " + col + " is full");
        }
        int row = nextEmptyRow(col);
        board[row][col] = turn;
        return row;
    }

    void removeMostRecentEntryFromColumn(int col) {
        for (int i = 0; i< NUM_OF_ROWS; i++) {
            if (board[i][col]!=-1) {
                board[i][col] = -1;
                break;
            }
        }
    }

    private int nextEmptyRow(int col) {
        for(int i = this.NUM_OF_ROWS -1; i>=0; i--) {
            if (board[i][col]==-1) {
                return i;
            }
        }
        return -1;
    }

    boolean isBoardFull() {
        for(int j = 0; j< NUM_OF_COLUMNS; j++) {
            if (board[0][j] == -1) {
                return false;
            }
        }
        return true;
    }

    boolean findContiguous(int n, int symbol) {

        // Check if n in a row
        for(int row = 0; row< NUM_OF_ROWS; row++) {
            for(int col = 0; col< NUM_OF_COLUMNS; col++) {
                if (board[row][col]==symbol && col+(n-1)< NUM_OF_COLUMNS) {
                    boolean flag = true;
                    for (int k=1;k<n;k++) {
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
        for(int col = 0; col< NUM_OF_COLUMNS; col++) {
            for(int row = 0; row< NUM_OF_ROWS; row++) {
                if (board[row][col]==symbol && row+(n-1)< NUM_OF_ROWS) {
                    boolean flag = true;
                    for (int k=1;k<n;k++) {
                        if (board[row+k][col] != symbol) {
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
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                if (canMove(i + (n-1), j + (n-1))) {
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
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                if (canMove(i - (n-1), j + (n-1))) {
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
        initializeBoard(NUM_OF_COLUMNS, NUM_OF_ROWS);
    }

    private boolean canMove(int row, int col) {
        if ((row <= -1) || (col <= -1) || (row >= NUM_OF_ROWS) || (col >= NUM_OF_COLUMNS)) {
            return false;
        }
        return true;
    }

//    public void print() {
//        for (int i = 0; i< NUM_OF_ROWS; i++) {
//            for (int j = 0; j< NUM_OF_COLUMNS; j++) {
//                if (j!=6) {
//                    if (board[i][j] == 1) {
//                        System.out.print("| " + "X" + " ");
//                    } else if (board[i][j] == 0) {
//                        System.out.print("| " + "O" + " ");
//                    } else {
//                        System.out.print("| " + "-" + " ");
//                    }
//                } else {
//                    if (board[i][j] == 1) {
//                        System.out.println("| " + "X" + " |");
//                    } else if (board[i][j] == 0) {
//                        System.out.println("| " + "O" + " |");
//                    } else {
//                        System.out.println("| " + "-" + " |");
//                    }
//                }
//            }
//        }
//        return;
//    }
}
