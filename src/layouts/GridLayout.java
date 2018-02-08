package layouts;

import gui.GraphicsComponent;
import processing.core.PVector;

public abstract class GridLayout extends Layout {

	protected abstract GraphicsComponent getComponentAt(int x, int y);

	public abstract int getSizeX();

	public abstract int getSizeY();

	@Override
	protected void onRender(PVector pos, PVector size) {
		float gridX = (size.x - padding.left - padding.right) / getSizeX();
		float gridY = (size.y - padding.top - padding.bottom) / getSizeY();
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
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
	protected void onUpdate(PVector pos, PVector size) {
		float gridX = (size.x - padding.left - padding.right) / getSizeX();
		float gridY = (size.y - padding.top - padding.bottom) / getSizeY();
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				float posX = pos.x + padding.left + i * gridX;
				float posY = pos.y + padding.top + j * gridY;
				GraphicsComponent g = getComponentAt(i, j);
				if (g != null) {
					g.update(new PVector(posX, posY), new PVector(gridX, gridY));
				}
			}
		}
	}

}
