package edu.nyu.cs.pqs.assignment4;

public class TestUtils {
    public static Board board = new Board(7, 6);

    public static void fillTheColumnOfTheBoard(Board board, int column) throws IllegalArgumentException {
        for (int i=0;i<board.getNUM_OF_ROWS();i++) {
            board.columnSelected(column, 0);
        }
    }
}
