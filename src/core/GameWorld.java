package core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {
    protected List<GameObject> objects;

    public GameWorld() {
        this.objects = new ArrayList<>();
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void removeObject(GameObject obj) {
        objects.remove(obj);
    }

    public void update(InputHandler input) {
        for (GameObject obj : objects) {
            if (obj.isActive()) {
                obj.update(input);
            }
        }
    }

    public void draw(Graphics2D g) {
        for (GameObject obj : objects) {
            if (obj.isActive()) {
                obj.draw(g);
            }
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void clear() {
        objects.clear();
    }
}