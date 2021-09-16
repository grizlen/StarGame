package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.global.Config;
import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyEmitTer {

    private static final float GENERATE_INTERVAL = 1f;
    private final EnemyPool enemyPool;
    private final Rect worldBounds;
    private final TextureRegion[] smallRegions, mediumRegions, bigRegions;
    private final TextureRegion bulletRegion;


    private static final float
            SMALL_ENEMY_HEIGHT = 0.1f,
            MEDIUM_ENEMY_HEIGHT = 0.15f,
            BIG_ENEMY_HEIGHT = 0.2f;
    private static final Vector2
            SMALL_ENEMY_VEL = new Vector2(0, -0.3f),
            MEDIUM_ENEMY_VEL = new Vector2(0, -0.2f),
            BIG_ENEMY_VEL = new Vector2(0, -0.1f);

    private float generateTimer;

    public EnemyEmitTer(EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;

        smallRegions = Regions.split(Config.getMainATLAS().findRegion("enemy0"), 1, 2, 2);
        mediumRegions = Regions.split(Config.getMainATLAS().findRegion("enemy1"), 1, 2, 2);
        bigRegions = Regions.split(Config.getMainATLAS().findRegion("enemy2"), 1, 2, 2);
        bulletRegion = Config.getMainATLAS().findRegion("bulletEnemy");

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
    }

    private void setSmallEnemy(EnemyShip ship) {
        ship.setRegions(smallRegions);
        ship.setHeightProportion(SMALL_ENEMY_HEIGHT);
        ship.setBulletInfo(BulletInfo.BULLET_SMALL_ENEMY_SHIP);

        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
        ship.move(SMALL_ENEMY_VEL);

        ship.setShootInterval(0.2f);
    }

    private void setMediumEnemy(EnemyShip ship) {
        ship.setRegions(mediumRegions);
        ship.setHeightProportion(MEDIUM_ENEMY_HEIGHT);
        ship.setBulletInfo(BulletInfo.BULLET_MEDIUM_ENEMY_SHIP);
        ship.setBulletInfo(BulletInfo.BULLET_MEDIUM_ENEMY_SHIP);

        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
        ship.move(MEDIUM_ENEMY_VEL);

        ship.setShootInterval(0.3f);
    }

    private void setBigEnemy(EnemyShip ship) {
        ship.setRegions(bigRegions);
        ship.setHeightProportion(BIG_ENEMY_HEIGHT);
        ship.setBulletInfo(BulletInfo.BULLET_BIG_ENEMY_SHIP);

        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
        ship.move(BIG_ENEMY_VEL);

        ship.setShootInterval(0.4f);
    }
}
