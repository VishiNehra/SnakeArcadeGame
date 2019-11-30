package main.model;

import com.sun.javafx.scene.traversal.Direction;
import main.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class Snake {

    public static final int WIDTH = GamePanel.CHECKER_SIZE;
    public static final int HEIGHT = GamePanel.CHECKER_SIZE;
    public static final int SPEED = GamePanel.CHECKER_SIZE;

    private java.util.List<Figure> body;

    private Image bodyImageVertical;
    private Image bodyImageHorizontal;
    private Image headImageDown;
    private Image headImageUp;
    private Image headImageRight;
    private Image headImageLeft;
    private Image tailImageDown;
    private Image tailImageUp;
    private Image tailImageRight;
    private Image tailImageLeft;
    private Image upAndRightTurn;
    private Image downAndRightTurn;
    private Image upAndLeftTurn;
    private Image downAndLeftTurn;

    // Constructs a snake at given coordinates, with 4 body parts and loads images
    public Snake(int posX, int posY) {
        body = new LinkedList<>(Arrays.asList(
            new Figure(posX, posY, WIDTH, HEIGHT, Direction.UP), // body parts one after another
            new Figure(posX, posY + HEIGHT, WIDTH, HEIGHT, Direction.UP),
            new Figure(posX, posY + 2 * HEIGHT, WIDTH, HEIGHT, Direction.UP),
            new Figure(posX, posY + 3 * HEIGHT, WIDTH, HEIGHT, Direction.UP)));
        loadImages();
    }

    // Loads all the images corresponding to the snake
    private void loadImages() {
        try {
            bodyImageVertical = ImageIO.read(new File("images\\bodyVertical.png"));
            bodyImageHorizontal = ImageIO.read(new File("images\\bodyHorizontal.png"));
            headImageDown = ImageIO.read(new File("images\\headDown.png"));
            headImageUp = ImageIO.read(new File("images\\headUp.png"));
            headImageRight = ImageIO.read(new File("images\\headRight.png"));
            headImageLeft = ImageIO.read(new File("images\\headLeft.png"));
            tailImageDown = ImageIO.read(new File("images\\tailDown.png"));
            tailImageUp = ImageIO.read(new File("images\\tailUp.png"));
            tailImageRight = ImageIO.read(new File("images\\tailRight.png"));
            tailImageLeft = ImageIO.read(new File("images\\tailLeft.png"));
            upAndRightTurn = ImageIO.read(new File("images\\upAndRightTurn.png"));
            downAndRightTurn = ImageIO.read(new File("images\\downAndRightTurn.png"));
            upAndLeftTurn = ImageIO.read(new File("images\\upAndLeftTurn.png"));
            downAndLeftTurn = ImageIO.read(new File("images\\downAndLeftTurn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Moves the snake according to each part's direction
    public void move() {
        for (int i = body.size() - 1; i > 0; i--) {
            // moves each body part to the position of the body part in front of it (in the body list)
            body.get(i).setPosX(body.get(i - 1).getPosX());
            body.get(i).setPosY(body.get(i - 1).getPosY());
            body.get(i).setDirection(body.get(i-1).getDirection());
        }

        Figure head = getHead();
        Direction headDirection = head.getDirection();
        if (headDirection == Direction.RIGHT) {
            head.incrementPosX(SPEED);
        } else if (headDirection == Direction.LEFT) {
            head.incrementPosX(-SPEED);
        } else if (headDirection == Direction.UP) {
            head.incrementPosY(-SPEED);
        } else if (headDirection == Direction.DOWN) {
            head.incrementPosY(SPEED);
        }
    }

    // Adds a body part to the tail of the snake
    public void grow() {
        Figure lastPart = body.get(body.size() - 1);
        body.add(new Figure(lastPart.getPosX(), lastPart.getPosY(), WIDTH, HEIGHT, lastPart.getDirection()));
    }

    // Directs the snake up
    public void directUp() {
        // Check 2nd body part to see snake isn't going to conduct an invalid move
        if (body.get(0).getDirection() != Direction.DOWN && body.get(1).getDirection() != Direction.DOWN)
            getHead().setDirection(Direction.UP);
    }

    // Directs the snake down
    public void directDown() {
        // Check 2nd body part to see snake isn't going to conduct an invalid move
        if (body.get(0).getDirection() != Direction.UP && body.get(1).getDirection() != Direction.UP)
            getHead().setDirection(Direction.DOWN);
    }

    // Directs the snake right
    public void directRight() {
        // Check 2nd body part to see snake isn't going to conduct an invalid move
        if (body.get(0).getDirection() != Direction.LEFT && body.get(1).getDirection() != Direction.LEFT)
            getHead().setDirection(Direction.RIGHT);
    }

    // Directs the snake left
    public void directLeft() {
        // Check 2nd body part to see snake isn't going to conduct an invalid move
        if (body.get(0).getDirection() != Direction.RIGHT && body.get(1).getDirection() != Direction.RIGHT)
            getHead().setDirection(Direction.LEFT);
    }

    // returns the head, or the first body part of the snake
    public Figure getHead() {
        return body.get(0);
    }

    public java.util.List<Figure> getBody() {
        return body;
    }

    // Draws the Figure on the Graphics object g
    public void draw(Graphics graphics) {
        drawHead(graphics);
        drawBody(graphics);
        drawTail(graphics);
    }

    // Draws the head of the snake
    private void drawHead(Graphics graphics) {
        Figure head = getHead();
        if (head.getDirection() == Direction.DOWN)
            graphics.drawImage(headImageDown,  head.getPosX() - WIDTH / 2,  head.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (head.getDirection() == Direction.UP)
            graphics.drawImage(headImageUp,  head.getPosX() - WIDTH / 2,  head.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (head.getDirection() == Direction.RIGHT)
            graphics.drawImage(headImageRight,  head.getPosX() - WIDTH / 2,  head.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (head.getDirection() == Direction.LEFT)
            graphics.drawImage(headImageLeft,  head.getPosX() - WIDTH / 2,  head.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
    }

    // Draws the body of the snake (excluding head and tail of snake)
    private void drawBody(Graphics graphics) {
        for (int i = 1; i < body.size() - 1; i++) { // Does not draw head and tail, only parts in the middle
            Direction aheadDirection = body.get(i - 1).getDirection();
            Direction currentDirection = body.get(i).getDirection();
            Direction behindDirection = body.get(i + 1).getDirection();
            Figure current = body.get(i);
            // OR's signify to situations using the same image, first two are for general situations, next four for 2 immediate turns
            if (isTurningDownRight(aheadDirection, currentDirection, behindDirection))
                graphics.drawImage(downAndRightTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (isTurningUpAndRight(aheadDirection, currentDirection, behindDirection))
                graphics.drawImage(upAndRightTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (isTurningDownAndLeft(aheadDirection, currentDirection, behindDirection))
                graphics.drawImage(downAndLeftTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (isTurningUpAndLeft(aheadDirection, currentDirection, behindDirection))
                graphics.drawImage(upAndLeftTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (isGoingVertical(currentDirection))
                graphics.drawImage(bodyImageVertical,  current.getPosX() - WIDTH / 2,  current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (isGoingHorizontal(currentDirection))
                graphics.drawImage(bodyImageHorizontal,  current.getPosX() - WIDTH / 2,  current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        }
    }

    private boolean isGoingHorizontal(Direction currentDirection) {
        return currentDirection == Direction.RIGHT || currentDirection == Direction.LEFT;
    }

    private boolean isGoingVertical(Direction currentDirection) {
        return currentDirection == Direction.UP || currentDirection == Direction.DOWN;
    }

    private boolean isTurningUpAndLeft(Direction aheadDirection, Direction currentDirection, Direction behindDirection) {
        return (aheadDirection == Direction.LEFT && currentDirection == Direction.LEFT && behindDirection == Direction.DOWN) ||
                (aheadDirection == Direction.UP && currentDirection == Direction.UP && behindDirection == Direction.RIGHT) ||
                (aheadDirection == Direction.UP && currentDirection == Direction.LEFT && behindDirection == Direction.DOWN)||
                (aheadDirection == Direction.LEFT && currentDirection == Direction.UP && behindDirection == Direction.RIGHT) ||
                (aheadDirection == Direction.DOWN && currentDirection == Direction.LEFT && behindDirection == Direction.DOWN) ||
                (aheadDirection == Direction.RIGHT && currentDirection == Direction.UP && behindDirection == Direction.RIGHT);
    }

    private boolean isTurningDownAndLeft(Direction aheadDirection, Direction currentDirection, Direction behindDirection) {
        return (aheadDirection == Direction.LEFT && currentDirection == Direction.LEFT && behindDirection == Direction.UP) ||
                (aheadDirection == Direction.DOWN && currentDirection == Direction.DOWN && behindDirection == Direction.RIGHT) ||
                (aheadDirection == Direction.DOWN && currentDirection == Direction.LEFT && behindDirection == Direction.UP) ||
                (aheadDirection == Direction.LEFT && currentDirection == Direction.DOWN && behindDirection == Direction.RIGHT) ||
                (aheadDirection == Direction.RIGHT && currentDirection == Direction.DOWN && behindDirection == Direction.RIGHT)||
                (aheadDirection == Direction.UP && currentDirection == Direction.LEFT && behindDirection == Direction.UP);
    }

    private boolean isTurningDownRight(Direction aheadDirection, Direction currentDirection, Direction behindDirection) {
        return (aheadDirection == Direction.RIGHT && currentDirection == Direction.RIGHT && behindDirection == Direction.UP) ||
            (aheadDirection == Direction.DOWN && currentDirection == Direction.DOWN && behindDirection == Direction.LEFT) ||
            (aheadDirection == Direction.DOWN && currentDirection == Direction.RIGHT && behindDirection == Direction.UP) ||
            (aheadDirection == Direction.RIGHT && currentDirection == Direction.DOWN && behindDirection == Direction.LEFT) ||
            (aheadDirection == Direction.UP && currentDirection == Direction.RIGHT && behindDirection == Direction.UP) ||
            (aheadDirection == Direction.LEFT && currentDirection == Direction.DOWN && behindDirection == Direction.LEFT);
    }

    private boolean isTurningUpAndRight(Direction aheadDirection, Direction currentDirection, Direction behindDirection) {
        return (aheadDirection == Direction.RIGHT && currentDirection == Direction.RIGHT && behindDirection == Direction.DOWN) ||
                (aheadDirection == Direction.UP && currentDirection == Direction.UP && behindDirection == Direction.LEFT) ||
                (aheadDirection == Direction.UP && currentDirection == Direction.RIGHT && behindDirection == Direction.DOWN) ||
                (aheadDirection == Direction.RIGHT && currentDirection == Direction.UP && behindDirection == Direction.LEFT) ||
                (aheadDirection == Direction.DOWN && currentDirection == Direction.RIGHT && behindDirection == Direction.DOWN) ||
                (aheadDirection == Direction.LEFT && currentDirection == Direction.UP && behindDirection == Direction.LEFT);
    }

    // Draws the tail of the snake
    private void drawTail(Graphics graphics) {
        Figure tail = body.get(body.size() - 1);
        // to avoid double painting of tile, we reset it to background colour
        if (GamePanel.isCoordinateCheckerbox(tail.getPosX() - GamePanel.CHECKER_SIZE / 2, tail.getPosY() - GamePanel.CHECKER_SIZE / 2)) {
            GamePanel.paintCheckerBoardBox(graphics, tail.getPosX() - GamePanel.CHECKER_SIZE / 2, tail.getPosY() - GamePanel.CHECKER_SIZE / 2);
        } else {
            GamePanel.paintBackgroundBox(graphics, tail.getPosX() - GamePanel.CHECKER_SIZE / 2, tail.getPosY() - GamePanel.CHECKER_SIZE / 2);
        }

        if (tail.getDirection() == Direction.DOWN)
            graphics.drawImage(tailImageDown,  tail.getPosX() - WIDTH / 2,  tail.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (tail.getDirection() == Direction.UP)
            graphics.drawImage(tailImageUp,  tail.getPosX() - WIDTH / 2,  tail.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (tail.getDirection() == Direction.RIGHT)
            graphics.drawImage(tailImageRight,  tail.getPosX() - WIDTH / 2,  tail.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        else if (tail.getDirection() == Direction.LEFT)
            graphics.drawImage(tailImageLeft,  tail.getPosX() - WIDTH / 2,  tail.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
    }

    // Returns true if snake has collided with itself, otherwise returns false
    public boolean isSelfCollided() {
        Figure head = getHead();
        for (int i = 1; i < body.size(); i++) { // Skip over 1st element, which is head itself
            if (body.get(i).getPosX() == head.getPosX() && body.get(i).getPosY() == head.getPosY())
                return true;
        }
        return false;
    }

    // returns true if any part of snake body contains coordinates posX and posY, otherwise returns false
    public boolean containsCoordinate(int posX, int posY) {
        for (Figure part:body) {
            if (part.getPosX() == posX && part.getPosY() == posY)
                return true;
        }
        return false;
    }
}
