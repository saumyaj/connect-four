package app;

import controller.Controller;
import view.GameView;
import model.BoardInjector;
import model.ConnectFourModel;

public class Connect4App {
    public static void main(String[] args) {
        BoardInjector injector = new BoardInjector();
        ConnectFourModel gameModel = injector.getModel();
        Controller controller = new Controller(gameModel);
        new GameView(controller, gameModel);
        new GameView(controller, gameModel);
    }
}
