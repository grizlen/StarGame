package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;

    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bg.dispose();
    }

    private void update(float delta) {

    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        batch.draw(img, 0, 0, 0.3f, 0.3f);
        batch.end();
    }
}
