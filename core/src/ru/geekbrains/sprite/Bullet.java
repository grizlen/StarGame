package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseSprite;
import ru.geekbrains.base.PooledSprite;
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
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        regions[0] = region;
        pos.set(pos0);
        v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
