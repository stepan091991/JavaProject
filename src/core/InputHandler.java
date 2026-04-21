package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    /*
    Класс, отвечающий за обработку ввода с клавиатуры и мыши.
    Полностью совместим с существующим кодом.
     */

    private Map<Integer, Boolean> keys;
    private Map<Integer, Boolean> previousKeys;
    private Map<String, Integer> keyBindings;
    private Map<Integer, Boolean> mouseButtons;
    private Map<Integer, Boolean> previousMouseButtons;
    private Map<String, Integer> mouseBindings;
    private int mouseX, mouseY;
    private Component gameComponent;

    public InputHandler() {
        this.keys = new HashMap<>();
        this.previousKeys = new HashMap<>();
        this.keyBindings = new HashMap<>();

        this.mouseButtons = new HashMap<>();
        this.previousMouseButtons = new HashMap<>();
        this.mouseBindings = new HashMap<>();
        this.gameComponent = null;
        this.mouseX = 0;
        this.mouseY = 0;

        setDefaultBindings();
        setDefaultMouseBindings();
    }

    public InputHandler(Component gameComponent) {
        this();
        this.gameComponent = gameComponent;
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

    private void setDefaultMouseBindings() {
        /*
        Стандартные назначения кнопок мышки.
         */

        bindMouse("LEFT_CLICK", MouseEvent.BUTTON1);
        bindMouse("MIDDLE_CLICK", MouseEvent.BUTTON2);
        bindMouse("RIGHT_CLICK", MouseEvent.BUTTON3);
    }

    public void bind(String action, int... keyCodes) {
        /*
        Функция для привязки клавиши клавиатуры к какому-либо действию.
         */

        for (int keyCode : keyCodes) {
            keyBindings.put(action + "_" + keyCode, keyCode);
        }
    }

    public void bindMouse(String action, int... mouseButtons) {
        /*
        Функция для привязки клавиши мышки к какому-либо действию.
         */

        for (int button : mouseButtons) {
            mouseBindings.put(action + "_" + button, button);
        }
    }

    public boolean isPressed(String action) {
        /*
        Функция, срабатывает пока нажата нужная клавиша.
         */

        // Клавиатура
        for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (isKeyPressed(entry.getValue())) {
                    return true;
                }
            }
        }

        // Мышка
        for (Map.Entry<String, Integer> entry : mouseBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (isMouseButtonPressed(entry.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean onKeyPressed(String action) {
        /*
        Функция, срабатывает только один раз при нажатии клавиши.
         */

        // Клавиатура
        for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (wasKeyJustPressed(entry.getValue())) {
                    return true;
                }
            }
        }

        // Мышка
        for (Map.Entry<String, Integer> entry : mouseBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (wasMouseButtonJustPressed(entry.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean onKeyReleased(String action) {
        /*
        Функция, срабатывает только один раз при отпускании клавиши.
         */

        // Клавиатура
        for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (wasKeyJustReleased(entry.getValue())) {
                    return true;
                }
            }
        }

        // Мышка
        for (Map.Entry<String, Integer> entry : mouseBindings.entrySet()) {
            if (entry.getKey().startsWith(action + "_")) {
                if (wasMouseButtonJustReleased(entry.getValue())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    public boolean wasKeyJustPressed(int keyCode) {
        boolean current = keys.getOrDefault(keyCode, false);
        boolean previous = previousKeys.getOrDefault(keyCode, false);
        return current && !previous;
    }

    public boolean wasKeyJustReleased(int keyCode) {
        boolean current = keys.getOrDefault(keyCode, false);
        boolean previous = previousKeys.getOrDefault(keyCode, false);
        return !current && previous;
    }

    public boolean isMouseButtonPressed(int button) {
        return mouseButtons.getOrDefault(button, false);
    }

    public boolean wasMouseButtonJustPressed(int button) {
        boolean current = mouseButtons.getOrDefault(button, false);
        boolean previous = previousMouseButtons.getOrDefault(button, false);
        return current && !previous;
    }

    public boolean wasMouseButtonJustReleased(int button) {
        boolean current = mouseButtons.getOrDefault(button, false);
        boolean previous = previousMouseButtons.getOrDefault(button, false);
        return !current && previous;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setGameComponent(Component component) {
        this.gameComponent = component;
    }

    public void update() {
        /*
        Обновление состояния клавиш.
         */

        previousKeys.clear();
        previousKeys.putAll(keys);

        // Новое обновление для мыши
        previousMouseButtons.clear();
        previousMouseButtons.putAll(mouseButtons);
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

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons.put(e.getButton(), true);
        updateMousePosition(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons.put(e.getButton(), false);
        updateMousePosition(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateMousePosition(e);
    }

    private void updateMousePosition(MouseEvent e) {
        if (gameComponent != null) {
            java.awt.Point point = e.getPoint();
            mouseX = point.x;
            mouseY = point.y;
        } else {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
}