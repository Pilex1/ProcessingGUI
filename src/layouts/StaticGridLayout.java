package layouts;

import java.util.ArrayList;

import gui.GraphicsComponent;
import processing.core.PVector;

public class StaticGridLayout extends Layout {

	private int x, y;
	private GraphicsComponent[][] components;

	public StaticGridLayout(int x, int y) {
		this.x = x;
		this.y = y;
		components = new GraphicsComponent[x][y];
	}

	// tries to add component at cell[x,y]
	// returns true if added succesfully
	// false otherwise (either there is already another component there
	// or the index is out of bounds
	public boolean addComponent(int x, int y, GraphicsComponent g) {
		if (x>=this.x||y>=this.y) return false;
		if (getComponentAt(x, y)!=null) return false;
		components[x][y]=g;
		return true;
	}
	
	public GraphicsComponent removeComponentAt(int x, int y) {
		GraphicsComponent g = getComponentAt(x, y);
		if (g!=null) {
			components[x][y]=null;
		}
		return g;
	}
	
	public GraphicsComponent getComponentAt(int x, int y) {
		if (x>=this.x||y>=this.y) return null;
		return components[x][y];
	}
	
	@Override
	protected void onUpdate(PVector pos, PVector size) {
		float gridX = (size.x-padding.left-padding.right) / x;
		float gridY = (size.y-padding.top-padding.bottom) / y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				float posX = pos.x + padding.left + i * gridX;
				float posY = pos.y + padding.top + j * gridY;
				GraphicsComponent g = getComponentAt(i, j);
				if (g!=null) {
					g.update(new PVector(posX, posY), new PVector(gridX, gridY));
				}
			}
		}
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		float gridX = (size.x-padding.left-padding.right) / x;
		float gridY = (size.y-padding.top-padding.bottom) / y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				float posX = pos.x + padding.left + i * gridX;
				float posY = pos.y + padding.top + j * gridY;
				GraphicsComponent g = getComponentAt(i, j);
				if (g!=null) {
					g.render(new PVector(posX, posY), new PVector(gridX, gridY));
				}
			}
		}
	}
	
	@Override
	protected ArrayList<GraphicsComponent> getAllComponents() {
		ArrayList<GraphicsComponent> l = new ArrayList<>();
		for (int i =0;i<x;i++) {
			for (int j =0;j<y;i++) {
				GraphicsComponent g = components[i][j];
				if (g!=null) {
					l.add(g);
				}
			}
		}
		return l;
	}

}
