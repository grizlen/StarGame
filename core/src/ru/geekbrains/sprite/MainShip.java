package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseSprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends BaseSprite {

    private static final float SHIP_SPEED = 0.5f;
    private static final float HEIFGH = 0.15f;
    private static final float BOOTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final float SHOOT_INTERVAL = 0.1f;
    private final BulletPool bulletPool;
    private final TextureRegion bulletRegion;
    private final Vector2 bulletV;
    private final Vector2 bulletPos;
    private final float bulletHeight;
    private final int bulletDamage;

    private Rect worldBounds;
    private Vector2 v = new Vector2();
    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private float shootTime = 0;
    private boolean shooting;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        bulletDamage = 1;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIFGH);
        setBottom(worldBounds.getBottom() + BOOTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (!worldBounds.isMe(pos)) {
            pos.x = pos.x < worldBounds.getLeft() ? worldBounds.getLeft() : worldBounds.getRight();
        }
        shootTime += delta;
        if (shootTime > SHOOT_INTERVAL) {
            shoot();
//            System.out.println(shootTime);
            while (shootTime > SHOOT_INTERVAL) {
                shootTime -= SHOOT_INTERVAL;
            }
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < 0) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            v.x = -SHIP_SPEED;
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            v.x = SHIP_SPEED;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                v.x = SHIP_SPEED;
            } else {
                v.x = 0;
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                v.x = -SHIP_SPEED;
            } else {
                v.x = 0;
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                v.x = -SHIP_SPEED;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                v.x = SHIP_SPEED;
                break;
            case Input.Keys.UP:
                shooting = !shooting;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                v.x = pressedRight ? SHIP_SPEED : 0f;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                v.x = pressedLeft ? -SHIP_SPEED : 0f;
                break;
        }
        return false;
    }

    private void shoot() {
        if (shooting) {
            Bullet bullet = bulletPool.obtain();
            bulletPos.set(pos.x, pos.y + getHalfHeight());
            bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        }
    }
}
