package model;

import java.util.*;

public class ConnectFourModel {

    private List<GameListener> listeners;
    private Player[] players;
    private int turn;
    private final int WIN_LIMIT = 4;
    private Board gameBoard;
    private static final int NUM_OF_PLAYERS = 2;

    private static ConnectFourModel connectFourModel;

    public static ConnectFourModel getInstance(Board board) {
        if (connectFourModel == null)
            connectFourModel = new ConnectFourModel(board);
        return connectFourModel;
    }

    private ConnectFourModel(Board gameBoard) {
        listeners = new ArrayList<>();
        players = new Player[NUM_OF_PLAYERS];
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
     * @param p1 - first player in the game. This players gets first move
     * @param p2 - second player in the game. This player moves after the first player
     */
    public void startGame(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
        restartGame();
        fireGameStartedEvent(p1);
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
        firePlayerMovedEvent(players[turn], players[1 - turn], row, column);

        // If the last used column is full then disable the button for that column
        if (gameBoard.isColumnFull(column)) {
            fireDisableColumnEvent(column);
        }

        // Check if the current player has won
        if (gameBoard.findContiguousSymbols(WIN_LIMIT, turn)) {
            firePlayerWonEvent(players[turn]);
            return;
        }

        // If no player has won yet and if the board is full then the game it's a draw
        if (gameBoard.isBoardFull()) {
            fireGameDrawsEvent();
            return;
        }

        turn = 1 - turn;

        players[turn].move();
    }

    Board getCurrentState() {
        return new Board(gameBoard);
    }

    /**
     * This method resets the game to its initial state and gives the chance to move to player 1
     */
    public void restartGame() {
        gameBoard.reset();
        turn = 0;
        fireRestartGameEvent(players[0]);
    }

    /**
     * This method stops the game
     */
    public void stop() {
        gameBoard.reset();
        turn = 0;
        fireGameStoppedEvent();
    }

    private void fireGameStoppedEvent() {
        for (GameListener listener : listeners) {
            listener.gameStopped();
        }
    }

    private void firePlayerMovedEvent(Player currentPlayer, Player nextPlayer, int row, int column) {
        for (GameListener listener : listeners) {
            listener.playerMoved(currentPlayer, nextPlayer, row, column);
        }
    }

    private void fireGameDrawsEvent() {
        for (GameListener listener : listeners) {
            listener.gameDraw();
        }
    }

    private void firePlayerWonEvent(Player player) {
        for (GameListener listener : listeners) {
            listener.playerWon(player);
        }
    }

    private void fireDisableColumnEvent(int column) {
        for (GameListener listener : listeners) {
            listener.disableColumnButton(column);
        }
    }

    private void fireRestartGameEvent(Player firstPlayer) {
        for (GameListener listener : listeners) {
            listener.resetGame(firstPlayer);
        }
    }

    private void fireGameStartedEvent(Player firstPlayer) {
        for (GameListener listener : listeners) {
            listener.gameStarted(firstPlayer);
        }
    }

}
