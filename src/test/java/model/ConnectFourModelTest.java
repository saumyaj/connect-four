package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConnectFourModelTest {

    @Mock
    GameListener gameListenerMock1, gameListenerMock2;

    @Mock
    Board gameBoardMock;

    @Mock
    ComputerPlayer cp1;

    @Mock
    HumanPlayer hp1, hp2;

    @BeforeEach
    void resetModel() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = ConnectFourModel.class.getDeclaredField("connectFourModel");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void testIllegalColumnSelectedThrowsException() {
        ConnectFourModel model = ConnectFourModel.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);

        doThrow(IllegalArgumentException.class)
                .when(gameBoardMock)
                .columnSelected(anyInt(), anyInt());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            model.columnSelected(-1);
        });
    }

    @Test
    void testPlayerMovesAreRelayedToTheListenersCorrectly() {
        ConnectFourModel model = ConnectFourModel.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);
        model.startGame(hp1, hp2);

        doReturn(5).when(gameBoardMock).columnSelected(anyInt(), anyInt());

        model.columnSelected(0);
        verify(gameListenerMock1).playerMoved(hp1, hp2, 5, 0);
        verify(gameListenerMock2).playerMoved(hp1, hp2, 5, 0);

        model.columnSelected(1);
        verify(gameListenerMock1).playerMoved(hp2, hp1, 5, 1);
        verify(gameListenerMock2).playerMoved(hp2, hp1, 5, 1);
    }

    @Test
    void testGameStoppedRelayedToAllListenersSuccessfully() {
        ConnectFourModel model = ConnectFourModel.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.stop();
        verify(gameListenerMock1).gameStopped();
        verify(gameListenerMock2).gameStopped();
    }

    @Test
    void testSinglePlayerGameStartedRelayedToAllListenersSuccessfully() {
        ConnectFourModel model = ConnectFourModel.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.startGame(hp1, cp1);
        verify(gameListenerMock1).gameStarted(hp1);
        verify(gameListenerMock2).gameStarted(hp1);
    }

    @Test
    void testTwoPlayerGameStartedRelayedToAllListenersSuccessfully() {
        ConnectFourModel model = ConnectFourModel.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.startGame(hp1, hp2);
        verify(gameListenerMock1).gameStarted(hp1);
        verify(gameListenerMock2).gameStarted(hp1);
    }

    @Test
    void testWhenBoardIsFullGameDrawIsCalledForAllListeners() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);

        when(gameBoardMock.isBoardFull()).thenReturn(true);
        when(gameBoardMock.isColumnFull(anyInt())).thenReturn(false);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).gameDraw();
        verify(gameListenerMock2).gameDraw();

    }

    @Test
    void testPlayer1WonIsCalledForAllListeners() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);
        modelWithMockBoard.startGame(hp1, hp2);

        when(gameBoardMock.findContiguousSymbols(4, 0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).playerWon(hp1);
        verify(gameListenerMock2).playerWon(hp1);

    }

    @Test
    void testPlayer2WonIsCalledForAllListeners() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);
        modelWithMockBoard.startGame(hp1, hp2);

        Mockito.when(gameBoardMock.findContiguousSymbols(Mockito.any(Integer.class), Mockito.any(Integer.class)))
                .thenAnswer((Answer) invocation -> {
                    Object[] args = invocation.getArguments();
                    if ((Integer) args[1] == 1) {
                        return true;
                    }
                    return false;
                });

        modelWithMockBoard.columnSelected(0);
        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).playerWon(hp2);
        verify(gameListenerMock2).playerWon(hp2);
    }

    @Test
    void testColumnDisableFired() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);
        modelWithMockBoard.startGame(hp1, hp2);
        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).disableColumnButton(0);
        verify(gameListenerMock2).disableColumnButton(0);
    }

    @Test
    void testRemoveListener() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);
        modelWithMockBoard.startGame(hp1, hp2);
        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.removeGameListener(gameListenerMock2);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).disableColumnButton(0);
        verify(gameListenerMock2, times(0)).disableColumnButton(0);
    }

    @Test
    void testListenerIsAddedOnlyOnce() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.startGame(hp1, hp2);
        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1, times(1)).disableColumnButton(0);
    }

    @Test
    void testRestartGameResetsTheBoardSuccessfully() {
        ConnectFourModel modelWithMockBoard = ConnectFourModel.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);
        modelWithMockBoard.startGame(hp1, hp2);

//        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        doNothing().when(gameBoardMock).reset();

        modelWithMockBoard.restartGame();

        verify(gameListenerMock1, times(1)).resetGame(hp1);
        verify(gameListenerMock2, times(1)).resetGame(hp1);
    }

}
