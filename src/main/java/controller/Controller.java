package controller;

import model.ComputerPlayer;
import model.ConnectFourModel;
import model.HumanPlayer;
import model.Player;
import view.GameView;

public class Controller {
    ConnectFourModel model;
    GameView view;

    public Controller(ConnectFourModel model) {
        this.model = model;
//        view = new GameView(this, model);
    }

    public void startGame(boolean isSinglePlayer) {
        Player p1 = new HumanPlayer("Player1", model, Player.Symbol.CIRCLE);
        Player p2;
        if (isSinglePlayer)
            p2 = new ComputerPlayer("Computer", model, Player.Symbol.CROSS);
        else {
            p2 = new HumanPlayer("Player2", model, Player.Symbol.CROSS);
        }
        model.startGame(p1, p2);
    }

    public void columnSelected(int column) {
        model.columnSelected(column);
    }

    public void goToMainMenu() {
        model.stop();
    }

    public void restartGame() {
        model.restartGame();
    }
}
