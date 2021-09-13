package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public class BaseShip extends PooledSprite{

    protected final Vector2 vel = new Vector2();

    private float shootTime;
    private float shootInterval;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletVel = new Vector2();
    protected Vector2 bulletPos = new Vector2();
    private float bulletHeight;
    private int bulletDamage;

    protected Rect worldBounds;

    public BaseShip() {
    }

    public BaseShip(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public float getShootInterval() {
        return shootInterval;
    }

    public void setShootInterval(float shootInterval) {
        this.shootInterval = shootInterval;
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
        bullet.set(this, bulletRegion, bulletPos, bulletVel, bulletHeight, worldBounds, bulletDamage);
    }

    public void setUpBullets(BulletPool pool, TextureRegion region, Vector2 vel, float height, int damage) {
//    public void setUpBullets(BulletPool pool, TextureRegion region, Vector2 vel, Vector2 pos, float height, int damade) {
        bulletPool = pool;
        bulletRegion = region;
        bulletVel.set(vel);
//        bulletPos.set(pos);
        bulletHeight = height;
        bulletDamage = damage;
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


}
