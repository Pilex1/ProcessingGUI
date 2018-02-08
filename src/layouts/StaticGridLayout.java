package layouts;

import java.util.ArrayList;

import gui.GraphicsComponent;
import processing.core.PVector;

public class StaticGridLayout extends GridLayout {

	public boolean keepGridSizesEqual = true;

	private int x, y;
	private GraphicsComponent[][] components;

	public StaticGridLayout(int x, int y) {
		this.x = x;
		this.y = y;
		components = new GraphicsComponent[x][y];
	}

	// tries to add component at cell[x,y]
	// returns true if added successfully
	// false otherwise (either there is already another component there
	// or the index is out of bounds
	public boolean addComponent(GraphicsComponent g, int x, int y) {
		if (x >= this.x || y >= this.y)
			return false;
		if (getComponentAt(x, y) != null)
			return false;
		components[x][y] = g;
		return true;
	}

	// tries to add the component into the next available slot
	// if no more slots available, returns false
	public boolean addComponent(GraphicsComponent g) {
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				if (components[i][j] == null) {
					components[i][j] = g;
					return true;
				}
			}
		}
		return false;
	}

	public GraphicsComponent removeComponentAt(int x, int y) {
		GraphicsComponent g = getComponentAt(x, y);
		if (g != null) {
			components[x][y] = null;
		}
		return g;
	}

	@Override
	public GraphicsComponent getComponentAt(int x, int y) {
		if (x >= this.x || y >= this.y)
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
