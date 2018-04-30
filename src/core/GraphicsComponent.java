package core;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import util.EdgeTuple;
import static main.Applet.P;

/** all graphical elements extend this class */
public abstract class GraphicsComponent {

	/** padding around the element */
	public EdgeTuple padding = new EdgeTuple(2);

	private PVector maxSize = new PVector(Float.MAX_VALUE, Float.MAX_VALUE);
	private PVector minSize = new PVector(0, 0);

	public boolean disabled;
	public boolean active;

	Layout parent;

	/**
	 * indicates that graphics within the component need to be re-rendered but its
	 * relative position and size stays the same<br>
	 * e.g. when a button is being hovered over
	 */
	private boolean requireGraphicalUpdate = true;

	/**
	 * indicates that the relative position or size of the component is changed<br>
	 * parent component must recalculate all positions + sizes
	 */
	private boolean requirePositionalUpdate = true;

	protected GraphicsComponent() {
	}

	boolean requiresGraphicalUpdate() {
		return requireGraphicalUpdate;
	}

	boolean requiresPositionalUpdate() {
		return requirePositionalUpdate;
	}

	public float getMaxWidth() {
		return maxSize.x;
	}

	public float getMaxHeight() {
		return maxSize.y;
	}

	public PVector getMaxSize() {
		return maxSize;
	}

	public void setMaxWidth(float width) {
		setMaxSize(width, getMaxHeight());
	}

	public void setMaxHeight(float height) {
		setMaxSize(getMaxWidth(), height);
	}

	public void setMaxSize(float width, float height) {
		setMaxSize(new PVector(width, height));
	}

	// if we change the max size, we want the parent to recalculate everything
	public void setMaxSize(PVector maxSize) {
		if (parent != null && !maxSize.equals(this.maxSize)) {
			parent.requestPositionalUpdate();
		}
		this.maxSize = maxSize;
	}

	public float getMinWidth() {
		return minSize.x;
	}

	public float getMinHeight() {
		return minSize.y;
	}

	public PVector getMinSize() {
		return minSize;
	}

	public void setMinWidth(float width) {
		setMinSize(width, getMinHeight());
	}

	public void setMinHeight(float height) {
		setMinSize(getMinWidth(), height);
	}

	public void setMinSize(float width, float height) {
		setMinSize(new PVector(width, height));
	}

	// if we change the min size, we want the parent to recalculate everything
	public void setMinSize(PVector minSize) {
		if (parent != null && !minSize.equals(this.minSize)) {
			parent.requestPositionalUpdate();
		}
		this.minSize = minSize;
	}

	public Layout getParent() {
		return parent;
	}

	public void onKeyType(char key) {
	}

	public void onMousePress(MouseEvent event) {
	}

	public void onMouseRelease(MouseEvent event) {
	}

	public void onScroll(MouseEvent event) {
	}

	protected abstract void onUpdate(PVector pos, PVector size);

	protected abstract void onRender(PVector pos, PVector size);

	protected final void requestGraphicalUpdate() {
		requireGraphicalUpdate = true;
		// System.out.println("Graphical update requested on " + this);
	}

	protected final void requestPositionalUpdate() {
		requirePositionalUpdate = true;
		requireGraphicalUpdate = true;
		// System.out.println("Positional update requested on " + this);
	}

	public final void update(PVector pos, PVector size) {
		if (!disabled) {
			if (requirePositionalUpdate && this instanceof Layout) {
				((Layout) this).recalculatePositions(pos, size);
				ArrayList<GraphicsComponent> children = ((Layout) this).getAllComponents();
				for (GraphicsComponent g : children) {
					g.requestPositionalUpdate();
				}
			}

			onUpdate(pos, size);
		}
	}

	private void renderComponent(PVector pos, PVector size) {
		if (requireGraphicalUpdate && this instanceof Layout) {
			P.noStroke();
			Color backgroundColor = ((Layout) this).backgroundColor;
			P.fill(backgroundColor.getRGB());
			P.rect(pos.x, pos.y, size.x, size.y);
		}

		// children are rendered when the parent layout is rendered
		// if the parent layout is not rendered, then any graphical updates
		// in the child layout will not be carried out
		if (requireGraphicalUpdate || this instanceof Layout) {
			onRender(pos, size);
		}
		requireGraphicalUpdate = false;
		requirePositionalUpdate = false;
	}

	public final void render(PVector pos, PVector size) {
		if (!disabled) {
			if (this instanceof GameCanvas) {
				this.onRender(pos, size);
			} else {
				renderComponent(pos, size);
			}

		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
