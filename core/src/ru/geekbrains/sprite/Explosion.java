package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.PooledSprite;
import ru.geekbrains.global.Config;
import ru.geekbrains.utils.Regions;

public class Explosion extends PooledSprite {

    private static final float ANIMATE_INTERVAL = 0.015f;

    private final Sound sound;
    private float animateTimer;

    public Explosion(Sound sound) {
        regions = Regions.split(Config.getMainATLAS().findRegion("explosion"), 9, 9, 74);
        this.sound = sound;
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        sound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
