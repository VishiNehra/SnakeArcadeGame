package main.ui;

import main.model.Game;

import javax.swing.*;
import java.awt.*;


// The panel in which the game is rendered.
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    public static final int CHECKER_SIZE = 30;
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "Press R to replay";

    private Game game;

    // Constructs a game panel, with size and background colour
    public GamePanel(Game game) {
        setLayout(new GridLayout(30, 30));
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(new Color(162, 209, 73));
        this.game = game;
    }

    // Paints given coordinate in the color of checkerbox
    public static void paintCheckerBoardBox(Graphics graphics, int i, int j) {
        graphics.setColor(new Color(170, 215, 81));
        graphics.fillRect(i, j, CHECKER_SIZE, CHECKER_SIZE);
    }

    // Paints given coordinate in the color of the background
    public static void paintBackgroundBox(Graphics graphics, int i, int j) {
        graphics.setColor(new Color(162, 209, 73));
        graphics.fillRect(i, j, CHECKER_SIZE, CHECKER_SIZE);
    }

    // Returns true if a checkerbox is at the location of given coordinates, else returns false
    public static boolean isCoordinateCheckerbox(int i, int j) {
        return (i % CHECKER_SIZE == 0 && j % CHECKER_SIZE == 0 && Math.abs(i - j) % (2*CHECKER_SIZE) == 0);
    }

    @Override
    // Sets up the background for the game
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // for checkerboard pattern
        for(int i = 0; i <= Game.WIDTH; i+= 2*CHECKER_SIZE){
            for(int j = 0; j <= Game.HEIGHT; j+= 2*CHECKER_SIZE) {
                graphics.setColor(new Color(170, 215, 81));
                graphics.fillRect(i, j, CHECKER_SIZE, CHECKER_SIZE);
                graphics.fillRect(i + CHECKER_SIZE, j + CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
            }
        }

        drawGame(graphics);
        if (game.isGameOver()) {
            gameOver(graphics);
        }
    }

    // Draws the game
    private void drawGame(Graphics graphics) {
        game.draw(graphics);
    }

    // Draws the "game over" message and replay instructions
    private void gameOver(Graphics graphics) {
        Color saved = graphics.getColor();
        graphics.setColor(new Color( 0, 0, 0));
        graphics.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = graphics.getFontMetrics();
        centreString(OVER, graphics, fm, Game.HEIGHT / 2);
        centreString(REPLAY, graphics, fm, Game.HEIGHT / 2 + 50);
        graphics.setColor(saved);
    }

    // Centres given string horizontally onto given graphics at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, posY);
    }
}
