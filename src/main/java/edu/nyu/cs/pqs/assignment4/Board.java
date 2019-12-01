package edu.nyu.cs.pqs.assignment4;

import java.util.Arrays;

public class Board {
    int[][] board;
    int rows;
    int cols;
    Board(int cols, int rows) {
        this.rows = rows;
        this.cols = cols;
        initializeBoard(cols, rows);
    }

    boolean isColumnFull(int col) {
        return board[0][col]!=-1;
    }

    public int columnSelected(int col, int turn) throws Exception {
        if (isColumnFull(col)) {
            throw new Exception("invalid column selection; this column is full");
        }
        int row = nextEmptyRow(col);
//        System.out.println(row);
//        System.out.println(turn);
        board[row][col] = turn;
        return row;
    }

    public void removeMostRecentEntryFromColumn(int col) {
        for (int i=0;i<rows;i++) {
            if (board[i][col]!=-1) {
                board[i][col] = -1;
                break;
            }
        }
    }

    private int nextEmptyRow(int col) {
        System.out.println(col);
        for(int i=this.rows-1;i>=0;i--) {
            if (board[i][col]==-1) {
//                System.out.println(i);
                return i;
            }
        }
        return -1;
    }

    boolean isBoardFull() {
        for(int j=0;j<cols;j++) {
            if (board[0][j] == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver(int turn) {

        // Check if 4 in a row
        for(int row=0; row<rows; row++) {
            for(int col=0; col<cols; col++) {
                if (board[row][col]==turn && col+3<cols) {
                    boolean flag = true;
                    for (int k=1;k<4;k++) {
                        if (board[row][col + k] != turn) {
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

        // Check if 4 in a col
        for(int col=0; col<cols; col++) {
            for(int row=0; row<rows; row++) {
                if (board[row][col]==turn && row+3<rows) {
                    boolean flag = true;
                    for (int k=1;k<4;k++) {
                        if (board[row+k][col] != turn) {
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

        // Check for 4 consecutive checkers in a row, in descending diagonal.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canMove(i + 3, j + 3)) {
                    if (board[i][j] == board[i + 1][j + 1]
                            && board[i][j] == board[i + 2][j + 2]
                            && board[i][j] == board[i + 3][j + 3]
                            && board[i][j] == turn) {
                        return true;
                    }
                }
            }
        }

        // Check for 3 consecutive checkers in a row, in ascending diagonal.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canMove(i - 3, j + 3)) {
                    if (board[i][j] == board[i - 1][j + 1]
                            && board[i][j] == board[i - 2][j + 2]
                            && board[i][j] == board[i - 3][j + 3]
                            && board[i][j] == turn) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    void reset() {
        initializeBoard(cols, rows);
    }

    private void initializeBoard(int cols, int rows) {
        this.board = new int[rows][cols];
        for(int i=0;i<rows;i++) {
            Arrays.fill(this.board[i], -1);
        }
    }

    private boolean canMove(int row, int col) {
        if ((row <= -1) || (col <= -1) || (row >= rows) || (col >= cols)) {
            return false;
        }
        return true;
    }

    public void print() {
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                if (j!=6) {
                    if (board[i][j] == 1) {
                        System.out.print("| " + "X" + " ");
                    } else if (board[i][j] == 0) {
                        System.out.print("| " + "O" + " ");
                    } else {
                        System.out.print("| " + "-" + " ");
                    }
                } else {
                    if (board[i][j] == 1) {
                        System.out.println("| " + "X" + " |");
                    } else if (board[i][j] == 0) {
                        System.out.println("| " + "O" + " |");
                    } else {
                        System.out.println("| " + "-" + " |");
                    }
                }
            }
        }
        return;
    }
}
