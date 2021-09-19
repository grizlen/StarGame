package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekbrains.base.SpritePool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds);
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
