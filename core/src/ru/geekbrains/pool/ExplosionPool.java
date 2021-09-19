package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritePool;
import ru.geekbrains.sprite.Explosion;

public class ExplosionPool extends SpritePool<Explosion> {

    private final Sound sound;

    public ExplosionPool(Sound sound) {
        this.sound = sound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
