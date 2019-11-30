package main.model;

import com.sun.javafx.scene.traversal.Direction;

public class Figure {

    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected Direction direction;

    // Constructs an object with given position and size
    public Figure(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    // Constructs an object with given position, size and direction
    public Figure(int posX, int posY, int width, int height, Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    protected void setPosX(int x) {
        posX = x;
    }

    protected void setPosY(int y) {
        posY = y;
    }

    protected void incrementPosX(int dx) {
        posX += dx;
    }

    protected void incrementPosY(int dy) {
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
}
