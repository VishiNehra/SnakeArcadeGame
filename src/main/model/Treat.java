package main.model;

import main.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Treat extends Object {

    public static final int WIDTH = GamePanel.CHECKER_SIZE;
    public static final int HEIGHT = GamePanel.CHECKER_SIZE;
    private Image IMAGE;

    // Constructs a treat at given coordinates and loads its image
    public Treat(int posX, int posY) {
        super(posX, posY, WIDTH, HEIGHT);
        try {
            IMAGE = ImageIO.read(new File("C:\\Users\\vishw\\IdeaProjects\\Snake Arcade Game\\images\\apple.png"));
        } catch (IOException e) {
        }
    }

    // Draws the Object on the given graphics
    public void draw(Graphics g) {
        g.drawImage(IMAGE, getPosX() - WIDTH / 2, getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
    }
}
