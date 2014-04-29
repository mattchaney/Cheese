package cheesegame.screens;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	
	public abstract void processInput();
	
	public abstract void update(float delta);
	
	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
