# Modular Game Engine for Java Swing

Легковесный модульный 2D игровой движок на чистой Java Swing.

---

## О проекте

Движок представляет собой простую и расширяемую основу для создания 2D игр. Ключевые принципы:

- **Модульность** — каждая система подключается независимо
- **Простота** — минимум абстракций, понятный код
- **Производительность** — оптимизированный игровой цикл

### Требования
- Java 8 или выше
- Swing (входит в стандартную поставку)

---


Базовые компоненты
GameFrame
Главное окно приложения. Простая обертка над JFrame.
GamePanel panel = new GamePanel();
GameFrame frame = new GameFrame(panel);
frame.setTitle("My Game");

GamePanel
Основной класс, управляющий игровым циклом. Работает с фиксированным FPS.

Метод	                          Описание
setGameWorld(GameWorld)	        Подключение игрового мира
setInputHandler(InputHandler)	  Подключение обработчика ввода
start()	                        Запуск игрового цикла
stop()	                        Остановка игры

GameWorld
Контейнер для всех игровых объектов. Управляет обновлением и отрисовкой.

GameWorld world = new GameWorld();
world.addObject(player);
world.addObject(enemy);
world.removeObject(bullet);

GameObject
Базовый класс для всех объектов в игре.

x, y — координаты
width, height — размеры
active — активен ли объект
sprite — спрайт

Методы:

update(InputHandler) — обновление логики
draw(Graphics2D) — отрисовка

Sprite
Класс для работы с изображениями. Поддерживает PNG, JPEG, GIF.

Sprite sprite = new Sprite("/images/hero.png");
sprite.resize(64, 64);
sprite.setPosition(x, y);
sprite.draw(graphics);

InputHandler
Обработчик клавиатурного ввода с поддержкой привязки действий.

Действие	Клавиши
UP	      W, UP
DOWN	    S, DOWN
LEFT	    A, LEFT
RIGHT	    D, RIGHT
JUMP	    SPACE
ACTION	  E
EXIT	    ESCAPE

InputHandler input = new InputHandler();
if (input.isPressed("JUMP")) {
    player.jump();
}
input.bind("SHOOT", KeyEvent.VK_F);

Опциональные модули

CollisionSystem
Система обнаружения столкновений с прямоугольными хитбоксами.

CollisionSystem collision = new CollisionSystem();
collision.registerObject(player, 10, 10, 40, 40);
collision.registerObject(coin, 0, 0, 32, 32);
collision.addListener((obj1, obj2) -> {
    if (obj2 instanceof Coin) {
        score += 10;
        world.removeObject(obj2);
    }
});
collision.checkCollisions();

PhysicsSystem
Простая система физики с гравитацией и импульсами.

PhysicsSystem physics = new PhysicsSystem();
physics.setGravity(0, 0.5);
physics.registerBody(player);
physics.applyForce(player, new Vector2D(0, -12));
physics.update();

ParticleSystem
Система частиц для спецэффектов.

ParticleSystem particles = new ParticleSystem();
particles.emit(x, y, 30, ParticleType.FIRE);
particles.emit(x, y, 5, ParticleType.SMOKE);
particles.update();
particles.draw(graphics);
Типы частиц: SMOKE, FIRE, SPARKLE, BLOOD.

AudioSystem
Система воспроизведения звуков (формат WAV).

AudioSystem audio = new AudioSystem();
audio.loadSound("jump", "/sounds/jump.wav");
audio.loadSound("bgm", "/sounds/music.wav");

audio.play("jump");
audio.loop("bgm");
audio.setVolume(0.7f);

GameBuilder
Утилита для удобной конфигурации игры.

GamePanel game = new GameBuilder()
    .setSize(1024, 768)
    .setFPS(60)
    .withInput()
    .withCollision()
    .withPhysics()
    .withParticles()
    .withAudio()
    .build();

new GameFrame(game);
game.start();
