package model;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {

    @Mock
    HumanPlayer mockHumanPlayer1, mockHumanPlayer2;

    @BeforeEach
    void resetModel() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = ConnectFourModel.class.getDeclaredField("connectFourModel");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void testMoveMethodPicsTheWinningMove() {
        Board board = new Board();
        ConnectFourModel model = ConnectFourModel.getInstance(board);
        ComputerPlayer computerPlayer = new ComputerPlayer("name", model, Player.Symbol.CROSS);
        model.startGame(mockHumanPlayer1, mockHumanPlayer2);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(0);
        model.columnSelected(1);
        model.columnSelected(2);
        computerPlayer.move();
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testMoveMethodPicsTheRandomMoveIfNoWinningMoveAvailable() {
        Board board = new Board();
        ConnectFourModel model = ConnectFourModel.getInstance(board);
        ComputerPlayer computerPlayer = new ComputerPlayer("name", model, Player.Symbol.CROSS);
        model.startGame(mockHumanPlayer1, computerPlayer);
        model.columnSelected(0);
        computerPlayer.move();

        boolean moveMade = board.getEntry(board.getHeight() - 2, 0) == 1;
        for (int i = 1; i < board.getWidth(); i++) {
            moveMade = moveMade || (board.getEntry(board.getHeight() - 1, i) == 1);
        }

        assertTrue(moveMade);
    }
}
