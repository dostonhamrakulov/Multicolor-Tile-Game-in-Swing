//Created by DOston Hamrakulov
//10.10.2017 in Riga, Latvia

package helper;

import GUI.GUI.UIHelper;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameLogic {
    public Set<Integer> getRandomIndex(int numberOfIndexes) {
        Random random = new Random();

        Set<Integer> indexes = new HashSet<>();

        while (indexes.size() < numberOfIndexes) {
            indexes.add(random.nextInt(GameConstants.TOTAL_GRID));
        }

        return indexes;
    }

    public void startRestartGame() {
        UIHelper uiHelper = UIHelper.getMainUI();
        uiHelper.displayColouredSquares(GameConstants.COLOR_TILE);
        startTimer(2, uiHelper);
    }

    private void startTimer(int second, UIHelper uiHelper) {
        Timer timer = new Timer(second * 1000, e -> uiHelper.resetSquares());
        timer.setRepeats(false);
        timer.start();
    }
}
