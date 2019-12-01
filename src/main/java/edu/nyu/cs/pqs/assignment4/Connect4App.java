package edu.nyu.cs.pqs.assignment4;

public class Connect4App {
    public static void main(String[] args) {
        Connect4 game = Connect4.getInstance();
        new GameView(game);
    }
}
