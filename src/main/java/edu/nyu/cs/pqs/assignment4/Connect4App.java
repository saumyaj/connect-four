package edu.nyu.cs.pqs.assignment4;

import edu.nyu.cs.pqs.assignment4.model.BoardInjector;
import edu.nyu.cs.pqs.assignment4.model.Connect4;
import edu.nyu.cs.pqs.assignment4.view.GameView;

public class Connect4App {
    public static void main(String[] args) {
        BoardInjector injector = new BoardInjector();
        Connect4 game = injector.getModel();
        new GameView(game);
    }
}
