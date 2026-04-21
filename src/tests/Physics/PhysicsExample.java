package tests.Physics;

import core.*;
import systems.PhysicsSystem;
import utils.GameBuilder;

import java.awt.*;
import static tests.Physics.PhysicsExample.*;

public class PhysicsExample {
    /*
    Простой код, демонстрирующий работу системы физики.
    Управление:
    W - прыжок
    A - движение влево
    D - движение вправо

    На данный момент реализованы функции:
    1. Применения импульса к объекту,
    2. Применения постоянной силы к объекту,
    3. Автоматические расчёты гравитации и векторов движения.
     */

    static PhysicsSystem physics;

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
        physics = builder.getPhysics();

        // Создаём и добавляем кубики в обработчик мира игры.
        FallingCube cube = new FallingCube(600, 200);
        world.addObject(cube);

        FallingCube cube2 = new FallingCube(600, 50);
        world.addObject(cube2);

        // Создаём и добавляем пустой объект в обработчик мира игры.
        AllUpdateObject object = new AllUpdateObject(0, 0);
        world.addObject(object);

        // Устанавливаем массу кубиков.
        cube.setMass(1);
        cube2.setMass(1.5);

        // Регистрируем кубики в физической системе
        physics.registerBody(cube);
        physics.registerBody(cube2);

        // Настраиваем гравитацию
        physics.setGravity(0.0, 0.1);

        // Запускаем игру
        GameFrame frame = new GameFrame(panel, "test");
        panel.start();

        System.out.println("Game started! Press ESC to exit.");

    }
}

// Класс пустого объекта, для обновления ввода и физики.
class AllUpdateObject extends GameObject{
    public AllUpdateObject(int x, int y) {
        super(x, y, 0, 0);
    }

    @Override
    public void update(InputHandler input) {
        physics.update();
        input.update();
    }

    @Override
    public void draw(Graphics2D g) {}
}

// Объявляем класс для наших кубиков.
class FallingCube extends GameObject {

    public FallingCube(int x, int y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update(InputHandler input) {
        // Проверяем, не упал ли кубик за границу(низ окна)
        if (y + height >= 600) {
            // Если упал, останавливаем движение по вертикальной оси и проверяем, нажата ли клавиша вверх.
            physics.stopMovementY(this);
            if(input.onKeyPressed("UP")) {
                // Если нажата, даём кубику вертикальный импульс.
                physics.applyImpulse(this, new PhysicsSystem.Vector2D(0, -10));
            }
        }

        // Проверка правой и левой границы окна.
        if (x <= 0){
            // Если вышли за правую, даём импульс влево.
            physics.applyImpulse(this, new PhysicsSystem.Vector2D(5, 0));
        }
        if(x >= 800 - width){
            // Если вышли за левую, даём импульс вправо.
            physics.applyImpulse(this, new PhysicsSystem.Vector2D(-5, 0));
        }

        // Движение кубика
        if(input.isPressed("LEFT")){
            physics.applyForce(this, new PhysicsSystem.Vector2D(-0.3, 0));
        }
        if(input.isPressed("RIGHT")){
            physics.applyForce(this, new PhysicsSystem.Vector2D(0.3, 0));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Рисуем кубик
        if (this.mass == 1)
        {
            // С массой равной 1, белым.
            g.setColor(Color.WHITE);
        }else{
            // С любой другой массой, красным.
            g.setColor(Color.RED);
        }
        g.fillRect(x, y, width, height);
    }
}
