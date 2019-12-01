package edu.nyu.cs.pqs.assignment4;

import java.util.*;

public class Connect4 {

    private List<GameListener> listeners;
    private int turn;
    private Board gameBoard;
    private int NUMBER_OF_ROWS = 6;
    private int NUMBER_OF_COLUMNS = 7;
    private int WIN_LIMIT = 4;

    private boolean isSinglePlayerGame = false;
    private static Connect4 connect4;

    public static Connect4 getInstance() {
        if (connect4 == null) {
            connect4 = new Connect4();
        }
        return connect4;
    }

    private Connect4() {
        listeners = new ArrayList<>();
        gameBoard = new Board(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
        this.turn = 0;
    }

    public void startGame(boolean gameType) {
        isSinglePlayerGame = gameType;
        fireGameStartedEvent();
    }

    public void columnSelected(int column) {

        // Try to make a move by the current player
        int row = -1;
        try {
            row = gameBoard.columnSelected(column, turn);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Update view if the move was successful
        firePlayerMovedEvent(turn, row, column);

        // If the last used column is full then disable the button for that column
        if (gameBoard.isColumnFull(column)) {
            fireDisableColumnEvent(column);
        }

        // Check if the current player has won
        if (gameBoard.findContiguous(WIN_LIMIT, turn)) {
            firePlayerWonEvent(turn);
            return;
        }

        // If no player has won yet and if the board is full then the game it's a draw
        if (gameBoard.isBoardFull()) {
            fireGameDrawsEvent();
        }

        turn = 1 - turn;

        if (isSinglePlayerGame && turn == 1) {
            int computerMove = findNextMoveForComputer();
            columnSelected(computerMove);
        }
    }

    int findNextMoveForComputer() {
        Optional<Integer> winningMove = checkForWinningMove();
        if (winningMove.isPresent()) {
            return winningMove.get();
        }
        return pickARandomMove();
    }

    int pickARandomMove() {
        while(true) {
            int col = new Random().nextInt(NUMBER_OF_COLUMNS);
            if (!gameBoard.isColumnFull(col)) {
                return col;
            }
        }
    }

    public void restartGame() {
        gameBoard.reset();
        turn = 0;
        fireRestartGameEvent();
    }

    Optional<Integer> checkForWinningMove() {
        for (int i=0;i<NUMBER_OF_COLUMNS;i++) {
            if (!gameBoard.isColumnFull(i)) {
                try {
                    gameBoard.columnSelected(i, turn);
                    boolean isWinnerMove = gameBoard.findContiguous(WIN_LIMIT, turn);
                    gameBoard.removeMostRecentEntryFromColumn(i);
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

    private void firePlayerMovedEvent(int playerId, int row, int column) {
        for (GameListener listener: listeners) {
            listener.playerMoved(playerId, row, column);
        }
    }

    private void fireGameDrawsEvent() {
        for (GameListener listener: listeners) {
            listener.gameDraw();
        }
    }

    private void firePlayerWonEvent(int playerId) {
        for (GameListener listener: listeners) {
            listener.playerWon(playerId);
        }
    }

    private void fireDisableColumnEvent(int column) {
        for (GameListener listener: listeners) {
            listener.disableColumn(column);
        }
    }

    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    private void fireRestartGameEvent() {
        for (GameListener listener: listeners) {
            listener.resetGame();
        }
    }

    private void fireGameStartedEvent() {
        for (GameListener listener: listeners) {
            listener.gameStarted();
        }
    }

}
