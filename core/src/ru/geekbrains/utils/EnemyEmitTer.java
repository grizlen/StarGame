package ru.geekbrains.utils;

import com.badlogic.gdx.math.MathUtils;

import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.info.ShipInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyEmitTer {

    private static final float GENERATE_INTERVAL = 4f;
    private final EnemyPool enemyPool;
    private final Rect worldBounds;

    private float generateTimer;

    public EnemyEmitTer(EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta, int level) {
        generateTimer += delta;
        if (generateTimer < GENERATE_INTERVAL) {
            return;
        }
        generateTimer = 0;
        EnemyShip ship = enemyPool.obtain();
        ship.setLevel(level);
        float type = (float) Math.random();
        if (type < 0.5) {
            ship.setShipInfo(ShipInfo.SMALL_ENEMY_SHIP);
            ship.setBulletInfo(BulletInfo.BULLET_SMALL_ENEMY_SHIP);
        } else if (type < 0.8) {
            ship.setShipInfo(ShipInfo.MEDIUM_ENEMY_SHIP);
            ship.setBulletInfo(BulletInfo.BULLET_MEDIUM_ENEMY_SHIP);
        } else {
            ship.setShipInfo(ShipInfo.BIG_ENEMY_SHIP);
            ship.setBulletInfo(BulletInfo.BULLET_BIG_ENEMY_SHIP);
        }
        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
    }
}
