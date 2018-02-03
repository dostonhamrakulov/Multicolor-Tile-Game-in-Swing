//Created by DOston Hamrakulov
//10.10.2017 in Riga, Latvia

package GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class GameStarter {

    private static final String TITLE = "MENU";
    private JFrame window;

    public void startApplication() {
        prepareWindow();
    }

    private void prepareWindow() {
        window = new JFrame(TITLE);
        window.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);

        JButton startMenu = getMenuItem("START GAME");
        startMenu.addActionListener(e -> displayGameUI());

        JButton exitMenu = getMenuItem("EXIT");
        exitMenu.addActionListener(e -> window.dispose());

        String helpText = "<html><B><br>TIP: There are 16 tiles in the game. <br>" +
                "Some tile will have different color at the beginning of game and they <br>" +
                "will be changed to normal color after 2 seconds. <br>" +
                "You have to remember and select all colored tile to get score.<br></B></html>";
        JLabel helpLabel = new JLabel(helpText);
        panel.add(helpLabel);
        panel.add(startMenu);
        panel.add(exitMenu);

        window.add(panel);

        window.setVisible(true);
    }

    private JButton getMenuItem(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(12, 103, 148));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void displayGameUI() {
        UIHelper.getMainUI();
        window.dispose();
    }
}
