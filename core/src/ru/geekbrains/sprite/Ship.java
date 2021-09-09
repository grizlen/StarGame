package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseSprite;
import ru.geekbrains.math.Rect;

public class Ship extends BaseSprite {

    private static final float SHIP_SPEED = 0.5f;

    private Rect worldBounds;
    private Vector2 v;

    public Ship(TextureAtlas atlas) {
        this(atlas.findRegion("main_ship"));
    }

    public Ship(TextureRegion textureRegion) {
        super(textureRegion);
        setHeightProportion(0.1f);
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.y = worldBounds.getBottom() + 0.1f;
        pos.mulAdd(v, delta);
        if (!worldBounds.isMe(pos)) {
            pos.x = pos.x < worldBounds.getLeft() ? worldBounds.getLeft() : worldBounds.getRight();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        v.x = touch.x < 0 ? -SHIP_SPEED : SHIP_SPEED;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        v.x = 0;
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                v.x = -SHIP_SPEED;
                break;
            case Input.Keys.RIGHT:
                v.x = SHIP_SPEED;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        v.x = 0;
        return false;
    }
}
