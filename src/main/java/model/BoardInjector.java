package model;

/**
 * This is a dependency injector class which injects board instance to the object of ConnectFourModel class
 */
public class BoardInjector {
    private Board board;

    public BoardInjector() {
        board = new Board();
    }

    public ConnectFourModel getModel() {
        return ConnectFourModel.getInstance(board);
    }
}
