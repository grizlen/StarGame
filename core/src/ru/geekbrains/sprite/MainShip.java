package ru.geekbrains.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseShip;
import ru.geekbrains.global.Config;
import ru.geekbrains.info.BulletInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends BaseShip {

    private static final float SHIP_SPEED = 0.5f;
    private static final float HEIFGH = 0.15f;
    private static final float BOOTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final float SHOOT_INTERVAL = 0.1f;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(BulletPool bulletPool, Rect worldBounds) {
        super(Config.getMainATLAS().findRegion("main_ship"), 1, 2, 2);
        setBulletPool(bulletPool);
        setWorldBounds(worldBounds);
        setShootInterval(SHOOT_INTERVAL);
        setBulletInfo(BulletInfo.BULLET_MAIN_SHIP);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIFGH);
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

}
