package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyEMitTer {

    private static final float GENERATE_INTERVAL = 4f;
    private final EnemyPool enemyPool;
    private final Rect worldBounds;
    private final TextureRegion[] smallRegions, mediumRegions, bigRegions;
    private final TextureRegion bulletRegion;


    private final Vector2
            SMALL_BULLET_VEL = new Vector2(0, -0.5f),
            MEDIUM_BULLET_VEL = new Vector2(0, -0.5f),
            BIG_BULLET_VEL = new Vector2(0, -0.5f);
    private static final float
            SMALL_BULLET_HEIGHT = 0.01f,
            MEDIUM_BULLET_HEIGHT = 0.02f,
            BIG_BULLET_HEIGHT = 0.04f;
    private static final int
            SMALL_BULLET_DAMAGE = 1,
            MEDIUM_BULLET_DAMAGE = 2,
            BIG_BULLET_DAMAGE = 4;

    private static final float
            SMALL_ENEMY_HEIGHT = 0.1f,
            MEDIUM_ENEMY_HEIGHT = 0.15f,
            BIG_ENEMY_HEIGHT = 0.2f;
    private static final Vector2
            SMALL_ENEMY_VEL = new Vector2(0, -0.3f),
            MEDIUM_ENEMY_VEL = new Vector2(0, -0.2f),
            BIG_ENEMY_VEL = new Vector2(0, -0.1f);

    private float generateTimer;

    public EnemyEMitTer(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;

        smallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        mediumRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        bigRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");

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
        ship.setUpBullets(
                enemyPool.getBulletPool(),
                bulletRegion,
                SMALL_BULLET_VEL,
                SMALL_BULLET_HEIGHT,
                SMALL_BULLET_DAMAGE
        );

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
        ship.setUpBullets(
                enemyPool.getBulletPool(),
                bulletRegion,
                MEDIUM_BULLET_VEL,
                MEDIUM_BULLET_HEIGHT,
                MEDIUM_BULLET_DAMAGE
        );

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
        ship.setUpBullets(
                enemyPool.getBulletPool(),
                bulletRegion,
                BIG_BULLET_VEL,
                BIG_BULLET_HEIGHT,
                BIG_BULLET_DAMAGE
        );

        ship.pos.x = MathUtils.random(
                worldBounds.getLeft() + ship.getHalfWidth(),
                worldBounds.getRight() - ship.getHalfWidth()
        );
        ship.setBottom(worldBounds.getTop());
        ship.move(BIG_ENEMY_VEL);

        ship.setShootInterval(0.4f);
    }
}
