package core;

import static main.Applet.P;

import java.util.ArrayList;

import processing.core.PVector;
import util.Color;

/**
 * represents a list of layouts, one of which is currently being rendered, and
 * which can be interchanged
 */
public class LayoutList extends Layout {

	private int curLayoutID = -1;
	private ArrayList<Layout> list;

	/**
	 * if set to true, then minimum and maximum sizes are constant, regardless of
	 * which layout is currently active<br>
	 * set to false if minimum and maximum sizes should change depending on the
	 * active layout
	 */
	private boolean constantBoundarySize = false;

	/**
	 * this is set to true when the current layout changes, and we need to clear the
	 * graphics from the previous layout
	 */
	private boolean clearGraphics = false;

	public LayoutList(Layout... layouts) {
		super();
		list = new ArrayList<>();

		for (Layout l : layouts) {
			addLayout(l);
		}
		if (layouts.length > 0) {
			setActiveLayout(0);
		}
	}

	public Layout getActiveLayout() {
		if (list.size() == 0)
			return null;
		return list.get(curLayoutID);
	}

	@Override
	public PVector getMaxSize() {
		return getActiveLayout().getMaxSize();
	}

	@Override
	public PVector getMinSize() {
		return getActiveLayout().getMinSize();
	}

	public ArrayList<GraphicsComponent> getAllComponents(boolean includeLayouts) {
		ArrayList<GraphicsComponent> gcList = new ArrayList<>();
		list.forEach(l -> {
			if (includeLayouts) {
				gcList.add(l);
			}
			gcList.addAll(l.getAllComponents(includeLayouts));
		});
		return gcList;
	}

	public boolean hasConstantBoundarySize() {
		return constantBoundarySize;
	}

	public void setConstantBoundarySize(boolean b) {
		constantBoundarySize = b;
	}

	/**
	 * 
	 * @return true if the layout was successfully set as the active layout<br>
	 *         false if the ID was invalid i.e. outside the range of the list
	 */
	public boolean setActiveLayout(int id) {
		if (id >= list.size()) {
			return false;
		}
		if (id == curLayoutID)
			return true;
		curLayoutID = id;
		if (!constantBoundarySize) {
			setMinSize(list.get(curLayoutID).getMinSize());
			setMaxSize(list.get(curLayoutID).getMaxSize());
		}
		list.get(curLayoutID).requestGraphicalUpdate();
		clearGraphics = true;
		return true;
	}

	/**
	 * 
	 * @return true if the layout was successfully set as the active layout<br>
	 *         false if the layout could not be found<br>
	 *         <b>NB: </b>this is an O(n) operation
	 */
	public boolean setActiveLayout(Layout l) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == l) {
				setActiveLayout(i);
				return true;
			}
		}
		return false;
	}

	public void addLayouts(Layout... ls) {
		for (Layout l : ls) {
			addLayout(l);
		}
	}

	public void addLayout(Layout l) {
		list.add(l);
		l.parent = this;
		if (list.size() == 1) {
			setActiveLayout(0);
		}

		if (constantBoundarySize) {
			// recalculate max and min sizes
			PVector minSize = getMinSize();
			if (l.getMinWidth() > minSize.x) {
				setMinWidth(l.getMinWidth());
			}
			if (l.getMinHeight() > minSize.y) {
				setMinHeight(l.getMinHeight());
			}

			PVector maxSize = getMaxSize();
			if (l.getMaxWidth() < maxSize.x) {
				setMaxWidth(l.getMaxWidth());
			}
			if (l.getMaxHeight() < maxSize.y) {
				setMaxHeight(l.getMaxHeight());
			}
		}
	}

	// gets the number of layouts stored
	public int getSize() {
		return list.size();
	}

	@Override
	public ArrayList<GraphicsComponent> getCurrentComponents() {
		return list.get(curLayoutID).getCurrentComponents();
	}

	@Override
	protected void recalculatePositions(PVector pos, PVector size) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).recalculatePositions(pos, size);
		}
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		if (list.size() == 0)
			return;
		list.get(curLayoutID).onUpdate(pos, size);
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		if (list.size() == 0)
			return;
		if (clearGraphics) {
			// P.noStroke();
			// P.fill(backgroundColor.r, backgroundColor.g, backgroundColor.b,
			// backgroundColor.a);
			// P.rect(pos.x, pos.y, size.x, size.y);
			clearGraphics = false;
		}
		list.get(curLayoutID).onRender(pos, size);
	}

	public ArrayList<Layout> getLayouts() {
		return list;
	}

@Override
public void reset() {
	curLayoutID=0;
}
}
