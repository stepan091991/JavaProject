package tests.GameBaseExample;

import core.*;
import utils.GameBuilder;

import java.awt.*;

public class GameBaseExample {
    /*
    Простой код, демонстрирующий основной начальный код для пустой игры.
    */

    public static void main(String[] args) {
        // Создаем игру с помощью GameBuilder
        GameBuilder builder = new GameBuilder()
                .setSize(800, 600)  // Размер окна
                .setFPS(60);      // Целевой FPS

        // Получаем компоненты игры
        GamePanel panel = builder.build();
        GameWorld world = builder.getWorld();
        // Запускаем игру
        GameFrame frame = new GameFrame(panel, "test");
        panel.start();

        System.out.println("Game started! Press ESC to exit.");
    }
}

