package main.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ScoreBoard extends JPanel implements Observer {
    private static final String SCORE = "Score: ";
    private static final String HIGH_SCORE = "High Score: ";
    private static final int LABEL_WIDTH = 200;
    public static final int LABEL_HEIGHT = 30;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private int score;
    private int highScore = 0;

    // Constructs a score panel with background colour and initial labels
    public ScoreBoard() {
        setBackground(new Color(84, 150, 46));
        scoreLabel = new JLabel(SCORE + score);
        scoreLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        add(scoreLabel);
        highScoreLabel = new JLabel(HIGH_SCORE + highScore);
        highScoreLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        add(highScoreLabel);
        add(Box.createHorizontalStrut(10));
    }

    // Updates the score and high score
    @Override
    public void update(Observable o, Object arg) {
        if (arg == null) {
            score = 0;
        } else if ((int) arg == 1) {
            score++;
        }
        scoreLabel.setText(SCORE + score);
        if (score > highScore) {
            highScore = score;
            highScoreLabel.setText(HIGH_SCORE + highScore);
        }
        repaint();
    }
}
