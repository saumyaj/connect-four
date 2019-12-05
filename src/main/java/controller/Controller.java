package controller;

import model.*;

import java.util.List;

public class Controller {
    ConnectFourModel model;
    GamePlayersFactory gamePlayersFactory;

    public Controller(ConnectFourModel model, GamePlayersFactory gamePlayersFactory) {
        this.model = model;
        this.gamePlayersFactory = gamePlayersFactory;
    }

    public void startGame(String gameName) {
        List<Player> players = gamePlayersFactory.createPlayers(gameName, model);
        model.startGame(players.get(0), players.get(1));
    }

    public void columnSelected(int column) {
        model.columnSelected(column);
    }

    public void goToMainMenu() {
        model.stop();
    }

    public void restartGame() {
        model.resetGame();
    }
}
