package main.model;

import main.ui.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable {

    public static final int WIDTH = 810; // must be a multiple of GamePanel.CHECKER_SIZE
    public static final int HEIGHT = 600; // must be a multiple of GamePanel.CHECKER_SIZE

    private Snake snake;
    private Treat treat;

    private boolean gameOver;

    // Constructs the game, with snake near the middle of the screen
    public Game() {
        snake = new Snake((int) Math.round(12.5*GamePanel.CHECKER_SIZE), (int) Math.round(11.5*GamePanel.CHECKER_SIZE));
        initializeTreat();
        gameOver = false;
    }

    // Spawns a treat randomly on the screen (ensures treat isn't on the snake)
    private void initializeTreat() {
        Random random = new Random(); // creates a random treat such that it fits in the checkerboard pattern
        int randomX = GamePanel.CHECKER_SIZE/2 + GamePanel.CHECKER_SIZE*Math.round(random.nextInt(Game.WIDTH)/GamePanel.CHECKER_SIZE);
        int randomY = GamePanel.CHECKER_SIZE/2 + GamePanel.CHECKER_SIZE*Math.round(random.nextInt(Game.HEIGHT)/GamePanel.CHECKER_SIZE);
        // To ensure that treat does not fall on snake
        while(snake.containsCoordinate(randomX, randomY)) { // loop continues until treat location is different from that of snake
            randomX = GamePanel.CHECKER_SIZE/2 + GamePanel.CHECKER_SIZE*Math.round(random.nextInt(Game.WIDTH)/GamePanel.CHECKER_SIZE);
            randomY = GamePanel.CHECKER_SIZE/2 + GamePanel.CHECKER_SIZE*Math.round(random.nextInt(Game.HEIGHT)/GamePanel.CHECKER_SIZE);
        }
        treat = new Treat(randomX, randomY);
    }

    // Updates the game: moves snake, removes treat and checks if snake is collided with self or with wall
    public void update() {
        if (!gameOver) {
            moveSnake();
            removeEatenTreatAndGenerateTreat();
            removeSelfCollidedSnake();
            removeWallCollidedSnake();
        }
    }

    // Moves snake or resets the game in case it is over (in response to key presses)
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP)
            snake.directUp();
        else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN)
            snake.directDown();
        else if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT)
            snake.directLeft();
        else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT)
            snake.directRight();
        else if (keyCode == KeyEvent.VK_R && gameOver)
            reset();
    }

    // Draws the snake and the treat
    public void draw(Graphics graphics) {
        snake.draw(graphics);
        treat.draw(graphics);
    }

    // Moves the snake if it isn't out of bounds
    private void moveSnake() {
        if (snake.getHead().getPosX() > 0 && snake.getHead().getPosX() < WIDTH &&
            snake.getHead().getPosY() > 0 && snake.getHead().getPosY() < HEIGHT) {
            snake.move();
        }
    }

    // If treat has been eaten: generates new treat and notifies observers, otherwise does nothing
    private void removeEatenTreatAndGenerateTreat() {
        if (Math.abs(snake.getHead().getPosX() - treat.getPosX()) < Snake.WIDTH/2 &&
                Math.abs(snake.getHead().getPosY() - treat.getPosY()) < Snake.HEIGHT/2) {
            snake.grow();
            initializeTreat();
            setChanged();
            notifyObservers(1);
        }
    }

    // Set's gameOver to true and notifies observers if snake has self collided
    private void removeSelfCollidedSnake() {
        if (snake.isSelfCollided()) {
            setChanged();
            notifyObservers();
            gameOver = true;
        }
    }

    // Set's gameOver to true and notifies observers if snake has collided with wall
    private void removeWallCollidedSnake() {
        if (snake.getHead().getPosX() <= 0 || snake.getHead().getPosX() >= WIDTH ||
                snake.getHead().getPosY() <= 0 || snake.getHead().getPosY() >= HEIGHT) {
            setChanged();
            notifyObservers();
            gameOver = true;
        }
    }

    // Reset's the game by spawning a new snake and treat, sets gameOver to false
    private void reset() {
        snake = new Snake((int) Math.round(12.5*GamePanel.CHECKER_SIZE), (int) Math.round(11.5*GamePanel.CHECKER_SIZE));
        initializeTreat();
        gameOver = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
