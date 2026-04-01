package core;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(GamePanel panel) {
        setTitle("Stepan4ek engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
