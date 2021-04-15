package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.java.GraphicView.FRAME_SIZE;
import static main.java.GraphicView.TILE_SIZE;

public class Sign {
    private Image image;
    private final Point coordinates;

    public Sign(FIELD_STATE sign, Point coordinates) {
        if (sign.equals(FIELD_STATE.X)) {
            loadImage("xSign.png");
        } else {
            loadImage("oSign.png");
        }
        this.coordinates = coordinates;
    }

    /**
     * Loads image of sign with given name
     * @param imageName name of file with image of result sign
     */
    private void loadImage(String imageName) {
        try {
            image = ImageIO.read(new File("src/main/resources/img/" + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws sign
     */
    public void draw(Graphics2D g) {
        int x = coordinates.x * TILE_SIZE + FRAME_SIZE * (coordinates.x + 1);
        int y = coordinates.y * TILE_SIZE + FRAME_SIZE * (coordinates.y + 1);
        if (coordinates.x == 1) { x += 3;}
        if (coordinates.x == 2) { x += 6;}
        if (coordinates.y == 1) { y += 3;}
        if (coordinates.y == 2) { y += 6;}
        g.drawImage(image, x, y, x + TILE_SIZE, y + TILE_SIZE, 0, 0, 164, 164,
                null);
    }

    public int getX() {
        return coordinates.x;
    }

    public int getY() {
        return coordinates.y;
    }
}
