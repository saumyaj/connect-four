package model;

public interface GameListener {

    void playerMoved(Player currentPlayer, Player otherPlayer, int row, int column);

    void playerWon(Player player);

    void gameDraw();

    void disableColumnButton(int column);

    void resetGame(Player firstPlayer);

    void gameStarted(Player firstPlayer);

    void gameStopped();

}
