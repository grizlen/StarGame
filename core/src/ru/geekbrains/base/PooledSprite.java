package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PooledSprite extends BaseSprite{

    private boolean destroyed;

    public PooledSprite() {
        super();
    }

    public PooledSprite(TextureRegion region) {
        super(region);
    }

    public PooledSprite(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }

    public void flushDestroy() {
        destroyed = false;
    }
}
