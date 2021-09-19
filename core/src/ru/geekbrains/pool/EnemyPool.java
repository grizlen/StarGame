package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpitePool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyPool extends SpitePool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bulletPool.dispose();
    }

    @Override
    public void updateActiveSprites(float delta) {
        super.updateActiveSprites(delta);
        bulletPool.updateActiveSprites(delta);
    }

    @Override
    public void drawActiveSprites(SpriteBatch batch) {
        super.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
    }
}
