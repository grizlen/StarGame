package ru.geekbrains.info;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.global.Config;
import ru.geekbrains.utils.Regions;

public enum ShipInfo {
    MAIN_SHIP("main_ship", 0.15f, new Vector2(0, 0), 0.1f, 40),
    SMALL_ENEMY_SHIP("enemy0", 0.1f, new Vector2(0, -0.3f), 0.2f, 10),
    MEDIUM_ENEMY_SHIP("enemy1", 0.15f, new Vector2(0, -0.2f), 0.3f, 20),
    BIG_ENEMY_SHIP("enemy2", 0.2f, new Vector2(0, -0.1f), 0.4f, 30);

    private final TextureRegion[] regions;
    private final float height;
    private final Vector2 vel;
    private final float shootInterval;
    private final int hp;

    ShipInfo(String regionName, float height, Vector2 vel, float shootInterval, int hp) {
        regions = Regions.split(Config.getMainATLAS().findRegion(regionName), 1, 2, 2);
        this.height = height;
        this.vel = vel;
        this.shootInterval = shootInterval;
        this.hp = hp;
    }

    public TextureRegion[] getRegions() {
        return regions;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getVel() {
        return vel;
    }

    public float getShootInterval() {
        return shootInterval;
    }

    public int getHp() {
        return hp;
    }
}
