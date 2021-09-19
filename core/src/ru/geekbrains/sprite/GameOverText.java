package ru.geekbrains.sprite;

import ru.geekbrains.base.BaseSprite;
import ru.geekbrains.global.Config;
import ru.geekbrains.math.Rect;

public class GameOverText extends BaseSprite {
    private boolean visible;

    public GameOverText(boolean visible) {
        super(Config.getMainATLAS().findRegion("message_game_over"));
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.05f);
        pos.set(0, worldBounds.getTop() - 0.1f);
    }
}
