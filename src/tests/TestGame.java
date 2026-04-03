package tests;

import core.*;
import systems.ParticleSystem;
import systems.PhysicsSystem;
import utils.GameBuilder;
import java.awt.*;

import static tests.TestGame.*;

public class TestGame {
    static ParticleSystem particles;
    static PhysicsSystem physics;
    static Sprite player;
    public static void main(String[] args) {
        // Создаем игру с помощью GameBuilder
        GameBuilder builder = new GameBuilder()
                .setSize(800, 600)  // Размер окна
                .setFPS(60)         // Целевой FPS
                .withInput()        // Подключаем обработку ввода
                .withPhysics();     // Подключаем обработку физики

        // Получаем компоненты игры
        GamePanel panel = builder.build();
        GameWorld world = builder.getWorld();
        InputHandler input = builder.getInput();
        physics = builder.getPhysics();

        // Создаем и добавляем простой объект
        SimpleObject obj = new SimpleObject(400, 300);
        player = new Sprite("/tests/test.gif");
        player.resize(256,256);
        obj.setSprite(player);
        world.addObject(obj);

        FallingCube cube = new FallingCube(600, 200);
        world.addObject(cube);

        FallingCube cube2 = new FallingCube(600, 50);
        world.addObject(cube2);

        // Регистрируем кубик в физической системе
        physics.registerBody(cube);
        physics.registerBody(cube2);

        // Настраиваем гравитацию
        physics.setGravity(0.2, 0);

        particles = new ParticleSystem();

        // Запускаем игру
        GameFrame frame = new GameFrame(panel, "test");
        panel.start();

        System.out.println("Game started! Press ESC to exit.");

    }

}

//Объект игрока
class SimpleObject extends GameObject {

    public SimpleObject(int x, int y) {
        super(x, y, 128, 128);
    }

    @Override
    public void update(InputHandler input) {
        // Движение объекта
        if (input != null) {
            int speed = 5;
            if (input.isPressed("LEFT")) x -= speed;
            if (input.isPressed("RIGHT")) x += speed;
            if (input.isPressed("UP")) y -= speed;
            if (input.isPressed("DOWN")) y += speed;
            if (input.isPressed("EXIT")) System.exit(0);
        }
        sprite.setPosition(x, y);
        physics.update();

        // Границы
        x = Math.max(0, Math.min(750, x));
        y = Math.max(0, Math.min(550, y));
    }

    @Override
    public void draw(Graphics2D g) {
        // Рисуем квадрат
        player.draw(g);
        //g.setColor(Color.CYAN);
        //g.fillRect(x, y, width, height);
        //g.setColor(Color.BLUE);
        //g.drawRect(x, y, width, height);


        // Проверка партиклов
        particles.emit(0, 0, 5, ParticleSystem.ParticleType.BLOOD);
        particles.update();
        particles.draw(g);

        // Рисуем текст
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Move with WASD/Arrows", 20, 50);
        g.drawString("Press ESC to exit", 20, 80);
    }
}

class FallingCube extends GameObject {

    public FallingCube(int x, int y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update(InputHandler input) {
        // Простые границы (чтобы кубик не упал за экран)
        if (y + height >= 550) {
            y = 550;
        }

        // Ограничение по X
        x = Math.max(0, Math.min(750, x));

    }

    @Override
    public void draw(Graphics2D g) {
        // Рисуем кубик
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLUE);
        g.drawRect(x, y, width, height);

        // Отображаем информацию
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Position Y: " + y, 20, 70);
    }
}