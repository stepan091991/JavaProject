package core;

import java.awt.Graphics2D;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected boolean active = true;
    protected Sprite sprite;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public abstract void update(InputHandler input);
    public abstract void draw(Graphics2D g);
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            sprite.resize(width, height);
        }
    }
    // Get/Set
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}