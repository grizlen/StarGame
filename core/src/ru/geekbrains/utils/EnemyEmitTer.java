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

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer < GENERATE_INTERVAL) {
            return;
        }
        generateTimer = 0;
        EnemyShip ship = enemyPool.obtain();
        float type = (float) Math.random();
        if (type < 0.5) {
            setSmallEnemy(ship);
        } else if (type < 0.8) {
            setMediumEnemy(ship);
        } else {
            setBigEnemy(ship);
        }
        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
    }

    private void setSmallEnemy(EnemyShip ship) {
        ship.setShipInfo(ShipInfo.SMALL_ENEMY_SHIP);
        ship.setBulletInfo(BulletInfo.BULLET_SMALL_ENEMY_SHIP);

    }

    private void setMediumEnemy(EnemyShip ship) {
        ship.setShipInfo(ShipInfo.MEDIUM_ENEMY_SHIP);
        ship.setBulletInfo(BulletInfo.BULLET_MEDIUM_ENEMY_SHIP);
    }

    private void setBigEnemy(EnemyShip ship) {
        ship.setShipInfo(ShipInfo.BIG_ENEMY_SHIP);
        ship.setBulletInfo(BulletInfo.BULLET_BIG_ENEMY_SHIP);
    }
}
