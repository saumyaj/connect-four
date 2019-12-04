package edu.nyu.cs.pqs.assignment4.view;

import edu.nyu.cs.pqs.assignment4.model.Connect4;
import edu.nyu.cs.pqs.assignment4.model.GameListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView implements GameListener {

    private Connect4 game;
    private int NUMBER_OF_COLUMNS = 7, NUMBER_OF_ROWS = 6;
    private JLabel gameStatusLabel;
    private String gameStatusMessage;
    private JFrame frame;
    private JPanel gamePanel, menuPanel;
    private JButton[] columnButtons;
    private JButton[][] gameBoardSquares;

    public GameView(Connect4 game) {
        this.game = game;
        game.addGameListener(this);
        gameBoardSquares = new JButton[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        initializeGui();
    }

    public void initializeGui() {

        // Setting up frame
        String FRAME_TITLE = "Connect4";
        int FRAME_WIDTH = 600;
        int FRAME_HEIGHT = 600;
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating menu page
        menuPanel = createMenuPanel();

        // Creating game page
        gamePanel = setupGamePanel();

        // Adding initial screen layout to frame
        frame.getContentPane().add(menuPanel);

        frame.setVisible(true);
    }

    private void initializeGameBoardSquares() {
        Insets buttonMargin = new Insets(0, 5, 0, 5);
        for (int ii = 0; ii < gameBoardSquares.length; ii++) {
            for (int jj = 0; jj < gameBoardSquares[ii].length; jj++) {
                final JButton b = new JButton();
                b.setEnabled(false);
                b.setBorderPainted(false);
                b.setBackground(Color.BLACK);
                b.setOpaque(true);
                b.setMargin(buttonMargin);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                gameBoardSquares[ii][jj] = b;
            }
        }
    }

    private void resetGameBoardSquares() {
        JButton b;
        for (int ii = 0; ii < gameBoardSquares.length; ii++) {
            for (int jj = 0; jj < gameBoardSquares[ii].length; jj++) {
                b = gameBoardSquares[ii][jj];
                if ((jj % 2 == 1 && ii % 2 == 1)
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
            }
        }
    }

    private JPanel setupGameBoardPanel() {
        JPanel gameBoardPanel = new JPanel(new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS));

        // create the game board squares
        initializeGameBoardSquares();

        // Adding board squares to gameBoardPanel
        for (int ii = 0; ii < NUMBER_OF_ROWS; ii++) {
            for (int jj = 0; jj < NUMBER_OF_COLUMNS; jj++) {
                gameBoardPanel.add(gameBoardSquares[ii][jj]);
            }
        }

        return gameBoardPanel;
    }

    private JPanel setupColumnButtonPanel() {
        JPanel columnButtonPanel = new JPanel(new GridLayout(1, 7));
        columnButtons = new JButton[NUMBER_OF_COLUMNS];

        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            columnButtons[i] = new JButton(Integer.toString(i + 1));
            columnButtons[i].setName(Integer.toString(i));

            columnButtons[i].addActionListener(event -> columnButtonPressed((JButton) event.getSource()));
            columnButtonPanel.add(columnButtons[i]);
        }
        return columnButtonPanel;
    }

    private void resetColumnButtons() {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            columnButtons[i].setEnabled(true);
        }
    }

    private JPanel setupGameControlPanel() {
        JPanel gameControlPanel = new JPanel();
        JButton restartButton = new JButton("RESTART");
        JButton mainMenuButton = new JButton("Main Menu");

        restartButton.addActionListener(event -> game.resetGame());

        mainMenuButton.addActionListener(event -> game.stop());

        gameControlPanel.add(restartButton);
        gameControlPanel.add(mainMenuButton);
        return gameControlPanel;
    }

    public void resetGame() {
        gameStatusLabel.setText(gameStatusMessage);
        resetColumnButtons();
        resetGameBoardSquares();
    }

    private JPanel setupGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Setting up columnButtonPanel
        JPanel columnButtonPanel = setupColumnButtonPanel();
        Dimension buttonPanelDimension = new Dimension();
        buttonPanelDimension.height = 5;
        columnButtonPanel.setPreferredSize(buttonPanelDimension);

        // Setting up gameStatusPanel
        JPanel gameStatusPanel = setupGameStatusPanel();

        // Setting up gameBoardPanel
        JPanel gameBoardPanel = setupGameBoardPanel();
        Dimension boardDimension = new Dimension();
        boardDimension.height = 350;
        gameBoardPanel.setPreferredSize(boardDimension);

        // Setup game control panel
        JPanel gameControlPanel = setupGameControlPanel();
        GridBagConstraints gameControlPanelConstraints = new GridBagConstraints();
        gameControlPanelConstraints.gridx = 0;
        gameControlPanelConstraints.gridy = 5;

        // putting it all together
        gamePanel.add(gameStatusPanel);
        gamePanel.add(gameBoardPanel);
        gamePanel.add(Box.createVerticalStrut(10));
        gamePanel.add(columnButtonPanel);
        gamePanel.add(Box.createVerticalStrut(20));
        gamePanel.add(gameControlPanel);

        return gamePanel;
    }

    private JPanel setupGameStatusPanel() {
        JPanel gameStatusPanel = new JPanel();
        gameStatusMessage = "Game started! Player 1 to move";
        gameStatusLabel = new JLabel(gameStatusMessage);
        gameStatusPanel.add(gameStatusLabel);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        gameStatusLabel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        return gameStatusPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());

        String SINGLE_PLAYER_BUTTON_TEXT = "New Single Player Game";
        String TWO_PLAYER_BUTTON_TEXT = "New Two Player Game";

        JButton singlePlayerGameButton = new JButton(SINGLE_PLAYER_BUTTON_TEXT);
        JButton twoPlayerGameButton = new JButton(TWO_PLAYER_BUTTON_TEXT);

        singlePlayerGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gameSelectionButtonPressed(true);
            }
        });

        twoPlayerGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gameSelectionButtonPressed(false);
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.add(singlePlayerGameButton);
        centerPanel.add(twoPlayerGameButton);

        menuPanel.add(centerPanel);

        return menuPanel;
    }

    private void gameSelectionButtonPressed(boolean isSinglePlayer) {
        game.startGame(isSinglePlayer);
    }

    public void gameStarted() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.getContentPane().doLayout();
        frame.update(frame.getGraphics());
    }


    private void columnButtonPressed(JButton button) {
        int column = Integer.parseInt(button.getName());
        game.columnSelected(column);
    }

    public void playerMoved(int playerId, int row, int column) {
        if (playerId == 0) {
            gameBoardSquares[row][column].setBackground(Color.RED);
            gameStatusLabel.setText("Player 2 to move");
        } else {
            gameBoardSquares[row][column].setBackground(Color.BLUE);
            gameStatusLabel.setText("Player 1 to move");
        }
    }

    private void disableAllColumnButtons() {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            disableColumnButton(i);
        }
    }

    public void playerWon(int playerId) {
        disableAllColumnButtons();
        gameStatusLabel.setText("player " + (playerId + 1) + " won!");
    }

    public void gameDraw() {
        disableAllColumnButtons();
        gameStatusLabel.setText("Draw!");
    }

    public void disableColumnButton(int column) {
        columnButtons[column].setEnabled(false);
    }

    public void gameStopped() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(menuPanel);;
        frame.update(frame.getGraphics());
    }
}
