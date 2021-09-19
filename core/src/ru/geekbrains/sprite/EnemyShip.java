package ru.geekbrains.sprite;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseShip;
import ru.geekbrains.info.ShipInfo;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class EnemyShip extends BaseShip {

    private boolean isInit;
    private  final Vector2 initVel = new Vector2();

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        super(bulletPool, worldBounds);
    }

    @Override
    public void update(float delta) {
        if (isInit && getTop() < worldBounds.getTop()){
            shootTime = shootInterval + 0.01f;
        }
        isInit = getTop() > worldBounds.getTop();
        if (isInit) {
            shootTime = 0;
            move(initVel);
        } else  {
            move(getShipInfo().getVel());
        }
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
    }

    @Override
    public void setShipInfo(ShipInfo shipInfo) {
        super.setShipInfo(shipInfo);
        initVel.set(shipInfo.getVel()).scl(3f);
    }
}
