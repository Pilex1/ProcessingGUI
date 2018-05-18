package core;

import java.util.ArrayList;

import processing.core.PVector;
import processing.event.MouseEvent;
import util.Color;

/** represents a screen/display on which GraphicsComponents can be added */
public abstract class Layout extends GraphicsComponent {

	/**
	 * gets all components currently on screen<br>
	 * i.e. on a LayoutList, this only returns all the components on the active
	 * layout<br>
	 * non-recursive
	 * 
	 */
	public abstract ArrayList<GraphicsComponent> getCurrentComponents();

	/**
	 * gets all components and sub-components of the current layout recursively <br>
	 * i.e. also returns components in nested layouts
	 * 
	 * if layouts is set to true, then it also returns layouts and nested layouts
	 * 
	 * @return
	 */

	public abstract ArrayList<GraphicsComponent> getAllComponents(boolean layouts);

	public ArrayList<GraphicsComponent> getAllComponents() {
		return getAllComponents(false);
	}

	/**
	 * sets the background color of this layout and all of its children
	 */
	public void setBackgroundColorRecursive(Color c) {
		backgroundColor = c;
		// getAllComponents(true).forEach(gc->System.out.println(gc));
		// System.out.println();
		getAllComponents(true).forEach(gc -> gc.backgroundColor = c);
		requestGraphicalUpdate();
	}

	/**
	 * normally, the positions and sizes in a layout are cached to avoid
	 * recalculating them every single render/update call however when the min/max
	 * sizes of components within a layout change the entire layout must be
	 * recalculated
	 */
	protected abstract void recalculatePositions(PVector pos, PVector size);

	@Override
	public void onKeyType(char key) {
		for (GraphicsComponent g : getCurrentComponents()) {
			g.onKeyType(key);
		}
	}

	@Override
	public void onMousePress(MouseEvent event) {
		for (GraphicsComponent g : getCurrentComponents()) {
			g.onMousePress(event);
		}
	}

	@Override
	public void onMouseRelease(MouseEvent event) {
		for (GraphicsComponent g : getCurrentComponents()) {
			g.onMouseRelease(event);
		}
	}

	@Override
	public void onScroll(MouseEvent event) {
		for (GraphicsComponent g : getCurrentComponents()) {
			g.onScroll(event);
		}
	}

}
