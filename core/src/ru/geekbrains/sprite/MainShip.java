package ru.geekbrains.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseShip;
import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.info.ShipInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends BaseShip {

    private static final float SHIP_SPEED = 0.5f;
    private static final float BOOTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        super(bulletPool, explosionPool, worldBounds);
        setBulletInfo(BulletInfo.BULLET_MAIN_SHIP);
        setShipInfo(ShipInfo.MAIN_SHIP);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOOTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        if (!worldBounds.isMe(pos)) {
            pos.x = pos.x < worldBounds.getLeft() ? worldBounds.getLeft() : worldBounds.getRight();
        }
    }


    @Override
    public void damage(float damage) {
        super.damage(damage);
        System.out.println("Main HP = " + getHp());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < 0) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            move(-SHIP_SPEED, 0);
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            move(SHIP_SPEED, 0);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                move(SHIP_SPEED, 0);
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                move(-SHIP_SPEED, 0);
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                move(-SHIP_SPEED, 0);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                move(SHIP_SPEED, 0);
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    move(SHIP_SPEED, 0);
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    move(-SHIP_SPEED, 0);
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean isCollision(Rect rect) {
        return !(
                rect.getRight() < getLeft()
                        || rect.getLeft() > getRight()
                        || rect.getBottom() > pos.y
                        || rect.getTop() < getBottom()
        );
    }

    public void reset() {
        setShipInfo(ShipInfo.MAIN_SHIP);
        pos.x = 0;
        pressedLeft = pressedRight = false;
        leftPointer = rightPointer = INVALID_POINTER;
        flushDestroy();
    }

    @Override
    public void setLevel(int level) {
        if (getLevel() != level) {
            setHp(getShipInfo().getHp());
            super.setLevel(level);
        }
    }
}
