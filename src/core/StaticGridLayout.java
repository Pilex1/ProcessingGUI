package core;

import java.util.ArrayList;

public class StaticGridLayout extends GridLayout {

	private int x, y;
	private GraphicsComponent[][] components;

	public StaticGridLayout(int x, int y) {
		this.x = x;
		this.y = y;
		components = new GraphicsComponent[x][y];
	}

	@Override
	public boolean addComponent(GraphicsComponent g, int x, int y) {
		if (x >= this.x || y >= this.y || x < 0 || y < 0)
			return false;
		if (getComponent(x, y) != null)
			return false;
		components[x][y] = g;
		recalculateBounds();
		g.parent = this;
		return true;
	}

	// tries to add the component into the next available slot
	// if no more slots available, returns false
	public boolean addComponent(GraphicsComponent g) {
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				if (components[i][j] == null) {
					components[i][j] = g;
					recalculateBounds();
					g.parent = this;
					return true;
				}
			}
		}
		return false;
	}

	public GraphicsComponent removeComponentAt(int x, int y) {
		GraphicsComponent g = getComponent(x, y);
		if (g != null) {
			components[x][y] = null;
			g.parent = null;
		}
		return g;
	}

	@Override
	public GraphicsComponent getComponent(int x, int y) {
		if (x >= this.x || y >= this.y || x < 0 || y < 0)
			return null;
		return components[x][y];
	}

	@Override
	protected ArrayList<GraphicsComponent> getAllComponents() {
		ArrayList<GraphicsComponent> l = new ArrayList<>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				GraphicsComponent g = components[i][j];
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
