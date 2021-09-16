package ru.geekbrains.info;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.global.Config;

public enum BulletInfo {
    BULLET_MAIN_SHIP(
            "bulletMainShip",
            new Vector2(0, 0.5f),
            0.01f,
            1
    ),
    BULLET_SMALL_ENEMY_SHIP(
            "bulletEnemy",
            new Vector2(0, -0.5f),
            0.01f,
            1
    ),
    BULLET_MEDIUM_ENEMY_SHIP(
            "bulletEnemy",
            new Vector2(0, -0.5f),
            0.02f,
            2
    ),
    BULLET_BIG_ENEMY_SHIP(
            "bulletEnemy",
            new Vector2(0, -0.5f),
            0.04f,
            4
    );

    private final String regionName;
    private final TextureRegion region;
    private final Vector2 vel;
    private final float height;
    private final int damage;

    BulletInfo(String regionName, Vector2 vel, float height, int damage) {
        region = Config.getMainATLAS().findRegion(regionName);
        this.regionName = regionName;
        this.vel = vel;
        this.height = height;
        this.damage = damage;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Vector2 getVel() {
        return vel;
    }

    public float getHeight() {
        return height;
    }

    public int getDamage() {
        return damage;
    }

}
