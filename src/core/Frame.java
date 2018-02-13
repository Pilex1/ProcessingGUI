package core;

import static main.Program.P;
import processing.core.PVector;

public class Frame  {

	public Layout layout;

	public Frame(Layout layout) {
		this.layout = layout;
		layout.setMinSize(new PVector(P.WIDTH,P.HEIGHT));
		layout.setMaxSize(new PVector(P.WIDTH,P.HEIGHT));
	}

	public void update() {
		layout.update(new PVector(), new PVector(P.width, P.height));
	}

	public void render() {
		layout.render(new PVector(), new PVector(P.width, P.height));
	}

	public void onKeyType(char key) {
		layout.onKeyType(key);
	}
	
	public void onMousePress(int mouseBtn) {
		layout.onMousePress(mouseBtn);
	}
}
