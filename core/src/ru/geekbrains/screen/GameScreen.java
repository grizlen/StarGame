package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.global.Config;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameOverText;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.RestartButton;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitTer;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final float MARGIN = 0.01f;

    private Texture bg;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool mainBulletPool;
    private BulletPool enemyBulletPool;
    private Music bgMusic;
    private EnemyEmitTer enemyEmitter;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private RestartButton restartButton;
    private GameOverText gameOverText;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private int frags;
    private int level = 1;

    @Override
    public void show() {
        super.show();

        bg = new Texture("textures/bg.png");

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; stars[i++] = new Star());

        mainBulletPool = new BulletPool(Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav")));
        enemyBulletPool = new BulletPool(Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav")));

        explosionPool = new ExplosionPool(Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")));
        enemyPool = new EnemyPool(enemyBulletPool, explosionPool, worldBounds);

        mainShip = new MainShip(mainBulletPool, explosionPool, worldBounds);

        enemyEmitter = new EnemyEmitTer(enemyPool, worldBounds);

        restartButton = new RestartButton(false, this);
        gameOverText = new GameOverText(false);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        restartButton.resize(worldBounds);
        gameOverText.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        Config.disposeMainAtlas();
        mainBulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();

        bgMusic.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            restartButton.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            restartButton.touchUp(touch, pointer, button);
        } else {
            mainShip.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            mainBulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, level);
        } else {
            restartButton.setVisible(true);
            gameOverText.setVisible(true);
        }
    }

    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDst = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDst) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getBulletDamage() * 2);
            }
        }
        List<Bullet> bulletList = mainBulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        frags++;
                        level = frags / 10 + 1;
                        mainShip.setLevel(level);
                    }
                }
            }
        }
        bulletList = enemyBulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (mainShip.isCollision(bullet)) {
                mainShip.damage(bullet.getDamage() * ((EnemyShip) bullet.getOwner()).getLevel());
                bullet.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        mainBulletPool.freeAllDetroyedActiveSprites();
        explosionPool.freeAllDetroyedActiveSprites();
        enemyPool.freeAllDetroyedActiveSprites();
        enemyBulletPool.freeAllDetroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            mainBulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (restartButton.isVisible()) {
            restartButton.draw(batch);
        }
        if (gameOverText.isVisible()) {
            gameOverText.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void start() {
        gameOverText.setVisible(false);
        enemyPool.freeAll();
        enemyBulletPool.freeAll();
        mainBulletPool.freeAll();
        mainShip.reset();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }

}
