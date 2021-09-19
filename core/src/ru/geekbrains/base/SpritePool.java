package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritePool<T extends PooledSprite> {

    protected final List<T> activeObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (PooledSprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(final SpriteBatch batch) {
        for (PooledSprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public void freeAllDetroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    private void free(T sprite) {
        if (activeObjects.remove(sprite)) {
            freeObjects.add(sprite);
        }
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

}
