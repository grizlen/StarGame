package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpitePool;
import ru.geekbrains.sprite.Bullet;

public class BulletPool extends SpitePool<Bullet> {

    private Sound sound;

    public BulletPool(Sound sound) {
        this.sound = sound;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    public Bullet obtain() {
        Bullet bullet = super.obtain();
        if (sound != null) {
            sound.play();
        }
        return bullet;
    }

    @Override
    public void dispose() {
        sound.dispose();
        super.dispose();
    }
}
