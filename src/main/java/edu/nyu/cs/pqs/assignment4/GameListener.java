package edu.nyu.cs.pqs.assignment4;

public interface GameListener {

    void playerMoved(int playerId, int row, int column);
    void playerWon(int playerId);
    void gameDraw();
    void disableColumn(int column);
    void resetGame();
    void gameStarted();

}
