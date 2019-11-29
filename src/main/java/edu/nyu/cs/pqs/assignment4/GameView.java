package edu.nyu.cs.pqs.assignment4;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.*;

public class GameView implements GameListener {

    private Connect4 game;

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";

    GameView(Connect4 game) {
        this.game = game;
        game.addGameListener(this);
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
//        JToolBar tools = new JToolBar();
//        tools.setFloatable(false);
//        gui.add(tools, BorderLayout.PAGE_START);
//        tools.add(new JButton("New")); // TODO - add functionality!
//        tools.add(new JButton("Save")); // TODO - add functionality!
//        tools.add(new JButton("Restore")); // TODO - add functionality!
//        tools.addSeparator();
//        tools.add(new JButton("Resign")); // TODO - add functionality!
//        tools.addSeparator();
//        tools.add(message);

//        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 7));
//        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        // create the chess board squares
        Insets buttonMargin = new Insets(0,5,0,5);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                final JButton b = new JButton();
                b.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        // display/center the jdialog when the button is pressed
                        b.setBackground(Color.RED);
                        System.out.println("changed");
                        Border thickBorder = new LineBorder(Color.WHITE, 12);
                        b.setBorder(thickBorder);
                        b.setEnabled(false);
                    }
                });
                b.setBorderPainted (false);
                b.setBackground(Color.BLACK);
                b.setOpaque(true);
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.black);
                    b.setForeground(Color.black);
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

    void columnButtonPressed(int column) {
        game.columnSelected(column);
    }

    public void playerMoved(int playerId, int column) {

    }

    public void playerWon(int playerId) {

    }
}
