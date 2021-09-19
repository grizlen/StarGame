package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.global.Config;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitTer;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;
    private Texture bg;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool mainBulletPool;
    private BulletPool enemyBulletPool;
    private Music bgMusic;
    private EnemyEmitTer enemyEmitTer;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

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

        enemyEmitTer = new EnemyEmitTer(enemyPool, worldBounds);

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
        return mainShip.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return mainShip.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
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
            enemyEmitTer.generate(delta);
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
                }
            }
        }
        bulletList = enemyBulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (mainShip.isCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        mainBulletPool.freeAllDetroyedActiveSprites();
        explosionPool.freeAllDetroyedActiveSprites();
        enemyPool.freeAllDetroyedActiveSprites();
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
        batch.end();
    }
}
