package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener {
    /*
    Класс, отвечающий за обработку ввода с клавиатуры.
     */

    private Map<Integer, Boolean> keys;
    private Map<String, Integer> keyBindings;

    public InputHandler() {
        this.keys = new HashMap<>();
        this.keyBindings = new HashMap<>();

        setDefaultBindings();
    }

    private void setDefaultBindings() {
        /*
        Стандартные назначения клавиш.
         */

        bind("UP", KeyEvent.VK_W, KeyEvent.VK_UP);
        bind("DOWN", KeyEvent.VK_S, KeyEvent.VK_DOWN);
        bind("LEFT", KeyEvent.VK_A, KeyEvent.VK_LEFT);
        bind("RIGHT", KeyEvent.VK_D, KeyEvent.VK_RIGHT);
        bind("JUMP", KeyEvent.VK_SPACE);
        bind("ACTION", KeyEvent.VK_E);
        bind("EXIT", KeyEvent.VK_ESCAPE);
    }

    public void bind(String action, int... keyCodes) {
        /*
        Функция для привязки клавиши к какому-либо действию.
         */

        for (int keyCode : keyCodes) {
            keyBindings.put(action + "_" + keyCode, keyCode);
        }
    }

    public boolean isPressed(String action) {
        /*
        Функция, срабатывает пока нажата нужная клавиша.
         */

        for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (isKeyPressed(entry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}