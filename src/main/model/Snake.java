package main.model;

import com.sun.javafx.scene.traversal.Direction;
import main.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Snake {

    public static final int WIDTH = GamePanel.CHECKER_SIZE;
    public static final int HEIGHT = GamePanel.CHECKER_SIZE;
    public static final int SPEED = GamePanel.CHECKER_SIZE;

    private java.util.List<Object> body;

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
        body = new LinkedList<>();
        body.add(new Object(posX, posY, WIDTH, HEIGHT, Direction.UP));
        body.add(new Object(posX, posY + HEIGHT, WIDTH, HEIGHT, Direction.UP));
        body.add(new Object(posX, posY + 2 * HEIGHT, WIDTH, HEIGHT, Direction.UP));
        body.add(new Object(posX, posY + 3 * HEIGHT, WIDTH, HEIGHT, Direction.UP));
        loadImages();
    }

    // Loads all the images corresponding to the snake
    private void loadImages() {
        try {
            bodyImageVertical = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\bodyVertical.png"));
            bodyImageHorizontal = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\bodyHorizontal.png"));
            headImageDown = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\headDown.png"));
            headImageUp = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\headUp.png"));
            headImageRight = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\headRight.png"));
            headImageLeft = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\headLeft.png"));
            tailImageDown = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\tailDown.png"));
            tailImageUp = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\tailUp.png"));
            tailImageRight = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\tailRight.png"));
            tailImageLeft = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\tailLeft.png"));
            upAndRightTurn = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\upAndRightTurn.png"));
            downAndRightTurn = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\downAndRightTurn.png"));
            upAndLeftTurn = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\upAndLeftTurn.png"));
            downAndLeftTurn = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\downAndLeftTurn.png"));
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

        Object head = getHead();
        if (head.getDirection() == Direction.RIGHT) {
            head.incrementPosX(SPEED);
        } else if (head.getDirection() == Direction.LEFT) {
            head.incrementPosX(-SPEED);
        } else if (head.getDirection() == Direction.UP) {
            head.incrementPosY(-SPEED);
        } else if (head.getDirection() == Direction.DOWN) {
            head.incrementPosY(SPEED);
        }
    }

    // Adds a body part to the tail of the snake
    public void grow() {
        Object lastPart = body.get(body.size() - 1);
        body.add(new Object(lastPart.getPosX(), lastPart.getPosY(), WIDTH, HEIGHT, lastPart.getDirection()));
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
    public Object getHead() {
        return body.get(0);
    }

    public java.util.List<Object> getBody() {
        return body;
    }

    // Draws the Object on the Graphics object g
    public void draw(Graphics graphics) {
        drawHead(graphics);
        drawBody(graphics);
        drawTail(graphics);
    }

    // Draws the head of the snake
    private void drawHead(Graphics graphics) {
        Object head = getHead();
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
            Object ahead = body.get(i - 1);
            Object current = body.get(i);
            Object behind = body.get(i + 1);
            // OR's signify to situations using the same image, first two are for general situations, next four for 2 immediate turns
            if ((ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.UP) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.LEFT) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.UP) ||
                    (ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.LEFT) ||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.UP) ||
                    (ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.LEFT))
                graphics.drawImage(downAndRightTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if ((ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.DOWN) ||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.UP && behind.getDirection() == Direction.LEFT) ||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.DOWN) ||
                    (ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.UP && behind.getDirection() == Direction.LEFT) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.RIGHT && behind.getDirection() == Direction.DOWN) ||
                    (ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.UP && behind.getDirection() == Direction.LEFT))
                graphics.drawImage(upAndRightTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if ((ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.UP) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.RIGHT) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.UP) ||
                    (ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.RIGHT) ||
                    (ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.DOWN && behind.getDirection() == Direction.RIGHT)||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.UP))
                graphics.drawImage(downAndLeftTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if ((ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.DOWN) ||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.UP && behind.getDirection() == Direction.RIGHT) ||
                    (ahead.getDirection() == Direction.UP && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.DOWN)||
                    (ahead.getDirection() == Direction.LEFT && current.getDirection() == Direction.UP && behind.getDirection() == Direction.RIGHT) ||
                    (ahead.getDirection() == Direction.DOWN && current.getDirection() == Direction.LEFT && behind.getDirection() == Direction.DOWN) ||
                    (ahead.getDirection() == Direction.RIGHT && current.getDirection() == Direction.UP && behind.getDirection() == Direction.RIGHT))
                graphics.drawImage(upAndLeftTurn, current.getPosX() - WIDTH / 2, current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (current.getDirection() == Direction.UP || current.getDirection() == Direction.DOWN)
                graphics.drawImage(bodyImageVertical,  current.getPosX() - WIDTH / 2,  current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
            else if (current.getDirection() == Direction.RIGHT || current.getDirection() == Direction.LEFT)
                graphics.drawImage(bodyImageHorizontal,  current.getPosX() - WIDTH / 2,  current.getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
        }
    }

    // Draws the tail of the snake
    private void drawTail(Graphics graphics) {
        Object tail = body.get(body.size() - 1);
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
        Object head = getHead();
        for (int i = 1; i < body.size(); i++) { // Skip over 1st element, which is head itself
            if (body.get(i).getPosX() == head.getPosX() && body.get(i).getPosY() == head.getPosY())
                return true;
        }
        return false;
    }

    // returns true if any part of snake body contains coordinates posX and posY, otherwise returns false
    public boolean containsCoordinate(int posX, int posY) {
        for (Object part:body) {
            if (part.getPosX() == posX && part.getPosY() == posY)
                return true;
        }
        return false;
    }
}
