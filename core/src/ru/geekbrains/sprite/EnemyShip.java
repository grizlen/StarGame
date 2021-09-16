package ru.geekbrains.sprite;

import ru.geekbrains.base.BaseShip;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class EnemyShip extends BaseShip {

    private boolean isInit;

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        setBulletPool(bulletPool);
        setWorldBounds(worldBounds);
    }

    @Override
    public void update(float delta) {
        if (isInit && getTop() < worldBounds.getTop()){
            shootTime = shootInterval + 0.01f;
        }
        isInit = getTop() > worldBounds.getTop();
        if (isInit) {
            shootTime = 0;
        }
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
    }
}
