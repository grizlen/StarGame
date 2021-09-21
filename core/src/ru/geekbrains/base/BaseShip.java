package ru.geekbrains.base;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.info.ShipInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public class BaseShip extends PooledSprite{

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    protected Rect worldBounds;

    protected final Vector2 vel = new Vector2();
    protected float shootTime;
    protected float shootInterval;

    protected Vector2 bulletPos = new Vector2();

    private BulletInfo bulletInfo;
    private ShipInfo shipInfo;
    private int hp;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    private int level;

    public BaseShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    public void setBulletInfo(BulletInfo bulletInfo) {
        this.bulletInfo = bulletInfo;
    }

    public ShipInfo getShipInfo() {
        return shipInfo;
    }

    public void setShipInfo(ShipInfo shipInfo) {
        this.shipInfo = shipInfo;
        regions = shipInfo.getRegions();
        setHeightProportion(shipInfo.getHeight());
        shootInterval = shipInfo.getShootInterval();
        hp = shipInfo.getHp();
    }

    public float getBulletDamage() {
        return bulletInfo.getDamage();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletPos, worldBounds, bulletInfo);
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


    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }

    public void damage(float damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }
}
