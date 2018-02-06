package layouts;

import java.awt.Graphics;
import java.util.ArrayList;

import gui.GraphicsComponent;
import processing.core.PVector;

// a gridlayout that dynamically changes size depending on the number of elements
public class DynamicGridLayout extends Layout {

	private ArrayList<ArrayList<GraphicsComponent>> components = new ArrayList<>();
	private int x, y;

	public DynamicGridLayout() {
	}

	// adds the specified component into cell [x,y]
	// returns true if added successfully, false otherwise (if there is already
	// another component at where you want to add the new component)
	public boolean addComponent(GraphicsComponent g, int x, int y) {
		if (getComponentAt(x, y) != null)
			return false;
		for (int i = this.x; i <= x; i++) {
			components.add(new ArrayList<>());
		}
		ArrayList<GraphicsComponent> gcs = components.get(x);
		for (int i = gcs.size(); i <= y; i++) {
			gcs.add(null);
		}
		gcs.set(y, g);
		updateSize();
		return true;
	}

	public GraphicsComponent getComponentAt(int x, int y) {
		if (x >= this.x)
			return null;
		ArrayList<GraphicsComponent> l = components.get(x);
		if (y >= l.size())
			return null;
		return l.get(y);
	}

	// removes the component at cell [x,y]
	// returns null if the component does not exist
	public GraphicsComponent removeComponentAt(int x, int y) {
		GraphicsComponent g = getComponentAt(x, y);
		if (g != null) {
			ArrayList<GraphicsComponent> l = components.get(x);
			l.set(y, null);
		}
		updateSize();
		return g;
	}

	private int getLength(int column) {
		for (int i = components.get(column).size() - 1; i >= 0; i--) {
			if (components.get(column).get(i) != null) {
				return i + 1;
			}
		}
		return 0;
	}

	private void updateSize() {
		for (int i = components.size() - 1; i >= 0; i--) {
			if (getLength(i) == 0) {
				components.remove(i);
			} else {
				break;
			}
		}
		x = components.size();

		y = 0;
		for (int i = 0; i < components.size(); i++) {
			y = Math.max(y, getLength(i));
		}
		for (int i = 0; i < components.size(); i++) {
			while (components.get(i).size() > y) {
				components.get(i).remove(components.get(i).size() - 1);
			}
		}
	}

	public int getSizeX() {
		return x;
	}

	public int getSizeY() {
		return y;
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		float gridX = (size.x - padding.left - padding.right) / x;
		float gridY = (size.y - padding.top - padding.bottom) / y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				float posX = pos.x + padding.left + i * gridX;
				float posY = pos.y + padding.top + j * gridY;
				GraphicsComponent g = getComponentAt(i, j);
				if (g != null) {
					g.update(new PVector(posX, posY), new PVector(gridX, gridY));
				}
			}
		}
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		float gridX = (size.x - padding.left - padding.right) / x;
		float gridY = (size.y - padding.top - padding.bottom) / y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				float posX = pos.x + padding.left + i * gridX;
				float posY = pos.y + padding.top + j * gridY;
				GraphicsComponent g = getComponentAt(i, j);
				if (g != null) {
					g.render(new PVector(posX, posY), new PVector(gridX, gridY));
				}
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
}
