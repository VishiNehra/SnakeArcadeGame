package main.ui;

import main.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Main, sets up the game
public class Play extends JFrame {
    private static final int INTERVAL = 100;
    private GamePanel gamePanel;
    private Game game;
    private ScoreBoard scoreBoard;
    private Timer t;

    // Constructs main window in which the game will be played
    public Play() {
        super("Play Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new Game();
        gamePanel = new GamePanel(game);
        scoreBoard = new ScoreBoard();
        game.addObserver(scoreBoard);
        add(gamePanel);
        add(scoreBoard, BorderLayout.NORTH);
        getRootPane().setBorder(BorderFactory.createMatteBorder(0, ScoreBoard.LABEL_HEIGHT, ScoreBoard.LABEL_HEIGHT,
                ScoreBoard.LABEL_HEIGHT, new Color(84, 150, 46)));
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
        t.start();
    }

    // Initializes a timer that updates game every INTERVAL milliseconds
    private void addTimer() {
        t = new Timer(INTERVAL, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                gamePanel.repaint();
            }
        });
    }

    // Location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // A key handler to respond to key events
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }
    }

    // Play the game
    public static void main(String[] args) {
        new Play();
    }
}