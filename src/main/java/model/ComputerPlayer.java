package model;

import java.util.Optional;
import java.util.Random;

public class ComputerPlayer extends Player {
    private Board board;
    private Random randomGenerator;

    ComputerPlayer(String name, ConnectFourModel model, Symbol symbol) {
        super(name, model, symbol);
        randomGenerator = new Random();
    }

    @Override
    public void move() {
        board = gameModel.getCurrentState();
        Optional<Integer> winningMove = checkForWinningMove();
        int c = winningMove.orElseGet(this::pickARandomMove);
        gameModel.columnSelected(c);
    }

    private int pickARandomMove() {
        while (true) {
            int col = randomGenerator.nextInt(board.getWidth());
            if (!board.isColumnFull(col)) {
                return col;
            }
        }
    }

    private Optional<Integer> checkForWinningMove() {
        for (int i = 0; i < board.getWidth(); i++) {
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
