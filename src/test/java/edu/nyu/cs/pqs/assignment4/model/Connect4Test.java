package edu.nyu.cs.pqs.assignment4.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Connect4Test {

    @Mock
    GameListener gameListenerMock1;

    @Mock
    Board gameBoardMock;

    @Mock
    GameListener gameListenerMock2;

    @BeforeEach
    void resetModel() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Connect4.class.getDeclaredField("connect4");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void testIllegalColumnSelectedThrowsException() {
        Connect4 model = Connect4.getInstance(gameBoardMock);
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
        Connect4 model = Connect4.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        doReturn(5).when(gameBoardMock).columnSelected(anyInt(), anyInt());

        model.columnSelected(0);
        verify(gameListenerMock1).playerMoved(0, 5, 0);
        verify(gameListenerMock2).playerMoved(0, 5, 0);

        model.columnSelected(1);
        verify(gameListenerMock1).playerMoved(1, 5, 1);
        verify(gameListenerMock2).playerMoved(1, 5, 1);
    }

    @Test
    void testGameStoppedRelayedToAllListenersSuccessfully() {
        Connect4 model = Connect4.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.stop();
        verify(gameListenerMock1).gameStopped();
        verify(gameListenerMock2).gameStopped();
    }

    @Test
    void testSinglePlayerGameStartedRelayedToAllListenersSuccessfully() {
        Connect4 model = Connect4.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.startGame(true);
        verify(gameListenerMock1).gameStarted();
        verify(gameListenerMock2).gameStarted();
    }

    @Test
    void testTwoPlayerGameStartedRelayedToAllListenersSuccessfully() {
        Connect4 model = Connect4.getInstance(gameBoardMock);
        model.addGameListener(gameListenerMock1);
        model.addGameListener(gameListenerMock2);

        model.startGame(false);
        verify(gameListenerMock1).gameStarted();
        verify(gameListenerMock2).gameStarted();
    }

    @Test
    void testWhenBoardIsFullGameDrawIsCalledForAllListeners() {
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
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
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);

        when(gameBoardMock.findContiguousSymbols(4, 0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).playerWon(0);
        verify(gameListenerMock2).playerWon(0);

    }

    @Test
    void testPlayer2WonIsCalledForAllListeners() {
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);

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

        verify(gameListenerMock1).playerWon(1);
        verify(gameListenerMock2).playerWon(1);
    }

    @Test
    void testColumnDisableFired() {
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);

        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).disableColumnButton(0);
        verify(gameListenerMock2).disableColumnButton(0);
    }

    @Test
    void testRemoveListener() {
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock2);

        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.removeGameListener(gameListenerMock2);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1).disableColumnButton(0);
        verify(gameListenerMock2, times(0)).disableColumnButton(0);
    }

    @Test
    void testListenerIsAddedOnlyOnce() {
        Connect4 modelWithMockBoard = Connect4.getInstance(gameBoardMock);
        modelWithMockBoard.addGameListener(gameListenerMock1);
        modelWithMockBoard.addGameListener(gameListenerMock1);

        when(gameBoardMock.isColumnFull(0)).thenReturn(true);

        modelWithMockBoard.columnSelected(0);

        verify(gameListenerMock1, times(1)).disableColumnButton(0);
    }

}
