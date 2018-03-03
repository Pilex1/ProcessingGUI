package core;

import static main.Applet.P;

import processing.core.PVector;

/**
 * a Canvas that takes up the entire window, and must be redrawn each frame
 *
 */
public class GameCanvas extends Canvas {
	
	public Layout pauseScreenOverlay;

	public GameCanvas() {
		super(P.width, P.height);
		graphics=P.getGraphics();
	}
	
	@Override
	protected void onRender(PVector pos, PVector size) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		// TODO Auto-generated method stub

	}

}
