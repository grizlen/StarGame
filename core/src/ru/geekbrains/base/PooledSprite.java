package ru.geekbrains.base;

public class PooledSprite extends BaseSprite{

    private boolean destroyed;

    public PooledSprite() {
        super();
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
