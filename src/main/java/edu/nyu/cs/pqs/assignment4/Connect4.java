package edu.nyu.cs.pqs.assignment4;

import java.util.*;

class Connect4 {

    private List<GameListener> listeners;
    private int turn;
    private Board gameBoard;

    private boolean isSinglePlayerGame = false;
    private static Connect4 connect4;
    private ComputerPlayer computerPlayer;

    static Connect4 getInstance() {
        if (connect4 == null) {
            connect4 = new Connect4();
        }
        return connect4;
    }

    private Connect4() {
        listeners = new ArrayList<>();
        int NUMBER_OF_ROWS = 6;
        int NUMBER_OF_COLUMNS = 7;
        gameBoard = new Board(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
        this.turn = 0;
    }

    void startGame(boolean gameType) {
        isSinglePlayerGame = gameType;
        if (isSinglePlayerGame) {
            computerPlayer = new ComputerPlayer(this, gameBoard);
        }
        fireGameStartedEvent();
    }

    void columnSelected(int column) {

        // Try to make a move by the current player
        try {
            int row = gameBoard.columnSelected(column, turn);
            // Update view if the move was successful
            firePlayerMovedEvent(turn, row, column);

            // If the last used column is full then disable the button for that column
            if (gameBoard.isColumnFull(column)) {
                fireDisableColumnEvent(column);
            }

            // Check if the current player has won
            int WIN_LIMIT = 4;
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
                computerPlayer.move();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void restartGame() {
        gameBoard.reset();
        turn = 0;
        fireRestartGameEvent();
    }

    void stop() {
        restartGame();
        fireGameStoppedEvent();
    }

    private void fireGameStoppedEvent() {
        for (GameListener listener : listeners) {
            listener.gameStopped();
        }
    }

    private void firePlayerMovedEvent(int playerId, int row, int column) {
        for (GameListener listener : listeners) {
            listener.playerMoved(playerId, row, column);
        }
    }

    private void fireGameDrawsEvent() {
        for (GameListener listener : listeners) {
            listener.gameDraw();
        }
    }

    private void firePlayerWonEvent(int playerId) {
        for (GameListener listener : listeners) {
            listener.playerWon(playerId);
        }
    }

    private void fireDisableColumnEvent(int column) {
        for (GameListener listener : listeners) {
            listener.disableColumnButton(column);
        }
    }

    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    private void fireRestartGameEvent() {
        for (GameListener listener : listeners) {
            listener.resetGame();
        }
    }

    private void fireGameStartedEvent() {
        for (GameListener listener : listeners) {
            listener.gameStarted();
        }
    }

}
