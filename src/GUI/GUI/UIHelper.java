//Created by DOston Hamrakulov
//10.10.2017 in Riga, Latvia

package GUI;

import helper.GameConstants;
import helper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class UIHelper {
    private static final int SQUARE_LEN = (int) Math.sqrt(GameConstants.TOTAL_GRID);
    private static final int GAP = 4;
    private static final String TITLE = "YOUR-MEMORY";
    private static UIHelper gameInstance = null;
    private static int score = 0;
    private Color normalColor;
    private JToggleButton[] squares;
    private JFrame mainFrame;
    private JPanel squareContainer;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JLabel scoreLabel;
    private Set<Integer> colorablePositions;
    private GameLogic gameLogic = new GameLogic();
    private Set<Integer> userSelectedSquares;

    private UIHelper() {
    }

    public static UIHelper getMainUI() {
        if (gameInstance == null) {
            gameInstance = new UIHelper();
            gameInstance.displayGameUI();
        }
        return gameInstance;
    }

    private void displayGameUI() {
        normalColor = new Color(0, 155, 233);
        mainFrame = new JFrame(TITLE);
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Opened");
                gameLogic.startRestartGame();
            }
        });

        initializeGameWindow();
        mainFrame.setVisible(true);
    }

    private void initializeGameWindow() {
        headerPanel = getPanel(Color.DARK_GRAY);
        headerPanel.add(getLabel("WELCOME TO THE GAME", Color.WHITE), Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel();
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 14));
        showScoreInScreen();

        footerPanel = getPanel(Color.DARK_GRAY);
        footerPanel.add(scoreLabel);

        initializeSquares();

        mainFrame.add(headerPanel, BorderLayout.PAGE_START);
        mainFrame.add(squareContainer, BorderLayout.CENTER);
        mainFrame.add(footerPanel, BorderLayout.PAGE_END);
    }

    private void showScoreInScreen() {
        scoreLabel.setText("YOUR SCORE: " + score);
        waitSomeTime(500);
    }

    private void waitSomeTime(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JLabel getLabel(String text, Color fontColor) {
        JLabel label = new JLabel(text);
        label.setForeground(fontColor);
        return label;
    }

    private JPanel getPanel(Color c) {
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    private void initializeSquares() {
        squareContainer = new JPanel(new GridLayout(SQUARE_LEN, SQUARE_LEN, GAP, GAP));
        squares = new JToggleButton[GameConstants.TOTAL_GRID];

        JToggleButton square;
        for (int i = 0; i < GameConstants.TOTAL_GRID; i++) {
            square = getTile(normalColor, i);
            squareContainer.add(square);
            squares[i] = square;
        }
    }

    private JToggleButton getTile(Color c, int index) {
        JToggleButton button = new JToggleButton();
        button.setBackground(c);
        button.setName(String.valueOf(index));
        button.addActionListener(e -> checkAnswer(e));
        return button;
    }

    private void checkAnswer(ActionEvent e) {
        JToggleButton selectedSqr = (JToggleButton) e.getSource();
        int index = Integer.parseInt(selectedSqr.getName());

        if (selectedSqr.getModel().isSelected()) {
            userSelectedSquares.add(index);
        } else {
            userSelectedSquares.remove(index);
        }

        if (userSelectedSquares.size() == colorablePositions.size()) {
            if (isAnswerCorrect()) {
                score++;
                System.out.println("Your score is: " + score);
            }
            gameLogic.startRestartGame();
        }
    }

    public void displayColouredSquares(int numberOfTiles) {
        resetGame();
        colorablePositions = gameLogic.getRandomIndex(numberOfTiles);
        for (int i = 0; i < GameConstants.TOTAL_GRID; i++) {
            if (colorablePositions.contains(i)) {
                squares[i].setBackground(new Color(36, 180, 115));
            }
        }
    }

    public boolean isAnswerCorrect() {
        return userSelectedSquares.containsAll(colorablePositions);
    }

    public void resetGame() {
        userSelectedSquares = new HashSet<>();
        revertSelectedSquares();
        setSelectable(false);
        showScoreInScreen();
    }

    public void resetSquares() {
        for (Integer index : colorablePositions) {
            squares[index].setBackground(normalColor);
        }
        setSelectable(true);
    }

    public void setSelectable(boolean status) {
        for (JToggleButton sqr : squares) {
            sqr.setEnabled(status);
        }
    }

    public void revertSelectedSquares() {
        for (JToggleButton sqr : squares) {
            if (sqr.getModel().isSelected()) {
                sqr.setSelected(false);
            }
        }
    }
}
