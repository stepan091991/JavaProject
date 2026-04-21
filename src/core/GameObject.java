package core;

import java.awt.Graphics2D;

public abstract class GameObject {
    /*
    Класс игрового объекта, реализует основные методы игрового объекта для наследования в самописных классах.
     */
    protected int x, y;
    protected int width, height;
    protected boolean active = true;
    protected Sprite sprite;
    protected double mass;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = 1;
    }
    public abstract void update(InputHandler input);
    /*
    Функция, вызываемая при обновлении объекта(обработка движения и т.д.)
     */

    public abstract void draw(Graphics2D g);
    /*
    Функция, вызываемая для отрисовки объекта в окне игры.
     */

    public void setSprite(Sprite sprite) {
        /*
        Устанавливает sprite(изображение) объекта.
         */
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
    public void setMass(double mass) { this.mass = mass;}
    public double getMass() {return this.mass;}
}