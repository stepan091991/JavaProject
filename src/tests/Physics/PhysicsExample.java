package tests.Physics;

import core.*;
import systems.PhysicsSystem;
import utils.GameBuilder;

import java.awt.*;
import static tests.Physics.PhysicsExample.*;

public class PhysicsExample {
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
        InputHandler input = builder.getInput();
        physics = builder.getPhysics();

        FallingCube cube = new FallingCube(600, 200);
        world.addObject(cube);

        FallingCube cube2 = new FallingCube(600, 50);
        world.addObject(cube2);

        AllUpdateObject object = new AllUpdateObject(0, 0);
        world.addObject(object);
        cube2.setMass(2);

        // Регистрируем кубик в физической системе
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
class FallingCube extends GameObject {

    public FallingCube(int x, int y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update(InputHandler input) {
        // Простые границы (чтобы кубик не упал за экран)
        if (y + height >= 600) {
            physics.stopMovementY(this);
            if(input.onKeyPressed("UP")) {
                physics.applyImpulse(this, new PhysicsSystem.Vector2D(0, -10));
            }
        }

        if (x <= 0){
            physics.applyImpulse(this, new PhysicsSystem.Vector2D(5, 0));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Рисуем кубик
        if (this.mass == 1)
        {
            g.setColor(Color.WHITE);
        }else{
            g.setColor(Color.RED);
        }
        g.fillRect(x, y, width, height);

        // Отображаем информацию
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.CYAN);
        g.drawString("Position Y: " + y, 20 + (int)this.mass * 5, 70);
    }
}
