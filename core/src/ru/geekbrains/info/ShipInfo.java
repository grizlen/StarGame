package ru.geekbrains.info;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.global.Config;
import ru.geekbrains.utils.Regions;

public enum ShipInfo {
    MAIN_SHIP("main_ship"),
    SMALL_ENEMY_SHIP("enemy0"),
    MEDIUM_ENEMY_SHIP("enemy1"),
    BIG_ENEMY_SHIP("enemy2");

    ShipInfo(String regionName) {
        TextureRegion[] regions = Regions.split(Config.getMainATLAS().findRegion(regionName), 1, 2, 2);
    }
}
