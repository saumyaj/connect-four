package view;

import model.ConnectFourModel;
import controller.Controller;
import model.GameListener;
import model.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView implements GameListener {

    private ConnectFourModel model;
    private Controller controller;

    private int NUMBER_OF_COLUMNS = 7, NUMBER_OF_ROWS = 6;
    private JLabel gameStatusLabel;
    private JFrame frame;
    private JPanel gamePanel, menuPanel;
    private JButton[] columnButtons;
    private JButton[][] gameBoardSquares;

    private static final String FRAME_TITLE = "Connect4";
    private static final String RESTART_BUTTON_TEXT = "Restart";
    private static final String MAIN_MENU_BUTTON_TEXT = "Main Menu";
    private static final String GAME_STARTED_LABEL_MESSAGE = "Game Started! %s to move";
    private static final String PLAYER_MOVE_LABEL_MESSAGE = "%s to move";
    private static final String GAME_DRAW_LABEL_MESSAGE = "Draw!";
    private static final String PLAYER_WON_LABEL_MESSAGE = "%s won!";
    private static final String SINGLE_PLAYER_GAME_BUTTON_TEXT = "New Single Player Game";
    private static final String TWO_PLAYER_GAME_BUTTON_TEXT = "New Two Player Game";
    private static final String SINGLE_PLAYER_GAME_NAME = "SINGLE_PLAYER_GAME";
    private static final String TWO_PLAYER_GAME_NAME = "TWO_PLAYER_GAME";

    private static int FRAME_WIDTH = 600;
    private static int FRAME_HEIGHT = 600;

    public GameView(Controller controller, ConnectFourModel model) {
        this.model = model;
        this.controller = controller;
        this.model.addGameListener(this);
        gameBoardSquares = new JButton[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        initializeGui();
    }

    void initializeGui() {

        // Setting up frame
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
        for (int ii = 0; ii < gameBoardSquares.length; ii++) {
            for (int jj = 0; jj < gameBoardSquares[ii].length; jj++) {
                final JButton b = new JButton();
                b.setEnabled(false);
                b.setBorderPainted(false);
                b.setBackground(Color.BLACK);
                b.setOpaque(true);
                decideAndSetInitialButtonColor(b, ii, jj);
                gameBoardSquares[ii][jj] = b;
            }
        }
    }

    private void resetGameBoardSquares() {
        JButton button;
        for (int ii = 0; ii < gameBoardSquares.length; ii++) {
            for (int jj = 0; jj < gameBoardSquares[ii].length; jj++) {
                button = gameBoardSquares[ii][jj];
                decideAndSetInitialButtonColor(button, ii, jj);
            }
        }
    }

    private void decideAndSetInitialButtonColor(JButton button, int row, int col) {
        if ((col % 2 == 1 && row % 2 == 1)
                || (col % 2 == 0 && row % 2 == 0)) {
            button.setBackground(Color.WHITE);
        } else {
            button.setBackground(Color.BLACK);
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
        JButton restartButton = new JButton(RESTART_BUTTON_TEXT);
        JButton mainMenuButton = new JButton(MAIN_MENU_BUTTON_TEXT);

        restartButton.addActionListener(event -> controller.restartGame());

        mainMenuButton.addActionListener(event -> controller.goToMainMenu());

        gameControlPanel.add(restartButton);
        gameControlPanel.add(mainMenuButton);
        return gameControlPanel;
    }

    public void resetGame(Player firstPlayer) {
        gameStatusLabel.setText(String.format(GAME_STARTED_LABEL_MESSAGE, firstPlayer.getName()));
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
        gameStatusLabel = new JLabel();
        gameStatusPanel.add(gameStatusLabel);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        gameStatusLabel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        return gameStatusPanel;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        JButton singlePlayerGameButton = new JButton(SINGLE_PLAYER_GAME_BUTTON_TEXT);
        singlePlayerGameButton.setName(SINGLE_PLAYER_GAME_NAME);
        JButton twoPlayerGameButton = new JButton(TWO_PLAYER_GAME_BUTTON_TEXT);
        twoPlayerGameButton.setName(TWO_PLAYER_GAME_NAME);

        singlePlayerGameButton.addActionListener(event -> gameSelectionButtonPressed((JButton) event.getSource()));

        twoPlayerGameButton.addActionListener(event -> gameSelectionButtonPressed((JButton) event.getSource()));

        JPanel centerPanel = new JPanel();
        centerPanel.add(singlePlayerGameButton);
        centerPanel.add(twoPlayerGameButton);

        menuPanel.add(centerPanel);

        return menuPanel;
    }

    private void gameSelectionButtonPressed(JButton button) {
        controller.startGame(button.getName());
    }

    public void gameStarted(Player firstPlayer) {
        gameStatusLabel.setText(String.format(GAME_STARTED_LABEL_MESSAGE, firstPlayer.getName()));
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.getContentPane().doLayout();
        frame.update(frame.getGraphics());
    }


    private void columnButtonPressed(JButton button) {
        int column = Integer.parseInt(button.getName());
        controller.columnSelected(column);
    }

    public void playerMoved(Player currentPlayer, Player otherPlayer, int row, int column) {
        if (currentPlayer.getSymbol().equals(Player.Symbol.CIRCLE)) {
            gameBoardSquares[row][column].setBackground(Color.RED);
        } else {
            gameBoardSquares[row][column].setBackground(Color.BLUE);
        }
        gameStatusLabel.setText(String.format(PLAYER_MOVE_LABEL_MESSAGE, otherPlayer.getName()));
    }

    private void disableAllColumnButtons() {
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            disableColumnButton(i);
        }
    }

    public void playerWon(Player player) {
        disableAllColumnButtons();
        gameStatusLabel.setText(String.format(PLAYER_WON_LABEL_MESSAGE, player.getName()));
    }

    public void gameDraw() {
        disableAllColumnButtons();
        gameStatusLabel.setText(GAME_DRAW_LABEL_MESSAGE);
    }

    public void disableColumnButton(int column) {
        columnButtons[column].setEnabled(false);
    }

    public void gameStopped() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(menuPanel);
        ;
        frame.doLayout();
        frame.update(frame.getGraphics());
        frame.repaint();
    }
}
