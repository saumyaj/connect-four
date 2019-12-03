package edu.nyu.cs.pqs.assignment4;

import java.util.Optional;
import java.util.Random;

class ComputerPlayer {
    private Board board;
    private Connect4 model;

    ComputerPlayer(Connect4 model, Board board) {
        this.board = board;
        this.model = model;
    }

    void move() {
        Optional<Integer> winningMove = checkForWinningMove();
        int c = winningMove.orElseGet(this::pickARandomMove);
        model.columnSelected(c);
    }

    private int pickARandomMove() {
        while(true) {
            int col = new Random().nextInt(board.getNUM_OF_COLUMNS());
            if (!board.isColumnFull(col)) {
                return col;
            }
        }
    }

    private Optional<Integer> checkForWinningMove() {
        for (int i=0;i<board.getNUM_OF_COLUMNS();i++) {
            if (!board.isColumnFull(i)) {
                try {
                    board.columnSelected(i, 1);
                    boolean isWinnerMove = board.findContiguous(4, 1);
                    board.removeMostRecentEntryFromColumn(i);
                    if (isWinnerMove) {
                        return Optional.of(i);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return Optional.empty();
    }

}
