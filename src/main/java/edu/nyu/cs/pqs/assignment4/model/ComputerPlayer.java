package edu.nyu.cs.pqs.assignment4.model;

import java.util.Optional;
import java.util.Random;

class ComputerPlayer {
    private Board board;
    private Connect4 model;

    ComputerPlayer(Connect4 model) {
        this.model = model;
    }

    void move() {
        board = model.getCurrentState();
        Optional<Integer> winningMove = checkForWinningMove();
        int c = winningMove.orElseGet(this::pickARandomMove);
        model.columnSelected(c);
    }

    private int pickARandomMove() {
        while(true) {
            int col = new Random().nextInt(board.getWidth());
            if (!board.isColumnFull(col)) {
                return col;
            }
        }
    }

    private Optional<Integer> checkForWinningMove() {
        for (int i = 0; i<board.getWidth(); i++) {
            if (!board.isColumnFull(i)) {
                board.columnSelected(i, 1);
                boolean isWinnerMove = board.findContiguousSymbols(4, 1);
                board.removeMostRecentEntryFromColumn(i);
                if (isWinnerMove) {
                    return Optional.of(i);
                }
            }
        }
        return Optional.empty();
    }
}
