package core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
    // Базовые параметры
    public int width = 800;
    public int height = 600;
    public int targetFPS = 60;
    protected Thread gameThread;
    protected volatile boolean running = false;

    // Опциональные системы
    protected InputHandler inputHandler;
    protected GameWorld gameWorld;

    public GamePanel() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    // Методы для подключения опциональных систем
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
        if (handler != null) {
            addKeyListener(handler);
        }
    }

    public void setGameWorld(GameWorld world) {
        this.gameWorld = world;
    }

    // Запуск игры
    public void start() {
        if (gameThread == null) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stop() {
        running = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        double optimalTime = 1000000000.0 / targetFPS;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / optimalTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    protected void update() {
        if (gameWorld != null) {
            gameWorld.update(inputHandler);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (gameWorld != null) {
            gameWorld.draw(g2d);
        }

        g2d.dispose();
    }
}