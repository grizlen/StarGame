package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float IMG_SPEED = 2f;
    private Texture img;
    private final Vector2 pos = new Vector2();
    private final Vector2 offs = new Vector2();
    private final Vector2 touchPos = new Vector2();
    private final Vector2 vel = new Vector2();
    private final Vector2 imgPos = new Vector2();

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        offs.set(img.getWidth() / 2, img.getWidth() / 2);
        touchPos.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pos.set(touchPos);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        UpdatePos();
        imgPos.set(pos).sub(offs);
        batch.draw(img, imgPos.x, imgPos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float x = screenX < offs.x ? offs.x :
                screenX + offs.x > Gdx.graphics.getWidth() ? Gdx.graphics.getWidth() - offs.x :
                        screenX;
        screenY = Gdx.graphics.getHeight() - screenY;
        float y = screenY < offs.y ? offs.y :
                screenY + offs.y > Gdx.graphics.getHeight() ? Gdx.graphics.getHeight() - offs.y :
                        screenY;
        touchPos.set(x, y);
        UpdatePos();
        return false;
    }

    private void UpdatePos() {
        vel.set(touchPos).sub(pos);
        if (vel.len() > IMG_SPEED) {
            pos.add(vel.nor().scl(IMG_SPEED));
        } else {
            pos.set(touchPos);
        }
    }
}
