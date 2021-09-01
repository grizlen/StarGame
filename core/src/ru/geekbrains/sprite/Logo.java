package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float SPEED = 0.01f;

    private final Vector2 dest;
    private final Vector2 vel;
    private Vector2 tempDest;
    private Rect bounds;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        dest = new Vector2();
        vel = new Vector2();
        tempDest = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        bounds = new Rect(worldBounds);
    }

    @Override
    public void update(float delta) {
        tempDest.set(dest);
        if (tempDest.sub(pos).len() > SPEED) {
            pos.add(tempDest.setLength(SPEED));
        } else {
            pos.set(dest);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        dest.set(touch);
        if (dest.x - halfWidth < bounds.getLeft()) {
            dest.x = bounds.getLeft() + halfWidth;
        } else if (dest.x + halfWidth > bounds.getRight()) {
            dest.x = bounds.getRight() - halfWidth;
        }
        if (dest.y - halfHeight < -0.5) {
            dest.y = -0.5f + halfHeight;
        } else if (dest.y + halfHeight > 0.5) {
            dest.y = 0.5f - halfHeight;
        }
        return false;
    }
}
