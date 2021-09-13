package ru.geekbrains.pool;

import ru.geekbrains.base.SpitePool;
import ru.geekbrains.sprite.Bullet;

public class BulletPool extends SpitePool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
