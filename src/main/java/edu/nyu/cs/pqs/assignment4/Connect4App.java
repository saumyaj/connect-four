package edu.nyu.cs.pqs.assignment4;

public class Connect4App {
    public static void main(String[] args) {

        Connect4 game = new Connect4();
        new GameView(game);
//        for (int i=0;i<4;i++) {
//            try {
//                connect4.columnSelected(1, 0);
//                if (connect4.checkIfGameOver()) {
//                    System.out.println("player 1 wins");
//                    break;
//                }
//                connect4.columnSelected(2, 1);
//                if (connect4.checkIfGameOver()) {
//                    System.out.println("player 2 wins");
//                    break;
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
    }
}
