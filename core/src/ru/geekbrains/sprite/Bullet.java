package ru.geekbrains.sprite;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseSprite;
import ru.geekbrains.base.PooledSprite;
import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.math.Rect;

public class Bullet extends PooledSprite {

    private final Vector2 v = new Vector2();
    private BaseSprite owner;
    private Rect worldBounds;
    private int damage;

    public BaseSprite getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }

    public void set(
            BaseSprite owner,
            Vector2 pos0,
            Rect worldBounds,
            BulletInfo bulletInfo
    ) {
        this.owner = owner;
        pos.set(pos0);
        this.worldBounds = worldBounds;
        regions[0] = bulletInfo.getRegion();
        v.set(bulletInfo.getVel());
        setHeightProportion(bulletInfo.getHeight());
        damage = bulletInfo.getDamage();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
