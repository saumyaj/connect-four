package controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import view.GameView;
import model.ComputerPlayer;
import model.ConnectFourModel;
import model.HumanPlayer;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    GameView mockGameView;

    @Mock
    ConnectFourModel mockModel;

    private Controller controller;

    @Test
    public void testSinglePlayerGameStarted() {
        controller = new Controller(mockModel);
        controller.startGame(true);
        verify(mockModel).startGame(any(HumanPlayer.class), any(ComputerPlayer.class)) ;
    }

    @Test
    public void testTwoPlayerGameStarted() {
        controller = new Controller(mockModel);
        controller.startGame(false);
        verify(mockModel).startGame(any(HumanPlayer.class), any(HumanPlayer.class)) ;
    }

    @Test
    public void testColumnSelected() {
        controller = new Controller(mockModel);
        controller.columnSelected(1);
        verify(mockModel).columnSelected(1); ;
    }

    @Test
    public void testRestartGame() {
        controller = new Controller(mockModel);
        controller.restartGame();
        verify(mockModel).restartGame(); ;
    }

    @Test
    public void testGoToMainMenu() {
        controller = new Controller(mockModel);
        controller.goToMainMenu();
        verify(mockModel).stop(); ;
    }
}
