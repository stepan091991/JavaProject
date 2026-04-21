package tests.Collision;

import core.*;
import utils.GameBuilder;
import systems.CollisionSystem;

import java.awt.*;

import static tests.Collision.CollisionExample.*;

public class CollisionExample {
     /*
    Простой код, демонстрирующий работу столкновений.
    */

    static CollisionSystem collision;
    static Color staticCubeColor = Color.BLUE;

    public static void main(String[] args) {
        // Создаем игру с помощью GameBuilder
        GameBuilder builder = new GameBuilder()
                .setSize(800, 600)  // Размер окна
                .setFPS(60)      // Целевой FPS
                .withInput()
                .withCollision();

        // Получаем компоненты игры
        GamePanel panel = builder.build();
        GameWorld world = builder.getWorld();
        collision = builder.getCollision();

        // Создаём игровые объекты
        StaticCube staticCube = new StaticCube(500, 100);
        world.addObject(staticCube);

        MovingCube movingCube = new MovingCube(200, 100);
        world.addObject(movingCube);

        // Добавляем объекты в обработчик
        collision.registerObject(staticCube, 0, 0, staticCube.getWidth(), staticCube.getHeight());
        collision.registerObject(movingCube, 0, 0, movingCube.getWidth(), movingCube.getHeight());

        // Запускаем игру
        GameFrame frame = new GameFrame(panel, "test");
        panel.start();

        System.out.println("Game started! Press ESC to exit.");
    }
}

class StaticCube extends GameObject {
    public StaticCube(int x, int y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update(InputHandler input) {
        staticCubeColor = Color.BLUE;
        input.update();
        collision.checkCollisions();

        // Проверяем столкновения объектов(возвращает столкнувшиеся объекты)
        collision.addListener((obj1, obj2) -> {
            staticCubeColor = Color.YELLOW;
        });
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(CollisionExample.staticCubeColor);
        g.fillRect(x, y, width, height);
    }
}

class MovingCube extends GameObject {
    public MovingCube(int x, int y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update(InputHandler input) {
        // Перемещаем объект с курсором мышки
        x = input.getMouseX() - width / 2;
        y = input.getMouseY() - height / 2;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
