package main.model;

import com.sun.javafx.scene.traversal.Direction;

public class Object {

    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected Direction direction;

    // Constructs an object with given position and size
    public Object(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    // Constructs an object with given position, size and direction
    public Object(int posX, int posY, int width, int height, Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    public void setPosX(int x) {
        posX = x;
    }

    public void setPosY(int y) {
        posY = y;
    }

    public void incrementPosX(int dx) {
        posX += dx;
    }

    public void incrementPosY(int dy) {
        posY += dy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
