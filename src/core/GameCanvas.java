package core;

import static main.Applet.P;

import java.awt.event.KeyEvent;

import main.Applet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;
import util.Color;

/**
 * a Canvas that takes up the entire window, and must be redrawn each frame
 *
 */
public abstract class GameCanvas extends Canvas {

	public enum GameState {
		Title, Game, Paused, End
	}

	private Layout titleScreen;
	private Layout pausedScreen;
	private Layout endScreen;
	private GameState gameState;

	private boolean changeState = true;
	private boolean allowPausing = true;

	public GameCanvas() {
		super(P.width, P.height);
		backgroundColor = Color.Black;
		graphics = P.getGraphics();
		graphics.background(backgroundColor.r, backgroundColor.g, backgroundColor.b);
		
		loadGame();
		titleScreen = initTitleScreen();
		pausedScreen = initPauseScreen();
		endScreen = initEndScreen();
		setGameState(GameState.Title);
	}
	
	protected abstract void loadGame();

	protected abstract Layout initTitleScreen();

	protected abstract Layout initPauseScreen();

	protected abstract Layout initEndScreen();

	public void allowPausing(boolean b) {
		allowPausing = b;
	}

	public void setGameState(GameState state) {
		if (state == this.gameState)
			return;
		changeState = true;
		this.gameState = state;
		titleScreen.reset();
		pausedScreen.reset();
		endScreen.reset();
	}

	protected abstract void updateGame();

	protected abstract void renderGame();

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		switch (gameState) {
		case Game:
			updateGame();
			break;
		case Paused:
			updateScreen(pausedScreen, pos, size);
			break;
		case Title:
			updateScreen(titleScreen, pos, size);
			break;
		case End:
			updateScreen(endScreen, pos, size);
			break;
		}
		requestGraphicalUpdate();
	}

	private void updateScreen(Layout l, PVector pos, PVector size) {
		float width = Math.min(l.getMaxWidth(), P.width);
		float height = Math.min(l.getMaxHeight(), P.height);
		float x = (P.width - width) / 2;
		float y = (P.height - height) / 2;
		l.update(new PVector(x, y), new PVector(width, height));
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		switch (gameState) {
		case Game:
			renderGame();
			break;
		case Paused:
			renderScreen(pausedScreen, new Color(0, 0, 0, 192), pos, size);
			break;
		case Title:
			renderScreen(titleScreen, new Color(0, 0, 0, 255), pos, size);
			break;
		case End:
			renderScreen(endScreen, new Color(0, 0, 0, 255), pos, size);
			break;
		}
	}

	private void renderScreen(Layout l, Color background, PVector pos, PVector size) {
		if (changeState) {
			P.noStroke();
			P.fill(background.r, background.g, background.b, background.a);
			P.rect(0, 0, Applet.WIDTH, Applet.HEIGHT);
			changeState = false;
			l.requestGraphicalUpdate();
		} else {
			float width = Math.min(pausedScreen.getMaxWidth(), P.width);
			float height = Math.min(pausedScreen.getMaxHeight(), P.height);
			float x = (P.width - width) / 2;
			float y = (P.height - height) / 2;
			l.render(new PVector(x, y), new PVector(width, height));
		}
	}

	protected abstract void onMousePressGame(MouseEvent event);

	protected abstract void onMouseReleaseGame(MouseEvent event);

	protected abstract void onKeyPressGame(char key);

	protected abstract void onScrollGame(MouseEvent event);

	@Override
	public void onMousePress(MouseEvent event) {
		switch (gameState) {
		case Game:
			onMousePressGame(event);
			break;
		case Paused:
			pausedScreen.onMousePress(event);
			break;
		case Title:
			titleScreen.onMousePress(event);
			break;
		case End:
			endScreen.onMousePress(event);
			break;
		}
	}

	@Override
	public void onMouseRelease(MouseEvent event) {
		switch (gameState) {
		case Game:
			onMouseReleaseGame(event);
			break;
		case Paused:
			pausedScreen.onMouseRelease(event);
			break;
		case Title:
			titleScreen.onMouseRelease(event);
			break;
		case End:
			endScreen.onMouseRelease(event);
			break;
		}

	}

	@Override
	public void onKeyType(char key) {
		switch (gameState) {
		case Game:
			if (allowPausing && key == KeyEvent.VK_ESCAPE) {
				setGameState(GameState.Paused);
			} else {
				onKeyPressGame(key);
			}
			break;
		case Paused:
			if (key == KeyEvent.VK_ESCAPE) {
				setGameState(GameState.Game);
			} else {
				pausedScreen.onKeyType(key);
			}
			break;
		case Title:
			titleScreen.onKeyType(key);
			break;
		case End:
			endScreen.onKeyType(key);
			break;
		}
	}

	@Override
	public void onScroll(MouseEvent event) {
		switch (gameState) {
		case Game:
			onScrollGame(event);
			break;
		case Paused:
			pausedScreen.onScroll(event);
			break;
		case Title:
			titleScreen.onScroll(event);
			break;
		case End:
			endScreen.onScroll(event);
			break;
		}
	}

}
