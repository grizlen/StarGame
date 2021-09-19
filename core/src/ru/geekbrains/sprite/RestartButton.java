package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;

import ru.geekbrains.base.BaseButton;
import ru.geekbrains.global.Config;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class RestartButton extends BaseButton {

    private static final float HEIGHT = 0.05f;

    private boolean visible;
    private GameScreen screen;

    public RestartButton(boolean visible, GameScreen screen) {
        super(Config.getMainATLAS().findRegion("button_new_game"));
        this.visible = visible;
        this.screen = screen;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(0, 0);
    }

    @Override
    public void action() {
        visible = false;
        screen.start();
    }
}
