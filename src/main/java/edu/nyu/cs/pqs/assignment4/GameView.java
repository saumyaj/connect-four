package edu.nyu.cs.pqs.assignment4;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView implements GameListener {

    private final String FRAME_TITLE = "Connect4";
    private Connect4 game;
    private String topLabelMessage;
    private int NUMBER_OF_COLUMNS = 7, NUMBER_OF_ROWS = 6;
    private JLabel gameStatusLabel;
    private String gameStatusMessage;
    private JFrame frame;
    private JPanel gamePanel;
    private JButton[] columnButtons;

    private JButton[][] gameBoardSquares;


    // popup code
    PopupFactory pf = new PopupFactory();



    GameView(Connect4 game) {
        this.game = game;
        game.addGameListener(this);
        gameBoardSquares = new JButton[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        initializeGui();
    }

    public void initializeGui() {

        // Setting up frame
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(1024, 1024);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating menu page
        JPanel menuPanel = createMenuPanel();

        // Creating game page
        gamePanel = setupGamePanel();

        // Adding initial screen layout to frame
        frame.getContentPane().add(menuPanel);

        frame.setVisible(true);
    }

    private void initializeGameBoardSquares() {
        Insets buttonMargin = new Insets(0,5,0,5);
        for (int ii = 0; ii < gameBoardSquares.length; ii++) {
            for (int jj = 0; jj < gameBoardSquares[ii].length; jj++) {
                final JButton b = new JButton();
                b.setEnabled(false);
                b.setBorderPainted (false);
                b.setBackground(Color.BLACK);
                b.setOpaque(true);
                b.setMargin(buttonMargin);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
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
        gameBoardPanel.setBorder(new LineBorder(Color.BLACK));

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

        for (int i=0;i<NUMBER_OF_COLUMNS;i++) {
            columnButtons[i] = new JButton(Integer.toString(i+1));
            columnButtons[i].setName(Integer.toString(i));

            columnButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    columnButtonPressed((JButton)event.getSource());
                }
            });
            columnButtonPanel.add(columnButtons[i]);
        }
        return columnButtonPanel;
    }

    private void resetColumnButtons() {
        for (int i=0;i<NUMBER_OF_COLUMNS;i++) {
            columnButtons[i].setEnabled(true);
        }
    }

    private JPanel setupGameControlPanel() {
        JPanel gameControlPanel = new JPanel();
        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                game.restartGame();
            }
        });

        gameControlPanel.add(restartButton);
        return gameControlPanel;
    }

    public void resetGame() {
        gameStatusLabel.setText(gameStatusMessage);
        resetColumnButtons();
        resetGameBoardSquares();
    }

    private JPanel setupGamePanel() {
        JPanel gamePanel = new JPanel();
//        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setLayout(new GridBagLayout());

        // Setting up columnButtonPanel
        JPanel columnButtonPanel = setupColumnButtonPanel();
        GridBagConstraints columnButtonPanelConstraints = new GridBagConstraints();
        columnButtonPanelConstraints.gridx = 0;
        columnButtonPanelConstraints.gridy = 4;

        // Filler component
        Component c = javax.swing.Box.createHorizontalGlue();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;

        // Setting up gameStatusPanel
        JPanel gameStatusPanel = setupGameStatusPanel();
        GridBagConstraints gameStatusPanelConstraints = new GridBagConstraints();
        gameStatusPanelConstraints.gridx = 0;
        gameStatusPanelConstraints.gridy = 0;
        gameStatusPanelConstraints.ipady = 100;


        // Setting up gameBoardPanel
        JPanel gameBoardPanel = setupGameBoardPanel();
        GridBagConstraints gameBoardPanelConstraints = new GridBagConstraints();
        gameBoardPanelConstraints.gridx = 0;
        gameBoardPanelConstraints.gridy = 1;
//        gameBoardPanelConstraints.ipady = 300;

        // Setup game control panel
        JPanel gameControlPanel = setupGameControlPanel();
        GridBagConstraints gameControlPanelConstraints = new GridBagConstraints();
        gameControlPanelConstraints.gridx = 0;
        gameControlPanelConstraints.gridy = 5;



        gamePanel.add(gameStatusPanel, gameStatusPanelConstraints);
        gamePanel.add(gameBoardPanel, gameBoardPanelConstraints);
        gamePanel.add(columnButtonPanel, columnButtonPanelConstraints);
        gamePanel.add(gameControlPanel, gameControlPanelConstraints);
        gamePanel.add(c, constraints);

        return gamePanel;
    }

    private JPanel setupGameStatusPanel() {
        JPanel gameStatusPanel = new JPanel();
        gameStatusMessage = "game started!";
        gameStatusLabel = new JLabel(gameStatusMessage);
        gameStatusPanel.add(gameStatusLabel);
        return gameStatusPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();

        JButton singlePlayerGameButton = new JButton("New Single Player Game");
        JButton twoPlayerGameButton = new JButton("New Two Player Game");


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

        menuPanel.add(singlePlayerGameButton);
        menuPanel.add(twoPlayerGameButton);

        return menuPanel;
    }

    private void gameSelectionButtonPressed(boolean isSinglePlayer) {
        if (isSinglePlayer)
            game.startGame(true);
        else
            game.startGame(false);
    }

    public void gameStarted() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
    }


    private void columnButtonPressed(JButton button) {
        int column = Integer.parseInt(button.getName());
        game.columnSelected(column);
    }

    public void playerMoved(int playerId, int row, int column) {
        if (playerId == 0)
            gameBoardSquares[row][column].setBackground(Color.RED);
        else
            gameBoardSquares[row][column].setBackground(Color.BLUE);
    }

    void disableAllColumnButtons() {
        for (int i=0;i<NUMBER_OF_COLUMNS;i++) {
            disableColumn(i);
        }
    }

    public void playerWon(int playerId) {
        disableAllColumnButtons();
        gameStatusLabel.setText("player " + (playerId+1) + " won!");
    }

    public void gameDraw() {
        disableAllColumnButtons();
        gameStatusLabel.setText("Draw!");
    }

    public void disableColumn(int column) {
        columnButtons[column].setEnabled(false);
    }
}
