package core;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Sprite {
    /*
    Класс изображения игрового объекта. Отвечает за загрузку, изменение и добавление изображения к объектам.
     */

    private BufferedImage originalImage;
    private BufferedImage currentImage;
    private int x, y;
    private int width, height;

    public Sprite(String path) {
        loadImage(path);
    }

    private void loadImage(String path) {
        /*
        Функция, отвечающая за загрузку изображения
         */

        try (InputStream input = getClass().getResourceAsStream(path)) {
            if (input != null) {
                originalImage = ImageIO.read(input);
                currentImage = originalImage;
                width = originalImage.getWidth();
                height = originalImage.getHeight();
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
        }
    }

    public void resize(int newWidth, int newHeight) {
        /*
        Изменение размера изображения.
         */

        if (newWidth > 0 && newHeight > 0 && originalImage != null) {
            Image scaled = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            currentImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = currentImage.createGraphics();
            g.drawImage(scaled, 0, 0, null);
            g.dispose();
            width = newWidth;
            height = newHeight;
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {
        if (currentImage != null) {
            g.drawImage(currentImage, x, y, null);
        }
    }

    public BufferedImage getImage() {
        return currentImage;
    }
}