package model;

public class BoardInjector {
    private Board board;
    public BoardInjector () {
        board = new Board();
    }
    public ConnectFourModel getModel() {
        return ConnectFourModel.getInstance(board);
    }
}
