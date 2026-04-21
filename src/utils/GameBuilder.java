package utils;

import core.*;
import systems.*;

public class GameBuilder {
    private GamePanel panel;
    private GameWorld world;
    private InputHandler input;
    private CollisionSystem collision;
    private PhysicsSystem physics;
    private ParticleSystem particles;
    private SoundSystem sound;

    public GameBuilder() {
        this.panel = new GamePanel();
        this.world = new GameWorld();
        panel.setGameWorld(world);
    }

    public GameBuilder withInput() {
        this.input = new InputHandler(this.panel);
        panel.setInputHandler(input);
        panel.addMouseListener(this.input);
        panel.addMouseMotionListener(this.input);
        return this;
    }

    public GameBuilder withCollision() {
        this.collision = new CollisionSystem();
        return this;
    }

    public GameBuilder withPhysics() {
        this.physics = new PhysicsSystem();
        return this;
    }

    public GameBuilder withParticles() {
        this.particles = new ParticleSystem();
        return this;
    }

    public GameBuilder withSound() {
        this.sound = new SoundSystem();
        return this;
    }

    public GameBuilder setSize(int width, int height) {
        panel.width = width;
        panel.height = height;
        return this;
    }

    public GameBuilder setFPS(int fps) {
        panel.targetFPS = fps;
        return this;
    }

    public GamePanel build() {
        return panel;
    }

    public GameWorld getWorld() { return world; }
    public InputHandler getInput() { return input; }
    public CollisionSystem getCollision() { return collision; }
    public PhysicsSystem getPhysics() { return physics; }
    public ParticleSystem getParticles() { return particles; }
    public SoundSystem getSound() { return sound; }
}