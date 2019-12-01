package edu.nyu.cs.pqs.assignment4;

public class Connect4App {
    public static void main(String[] args) {

        Connect4 game = new Connect4();
        new GameView(game);
        new GameView(game);
    }
}
