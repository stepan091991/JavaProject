package systems;

import core.GameObject;
import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {
    /*
    Класс обработки физики.
     */

    private List<PhysicsBody> bodies;
    private Vector2D gravity;
    private boolean enabled = true;
    private double globalMassMultiplier = 1.0;

    public PhysicsSystem() {
        this.bodies = new ArrayList<>();
        this.gravity = new Vector2D(0, 0.5);
    }

    public void registerBody(GameObject obj) {
        /*
        Добавить объект к расчёту физики.
         */

        registerBody(obj, obj.getMass()); // По умолчанию масса = 1.0
    }

    public void registerBody(GameObject obj, double mass) {
        /*
        Добавить объект к расчёту физики с указанием массы.
         */

        PhysicsBody body = new PhysicsBody(obj, mass);
        bodies.add(body);
    }

    public void unregisterBody(GameObject obj) {
        /*
        Убрать объект из расчёта физики.
         */

        bodies.removeIf(body -> body.object == obj);
    }

    public void update() {
        if (!enabled) return;

        for (PhysicsBody body : bodies) {
            if (body.object.isActive()) {

                Vector2D gravityForce = new Vector2D(
                        gravity.x * body.mass * globalMassMultiplier,
                        gravity.y * body.mass * globalMassMultiplier
                );

                body.velocity.add(gravityForce);

                applyDamping(body);

                int newX = body.object.getX();
                int newY = body.object.getY();

                if (!body.freezeX) {
                    newX = body.object.getX() + (int) body.velocity.x;
                }

                if (!body.freezeY) {
                    newY = body.object.getY() + (int) body.velocity.y;
                }

                body.object.setX(newX);
                body.object.setY(newY);
            }
        }
    }

    private void applyDamping(PhysicsBody body) {
        /*
        Применяет затухание скорости.
        */

        if (body.damping > 0) {
            body.velocity.x *= (1.0 - body.damping);
            body.velocity.y *= (1.0 - body.damping);

            // Останавливаем движение при очень малой скорости
            if (Math.abs(body.velocity.x) < 0.01) body.velocity.x = 0;
            if (Math.abs(body.velocity.y) < 0.01) body.velocity.y = 0;
        }
    }

    public void applyForce(GameObject obj, Vector2D force) {
        /*
        Приложить усилие к объекту.
         */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                Vector2D acceleration = new Vector2D(
                        force.x / body.mass,
                        force.y / body.mass
                );
                body.velocity.add(acceleration);
                break;
            }
        }
    }

    public void applyImpulse(GameObject obj, Vector2D impulse) {
        /*
        Приложить мгновенный импульс (мгновенное изменение скорости).
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.velocity.add(impulse);
                break;
            }
        }
    }

    public void stopMovementX(GameObject obj) {
        /*
        Остановить движение объекта по оси X.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.velocity.x = 0;
                break;
            }
        }
    }

    public void stopMovementY(GameObject obj) {
        /*
        Остановить движение объекта по оси Y.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.velocity.y = 0;
                break;
            }
        }
    }

    public void stopMovement(GameObject obj) {
        /*
        Полностью остановить движение объекта.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.velocity.x = 0;
                body.velocity.y = 0;
                break;
            }
        }
    }

    public void freezeAxisX(GameObject obj, boolean freeze) {
        /*
        Заморозить/разморозить движение по оси X.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.freezeX = freeze;
                if (freeze) {
                    body.velocity.x = 0;
                }
                break;
            }
        }
    }

    public void freezeAxisY(GameObject obj, boolean freeze) {
        /*
        Заморозить/разморозить движение по оси Y.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.freezeY = freeze;
                if (freeze) {
                    body.velocity.y = 0;
                }
                break;
            }
        }
    }

    public void setMass(GameObject obj, double mass) {
        /*
        Установить массу объекта.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.mass = Math.max(0.1, mass);
                break;
            }
        }
    }

    public double getMass(GameObject obj) {
        /*
        Получить массу объекта.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                return body.mass;
            }
        }
        return 1.0;
    }

    public void setDamping(GameObject obj, double damping) {
        /*
        Установить демпфирование (затухание) для объекта (0-1).
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                body.damping = Math.max(0, Math.min(1, damping));
                break;
            }
        }
    }

    public void setGravity(double x, double y) {
        this.gravity = new Vector2D(x, y);
    }

    public void setGlobalMassMultiplier(double multiplier) {
        /*
        Установить глобальный множитель массы для всех объектов.
        */

        this.globalMassMultiplier = Math.max(0.1, multiplier);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void clear() {
        bodies.clear();
    }

    public Vector2D getVelocity(GameObject obj) {
        /*
        Получить текущую скорость объекта.
        */

        for (PhysicsBody body : bodies) {
            if (body.object == obj) {
                return new Vector2D(body.velocity.x, body.velocity.y);
            }
        }
        return new Vector2D(0, 0);
    }

    private static class PhysicsBody {
        GameObject object;
        Vector2D velocity;
        double mass;
        double damping = 0.02;
        boolean freezeX = false;
        boolean freezeY = false;

        PhysicsBody(GameObject object, double mass) {
            this.object = object;
            this.mass = Math.max(0.1, mass); // Минимальная масса 0.1
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

        public double magnitude() {
            return Math.sqrt(x * x + y * y);
        }
    }
}