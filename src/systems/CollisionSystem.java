package systems;

import core.GameObject;
import core.GameWorld;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class CollisionSystem {
    private List<CollisionBox> boxes;
    private List<CollisionListener> listeners;
    private boolean enabled = true;

    public CollisionSystem() {
        this.boxes = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public void registerObject(GameObject obj, int x, int y, int width, int height) {
        CollisionBox box = new CollisionBox(obj, x, y, width, height);
        boxes.add(box);
    }

    public void unregisterObject(GameObject obj) {
        boxes.removeIf(box -> box.parent == obj);
    }

    public void addListener(CollisionListener listener) {
        listeners.add(listener);
    }

    public void checkCollisions() {
        if (!enabled) return;

        for (CollisionBox box : boxes) {
            box.updatePosition();
        }

        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                CollisionBox box1 = boxes.get(i);
                CollisionBox box2 = boxes.get(j);

                if (box1.parent.isActive() && box2.parent.isActive() &&
                        box1.intersects(box2)) {
                    notifyCollision(box1.parent, box2.parent);
                }
            }
        }
    }

    private void notifyCollision(GameObject obj1, GameObject obj2) {
        for (CollisionListener listener : listeners) {
            listener.onCollision(obj1, obj2);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void clear() {
        boxes.clear();
        listeners.clear();
    }

    private static class CollisionBox {
        private GameObject parent;
        private Rectangle bounds;
        private int offsetX, offsetY;

        public CollisionBox(GameObject parent, int x, int y, int width, int height) {
            this.parent = parent;
            this.offsetX = x;
            this.offsetY = y;
            this.bounds = new Rectangle(x, y, width, height);
        }

        public void updatePosition() {
            bounds.x = parent.getX() + offsetX;
            bounds.y = parent.getY() + offsetY;
        }

        public boolean intersects(CollisionBox other) {
            return bounds.intersects(other.bounds);
        }
    }

    public interface CollisionListener {
        void onCollision(GameObject obj1, GameObject obj2);
    }
}