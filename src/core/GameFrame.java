package core;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    /*
    Класс окна игры, создаёт и управляет окном игры.
     */

    public GameFrame(GamePanel panel, String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}