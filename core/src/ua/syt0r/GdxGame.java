package ua.syt0r;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GdxGame extends Game {

	private SpriteBatch spriteBatch;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		Gdx.app.exit();
	}

	@Override
	public void pause() {
		super.pause();
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
