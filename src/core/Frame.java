package core;

import static main.Applet.P;

import processing.core.PVector;
import processing.event.MouseEvent;

public class Frame  {

	public GraphicsComponent mainComponent;

	public Frame(GraphicsComponent layout) {
		this.mainComponent = layout;
		layout.setMinSize(new PVector(P.width,P.height));
		layout.setMaxSize(new PVector(P.width,P.height));
	}

	public void update() {
		mainComponent.update(new PVector(), new PVector(P.width, P.height));
	}

	public void render() {
		mainComponent.render(new PVector(), new PVector(P.width, P.height));
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
