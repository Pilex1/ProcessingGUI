package core;

import static main.Applet.P;

import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Frame {

	public GraphicsComponent mainComponent;

	public Frame(GraphicsComponent layout) {
		this.mainComponent = layout;
		layout.setMinSize(new PVector(P.width, P.height));
		layout.setMaxSize(new PVector(P.width, P.height));
	}

	public void update() {
		float width = Math.min(mainComponent.getMaxWidth(), P.width);
		float height = Math.min(mainComponent.getMaxHeight(), P.height);
		float x = (P.width - width) / 2;
		float y = (P.height - height) / 2;
		mainComponent.update(new PVector(x, y), new PVector(width, height));
	}

	public void render() {
		float width = Math.min(mainComponent.getMaxWidth(), P.width);
		float height = Math.min(mainComponent.getMaxHeight(), P.height);
		float x = (P.width - width) / 2;
		float y = (P.height - height) / 2;
		mainComponent.render(new PVector(x, y), new PVector(width, height));
	}

	public void onKeyType(char key) {
		mainComponent.onKeyType(key);
	}

	public void onMousePress(MouseEvent event) {
		mainComponent.onMousePress(event);
	}

	public void onMouseRelease(MouseEvent event) {
		mainComponent.onMouseRelease(event);
	}
}
