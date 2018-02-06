package gui;

import processing.core.*;

// all graphical elements extend this class
public abstract class GraphicsComponent {
	
	// padding around the element
	public EdgeTuple padding = new EdgeTuple(5);

	public boolean disabled;
	public boolean active;
	
	protected GraphicsComponent() {
	}
	
	public void onKeyType(char key) {}
	public void onMousePress(int mouseBtn) {}

	protected abstract void onUpdate(PVector pos, PVector size);
	protected abstract void onRender(PVector pos, PVector size);
	
	public final void update(PVector pos, PVector size) {
		if (!disabled) {
			onUpdate(pos, size);
		}
	}
	public final void render(PVector pos, PVector size) {
		if (!disabled) {
			onRender(pos, size);
		}
	}

	public void setActive(boolean active) {
		this.active=active;
	}
	
}
