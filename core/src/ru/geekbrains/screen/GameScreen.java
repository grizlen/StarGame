package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEMitTer;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;
    private Texture bg;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool mainBulletPool;
    private Music bgMusic;
    private EnemyEMitTer enemyEMitTer;
    private EnemyPool enemyPool;

    @Override
    public void show() {
        super.show();

        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; stars[i++] = new Star(atlas));

        mainBulletPool = new BulletPool(Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav")));
        mainShip = new MainShip(atlas, mainBulletPool);

        BulletPool enemyBulletPool = new BulletPool(Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav")));
        enemyPool = new EnemyPool(enemyBulletPool, worldBounds);
        enemyEMitTer = new EnemyEMitTer(atlas, enemyPool, worldBounds);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
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
        atlas.dispose();
        mainBulletPool.dispose();
        enemyPool.dispose();
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
        mainShip.update(delta);
        mainBulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEMitTer.generate(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        mainBulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    private void freeAllDestroyed() {
        mainBulletPool.freeAllDetroyedActiveSprites();
        enemyPool.freeAllDetroyedActiveSprites();
    }
}
