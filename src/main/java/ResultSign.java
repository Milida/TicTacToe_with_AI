package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.java.GraphicView.FRAME_SIZE;
import static main.java.GraphicView.TILE_SIZE;

public class ResultSign {
    private Image image;
    private int x;
    private char sign;

    public ResultSign(char sign, int coord) {
        this.sign = sign;
        this.x = coord;
        if (sign == '\\') {
            this.x = 0;
            loadImage("backslash.png");
        } else if (sign == '/'){
            loadImage("slash.png");
        } else if (sign == '|') {
            loadImage("vertical.png");
        } else if (sign == '-') {
            loadImage("horizontal.png");
        }
    }

    /**
     * Loads image of result sign
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
     * Draws result sign
     */
    public void draw(Graphics2D g) { //TODO do skrócenia długość (ilość znaków czy coś)!
        int xx = FRAME_SIZE * ( x + 1) + x * TILE_SIZE + TILE_SIZE / 2 - 10;
        if (x == 1) { xx += 3;}
        if (x == 2) { xx += 6;}
        if (sign == '\\') {
            g.drawImage(image, FRAME_SIZE, FRAME_SIZE, 598 - FRAME_SIZE, 598 - FRAME_SIZE, 0, 0, 598, 598,
                    null);
        } else if (sign == '/') {
            g.drawImage(image, FRAME_SIZE, FRAME_SIZE, 598 - FRAME_SIZE, 598 - FRAME_SIZE, 0, 0, 598, 598, null);
        } else if (sign == '|') {
            g.drawImage(image, xx, FRAME_SIZE, xx + 20, 598 - FRAME_SIZE, 0, 0, 20, 598, null);
        } else if (sign == '-') {
            g.drawImage(image, FRAME_SIZE, xx, 598 - FRAME_SIZE, xx + 20, 0, 0, 598, 20, null);
        }
    }
}
