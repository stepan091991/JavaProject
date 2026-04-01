package systems;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleSystem {
    private List<Particle> particles;
    private Random random;
    private boolean enabled = true;

    public ParticleSystem() {
        this.particles = new ArrayList<>();
        this.random = new Random();
    }

    public void emit(int x, int y, int count, ParticleType type) {
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(x, y, type, random));
        }
    }

    public void update() {
        if (!enabled) return;

        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update();
            if (p.isDead()) {
                it.remove();
            }
        }
    }

    public void draw(Graphics2D g) {
        if (!enabled) return;

        for (Particle p : particles) {
            p.draw(g);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void clear() {
        particles.clear();
    }

    public enum ParticleType {
        SMOKE, FIRE, SPARKLE, BLOOD
    }

    private static class Particle {
        float x, y;
        float vx, vy;
        int life;
        int maxLife;
        ParticleType type;
        Random random;

        Particle(int x, int y, ParticleType type, Random random) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.random = random;
            this.maxLife = 30 + random.nextInt(20);
            this.life = maxLife;

            this.vx = (random.nextFloat() - 0.5f) * 5;
            this.vy = (random.nextFloat() - 0.5f) * 5;
        }

        void update() {
            x += vx;
            y += vy;
            life--;
            vy += 0.2f;
        }

        void draw(Graphics2D g) {
            float alpha = (float) life / maxLife;
            g.fillRect((int) x, (int) y, 2, 2);
        }

        boolean isDead() {
            return life <= 0;
        }
    }
}