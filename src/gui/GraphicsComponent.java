package gui;

import layouts.Layout;
import processing.core.*;

// all graphical elements extend this class
public abstract class GraphicsComponent {

	// padding around the element
	public EdgeTuple padding = new EdgeTuple(5);

	public PVector maxSize = new PVector(Float.MAX_VALUE, Float.MAX_VALUE);
	public PVector minSize = new PVector(0, 0);

	public boolean disabled;
	public boolean active;

	private boolean requireUpdate = true;

	protected GraphicsComponent() {
	}

	public void onKeyType(char key) {
	}

	public void onMousePress(int mouseBtn) {
	}

	protected abstract void onUpdate(PVector pos, PVector size);

	protected abstract void onRender(PVector pos, PVector size);

	protected final void requireGraphicalUpdate() {
		requireUpdate = true;
	}

	public final void update(PVector pos, PVector size) {
		if (!disabled) {
			onUpdate(pos, size);
		}
	}

	public final void render(PVector pos, PVector size) {
		if (!disabled) {
			// children are rendered when the parent layout is rendered
			// if the parent layout is not rendered, then any graphical updates
			// in the child layout will not be carried out
			if (requireUpdate || this instanceof Layout) {
				onRender(pos, size);
			}
			requireUpdate = false;
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
