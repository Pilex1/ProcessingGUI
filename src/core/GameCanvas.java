package core;

import static main.Applet.P;

import processing.core.PVector;

/**
 * a Canvas that takes up the entire window, and must be redrawn each frame
 *
 */
public class GameCanvas extends Canvas {

	public enum GameState {
		TitleScreen, Game, Paused
	}

	public Layout titleScreen;
	public Layout pauseScreenOverlay;
	public GameState gameState = GameState.TitleScreen;

	public GameCanvas() {
		super(P.width, P.height);
		graphics = P.getGraphics();
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
	}

}
