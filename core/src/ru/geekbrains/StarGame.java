package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion rgn;

	float x, y;
	float dx = 1,
			dy = 1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		rgn = new TextureRegion(img, 100, 100, 100, 50);
	}

	@Override
	public void render () {
		dx = direction(x, dx, Gdx.graphics.getWidth() - img.getWidth());
		x += dx;
		dy = direction(y, dy, Gdx.graphics.getHeight() - img.getHeight());
		y += dy;

		ScreenUtils.clear(Color.TEAL);
//		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, x, y);
		batch.setColor(1, 1, 1, 0.5f);
		batch.draw(img, 250, 250, 100, 100);
		batch.setColor(1, 1, 1, 1);
		batch.draw(rgn, 20, 34);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private float direction(float p, float d, int r) {
		if (p + d > r) {
			return -d;
		}
		if (p + d < 0) {
			return -d;
		}
		return d;
	}
}
