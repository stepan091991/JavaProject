package core;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(GamePanel panel) {
        setTitle("Modular Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}