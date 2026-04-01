package systems;

import core.GameObject;
import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {
    private List<PhysicsBody> bodies;
    private Vector2D gravity;
    private boolean enabled = true;

    public PhysicsSystem() {
        this.bodies = new ArrayList<>();
        this.gravity = new Vector2D(0, 0.5);
    }

    public void registerBody(GameObject obj) {
        PhysicsBody body = new PhysicsBody(obj);
        bodies.add(body);
    }

    public void unregisterBody(GameObject obj) {
        bodies.removeIf(body -> body.object == obj);
    }

    public void update() {
        if (!enabled) return;

        for (PhysicsBody body : bodies) {
            if (body.object.isActive()) {
                body.velocity.add(gravity);

                int newX = body.object.getX() + (int) body.velocity.x;
                int newY = body.object.getY() + (int) body.velocity.y;
                body.object.setX(newX);
                body.object.setY(newY);
            }
        }
    }

    public void applyForce(GameObject obj, Vector2D force) {
        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.velocity.add(force);
                break;
            }
        }
    }

    public void setGravity(double x, double y) {
        this.gravity = new Vector2D(x, y);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void clear() {
        bodies.clear();
    }

    private static class PhysicsBody {
        GameObject object;
        Vector2D velocity;

        PhysicsBody(GameObject object) {
            this.object = object;
            this.velocity = new Vector2D(0, 0);
        }
    }

    public static class Vector2D {
        public double x, y;

        public Vector2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void add(Vector2D other) {
            this.x += other.x;
            this.y += other.y;
        }
    }
}