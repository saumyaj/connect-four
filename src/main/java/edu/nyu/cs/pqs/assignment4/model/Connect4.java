package edu.nyu.cs.pqs.assignment4.model;

import java.util.*;

public class Connect4 {

    private List<GameListener> listeners;
    private int turn;
    private final int GAME_BOARD_HEIGHT = 6;
    private final int GAME_BOARD_WIDTH = 7;
    private final int WIN_LIMIT = 4;
    private Board gameBoard;

    private boolean isSinglePlayerGame = false;
    private static Connect4 connect4;
    private ComputerPlayer computerPlayer;

    public static Connect4 getInstance(Board board) {
        if (connect4 == null)
            connect4 = new Connect4(board);
        return connect4;
    }

    private Connect4(Board gameBoard) {
        listeners = new ArrayList<>();
        this.gameBoard = gameBoard;
        this.turn = 0;
    }

    /**
     * Adds the listener (observers) to the listeners list. The registered listeners are notified when the state of
     * the model is updated
     * @param listener A listener object that implements the GameListener interface
     */
    public void addGameListener(GameListener listener) {
        if (listeners.contains(listener))
            return;
        listeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
    }

    /**
     * This method initiates the game model
     *
     * @param gameType This is a boolean parameter indicating if the game is single player game
     */
    public void startGame(boolean gameType) {
        isSinglePlayerGame = gameType;
        if (isSinglePlayerGame) {
            computerPlayer = new ComputerPlayer(this);
        }
        fireGameStartedEvent();
    }

    /**
     * This method processes the column selection by a player in the ongoing game
     *
     * @param column A 0 based column index where the current player wants to move
     * @throws IllegalArgumentException If an invalid parameter is passed
     */
    public void columnSelected(int column) throws IllegalArgumentException {
        // Try to make a move by the current player
        int row = gameBoard.columnSelected(column, turn);

        // Update view if the move was successful
        firePlayerMovedEvent(turn, row, column);

        // If the last used column is full then disable the button for that column
        if (gameBoard.isColumnFull(column)) {
            fireDisableColumnEvent(column);
        }

        // Check if the current player has won
        if (gameBoard.findContiguousSymbols(WIN_LIMIT, turn)) {
            firePlayerWonEvent(turn);
            return;
        }

        // If no player has won yet and if the board is full then the game it's a draw
        if (gameBoard.isBoardFull()) {
            fireGameDrawsEvent();
            return;
        }

        turn = 1 - turn;

        if (isSinglePlayerGame && turn == 1) {
            computerPlayer.move();
        }
    }

    Board getCurrentState() {
        return new Board(gameBoard);
    }

    /**
     * This method resets the game to its initial state and gives the chance to move to player 1
     */
    public void resetGame() {
        gameBoard.reset();
        turn = 0;
        fireRestartGameEvent();
    }

    /**
     * This method stops the game
     */
    public void stop() {
        resetGame();
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
