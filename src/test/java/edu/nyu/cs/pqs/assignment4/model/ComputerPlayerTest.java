package edu.nyu.cs.pqs.assignment4.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComputerPlayerTest {

    @BeforeEach
    void resetModel() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Connect4.class.getDeclaredField("connect4");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void testMoveMethodPicsTheWinningMove() {
        Board board = new Board(7, 6);
        Connect4 model = Connect4.getInstance(board);
        ComputerPlayer player = new ComputerPlayer(model);
        model.startGame(false);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(2);
        player.move();
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testMoveMethodPicsTheRandomMoveIfNoWinningMoveAvailable() {
        Board board = new Board(7, 6);
        Connect4 model = Connect4.getInstance(board);
        ComputerPlayer player = new ComputerPlayer(model);
        model.startGame(false);
        model.columnSelected(0);
        player.move();

        boolean moveMade = board.getEntry(board.getHeight() - 2, 0) == 1;
        for (int i = 1; i < board.getWidth(); i++) {
            moveMade = moveMade || (board.getEntry(board.getHeight() - 1, i) == 1);
        }

        assertTrue(moveMade);
    }
}
