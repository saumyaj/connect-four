package controller;

import model.GamePlayersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import model.ComputerPlayer;
import model.ConnectFourModel;
import model.HumanPlayer;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    ConnectFourModel mockModel;

    private Controller controller;

    @BeforeEach
    void initController() {
        controller = new Controller(mockModel, GamePlayersFactory.getInstance());
    }

    @Test
    public void testSinglePlayerGameStarted() {

        controller.startGame("SINGLE_PLAYER_GAME");
        verify(mockModel).startGame(any(HumanPlayer.class), any(ComputerPlayer.class));
    }

    @Test
    public void testTwoPlayerGameStarted() {
        controller.startGame("TWO_PLAYER_GAME");
        verify(mockModel).startGame(any(HumanPlayer.class), any(HumanPlayer.class));
    }

    @Test
    public void testColumnSelected() {
        controller.columnSelected(1);
        verify(mockModel).columnSelected(1);
        ;
    }

    @Test
    public void testRestartGame() {
        controller.restartGame();
        verify(mockModel).resetGame();
        ;
    }

    @Test
    public void testGoToMainMenu() {
        controller.goToMainMenu();
        verify(mockModel).stop();
        ;
    }
}
