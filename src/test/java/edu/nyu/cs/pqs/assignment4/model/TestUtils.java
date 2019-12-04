package edu.nyu.cs.pqs.assignment4.model;

public class TestUtils {
    public static Board board = new Board(7, 6);

    public static void fillTheColumnOfTheBoard(Board board, int column) throws IllegalArgumentException {
        for (int i = 0; i<board.getHeight(); i++) {
            board.columnSelected(column, 0);
        }
    }
}
