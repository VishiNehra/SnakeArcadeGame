package main.model;

import main.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Treat extends Figure {

    private static final int WIDTH = GamePanel.CHECKER_SIZE;
    private static final int HEIGHT = GamePanel.CHECKER_SIZE;
    private Image IMAGE;

    // Constructs a treat at given coordinates and loads its image
    public Treat(int posX, int posY) {
        super(posX, posY, WIDTH, HEIGHT);
        loadImage();
    }

    private void loadImage() {
        try {
            IMAGE = ImageIO.read(new File("images\\apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Draws the Figure on the given graphics
    public void draw(Graphics g) {
        g.drawImage(IMAGE, getPosX() - WIDTH / 2, getPosY() - HEIGHT / 2, WIDTH, HEIGHT, null);
    }
}
