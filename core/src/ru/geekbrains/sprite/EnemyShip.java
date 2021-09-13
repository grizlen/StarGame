package ru.geekbrains.sprite;

import ru.geekbrains.base.BaseShip;
import ru.geekbrains.math.Rect;

public class EnemyShip extends BaseShip {

    public EnemyShip(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
    }
}
