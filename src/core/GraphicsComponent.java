package core;

import static main.Applet.P;

import java.util.ArrayList;

import processing.core.PVector;
import processing.event.MouseEvent;
import util.Color;
import util.EdgeTuple;

/** all graphical elements extend this class */
public abstract class GraphicsComponent {

	public Color backgroundColor;

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

	public void setPadding(EdgeTuple padding) {
		this.padding = padding;
	}

	boolean requiresGraphicalUpdate() {
		return requireGraphicalUpdate;
	}

	boolean requiresPositionalUpdate() {
		return requirePositionalUpdate;
	}

	public final float getMaxWidth() {
		return getMaxSize().x;
	}

	public final float getMaxHeight() {
		return getMaxSize().y;
	}

	public PVector getMaxSize() {
		return maxSize;
	}

	public final void setMaxWidth(float width) {
		setMaxSize(width, getMaxHeight());
	}

	public final void setMaxHeight(float height) {
		setMaxSize(getMaxWidth(), height);
	}

	public void setMaxSize(float width, float height) {
		setMaxSize(new PVector(width, height));
	}

	// if we change the max size, we want the parent to recalculate everything
	public void setMaxSize(PVector maxSize) {
		if (parent == null) {
			// System.out.println(this);
		}
		if (parent != null && !maxSize.equals(this.maxSize)) {
			parent.requestPositionalUpdate();
		}
		this.maxSize = maxSize;
	}

	public final float getMinWidth() {
		return getMinSize().x;
	}

	public final float getMinHeight() {
		return getMinSize().y;
	}

	public PVector getMinSize() {
		return minSize;
	}

	public final void setMinWidth(float width) {
		setMinSize(width, getMinHeight());
	}

	public final void setMinHeight(float height) {
		setMinSize(getMinWidth(), height);
	}

	public void setMinSize(float width, float height) {
		setMinSize(new PVector(width, height));
	}

	// if we change the min size, we want the parent to recalculate everything
	public void setMinSize(PVector minSize) {
		if (parent == null) {
			if (this instanceof GameCanvas) {

			} else {
				// System.out.println(this);
			}
		}
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
		if (this instanceof Layout) {
			((Layout) this).getCurrentComponents().forEach(c -> c.requestGraphicalUpdate());
		}
		// System.out.println("Graphical update requested on " + this);
	}

	protected final void requestPositionalUpdate() {
		requirePositionalUpdate = true;
		requireGraphicalUpdate = true;
		if (this instanceof Layout) {
			((Layout) this).getCurrentComponents().forEach(c -> c.requestPositionalUpdate());
		}
		// System.out.println("Positional update requested on " + this);
	}

	public final void update(PVector pos, PVector size) {
		if (!disabled) {
			if (requirePositionalUpdate && this instanceof Layout) {
				((Layout) this).recalculatePositions(pos, size);
				ArrayList<GraphicsComponent> children = ((Layout) this).getCurrentComponents();
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
			if (backgroundColor == null) {
				System.out.println(this);
				System.out.println("null");
				P.fill(0, 0, 0);
			} else {
				System.out.println(this);
				System.out.println(backgroundColor);
				P.fill(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
			}
			P.rect(pos.x, pos.y, size.x, size.y);
		}

		// children are rendered when the parent layout is rendered
		// if the parent layout is not rendered, then any graphical updates
		// in the child layout will not be carried out
		if (requireGraphicalUpdate || this instanceof Layout) {
			if (!(this instanceof Layout)) {
				// System.out.println(this);
			}
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

	public void reset() {
	}

}
