package core;

import java.util.ArrayList;

// a gridlayout that dynamically changes size depending on the number of elements
public class DynamicGridLayout extends GridLayout {

	private ArrayList<ArrayList<GraphicsComponent>> components = new ArrayList<>();
	private int x, y;

	public DynamicGridLayout() {
	}

	public boolean addComponentToCol(GraphicsComponent g, int col) {
		if (col < 0)
			return false;
		addComponent(g, col, getColumnLength(col));
		return true;
	}

	public boolean addComponentToRow(GraphicsComponent g, int row) {
		if (row < 0)
			return false;
		addComponent(g, getRowLength(row), row);
		return true;
	}

	@Override
	public boolean addComponent(GraphicsComponent g, int x, int y) {
		if (x < 0 || y < 0)
			return false;
		if (getComponent(x, y) != null)
			return false;
		// fill up any uninitialised columns with an empty list until we reach our
		// target column
		for (int i = this.x; i <= x; i++) {
			components.add(new ArrayList<>());
		}
		// gets the column that we should populate
		ArrayList<GraphicsComponent> gcs = components.get(x);
		// fills up any empty space in that column until we reach our target x value
		for (int i = gcs.size(); i <= y; i++) {
			gcs.add(null);
		}
		gcs.set(y, g);

		updateSize();
		recalculateBounds();
		g.parent = this;
		return true;
	}

	@Override
	public GraphicsComponent getComponent(int x, int y) {
		if (x < 0 || y < 0 || x >= this.x)
			return null;
		ArrayList<GraphicsComponent> l = components.get(x);
		if (y >= l.size())
			return null;
		return l.get(y);
	}

	// removes the component at cell [x,y]
	// returns null if the component does not exist
	public GraphicsComponent removeComponent(int x, int y) {
		GraphicsComponent g = getComponent(x, y);
		if (g != null) {
			ArrayList<GraphicsComponent> l = components.get(x);
			l.set(y, null);
			g.parent = null;
		}
		updateSize();
		return g;
	}

	public int getRowLength(int row) {
		int length = 0;
		for (int col = 0; col < x; col++) {
			if (getComponent(col, row) != null) {
				length = col + 1;
			}
		}
		return length;
	}

	public int getColumnLength(int column) {
		if (column >= components.size())
			return 0;
		if (column < 0)
			return 0;
		for (int i = components.get(column).size() - 1; i >= 0; i--) {
			if (components.get(column).get(i) != null) {
				return i + 1;
			}
		}
		return 0;
	}

	private void updateSize() {
		for (int i = components.size() - 1; i >= 0; i--) {
			if (getColumnLength(i) == 0) {
				components.remove(i);
			} else {
				break;
			}
		}
		x = components.size();

		y = 0;
		for (int i = 0; i < components.size(); i++) {
			y = Math.max(y, getColumnLength(i));
		}
		for (int i = 0; i < components.size(); i++) {
			while (components.get(i).size() > y) {
				components.get(i).remove(components.get(i).size() - 1);
			}
		}
	}

	@Override
	protected ArrayList<GraphicsComponent> getAllComponents() {
		ArrayList<GraphicsComponent> l = new ArrayList<>();
		for (int i = 0; i < components.size(); i++) {
			for (int j = 0; j < components.get(i).size(); j++) {
				GraphicsComponent g = components.get(i).get(j);
				if (g != null) {
					l.add(g);
				}
			}
		}
		return l;
	}

	@Override
	public int getSizeX() {
		return x;
	}

	@Override
	public int getSizeY() {
		return y;
	}
}
