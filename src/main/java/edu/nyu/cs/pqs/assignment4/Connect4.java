package edu.nyu.cs.pqs.assignment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connect4 {

    private List<GameListener> listeners;
    int turn;
    Board gameBoard;
    int NUMBER_OF_ROWS = 6;
    int NUMBER_OF_COLUMNS = 7;

    Connect4() {
        listeners = new ArrayList<GameListener>();
        gameBoard = new Board(NUMBER_OF_COLUMNS, NUMBER_OF_ROWS);
        this.turn = 0;
    }

    public void columnSelected(int column) {

        // Try to make a move by the current player
        try {
            gameBoard.columnSelected(column, turn);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Update view if the move was successful
        firePlayerMovedEvent(turn, column);

        // If the last used column is full then disable the button for that column
        if (gameBoard.isColumnFull(column)) {
            fireDisableColumnEvent(column);
        }

        // Check if the current player has won
        if (gameBoard.isGameOver(turn)) {
            firePlayerWonEvent(turn);
            return;
        }

        // If no player has won yet and if the board is full then the game it's a draw
        if (gameBoard.isBoardFull()) {
            fireGameDrawsEvent();
        }

        turn = 1 - turn;
    }

    private void firePlayerMovedEvent(int playerId, int column) {
        for (GameListener listener: listeners) {
            listener.playerMoved(playerId, column);
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

}
