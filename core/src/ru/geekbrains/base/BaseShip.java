package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.info.ShipInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public class BaseShip extends PooledSprite{

    private BulletPool bulletPool;
    protected Rect worldBounds;

    protected final Vector2 vel = new Vector2();
    protected float shootTime;
    protected float shootInterval;

    private Vector2 bulletVel = new Vector2();
    protected Vector2 bulletPos = new Vector2();

    private BulletInfo bulletInfo;
    private ShipInfo shipInfo;

    public BaseShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(vel, delta);

        shootTime += delta;
        if (shootTime > shootInterval) {
            shoot();
            while (shootTime > shootInterval) {
                shootTime -= shootInterval;
            }
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletPos, worldBounds, bulletInfo);
    }

    public void setBulletInfo(BulletInfo bulletInfo) {
        this.bulletInfo = bulletInfo;
    }

    public void move(float x, float y) {
        vel.set(x, y);
    }

    public void move(Vector2 v) {
        vel.set(v);
    }

    protected void stop() {
        vel.setZero();
    }


    public void setShipInfo(ShipInfo shipInfo) {
        this.shipInfo = shipInfo;
        regions = shipInfo.getRegions();
        setHeightProportion(shipInfo.getHeight());
        shootInterval = shipInfo.getShootInterval();
    }

    public ShipInfo getShipInfo() {
        return shipInfo;
    }
}
