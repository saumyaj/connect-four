package edu.nyu.cs.pqs.assignment4.model;

public interface GameListener {

    void playerMoved(int playerId, int row, int column);
    void playerWon(int playerId);
    void gameDraw();
    void disableColumnButton(int column);
    void resetGame();
    void gameStarted();
    void gameStopped();

}
