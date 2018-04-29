package core;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

//represents a screen/display on which GraphicsComponents can be added
public abstract class Layout extends GraphicsComponent {
	
	public Color backgroundColor = Color.WHITE;
	
	protected abstract ArrayList<GraphicsComponent> getAllComponents();
	
	// normally, the positions and sizes in a layout are cached to avoid recalculating them
	// every single render/update call
	// however when the min/max sizes of components within a layout change
	// the entire layout must be recalculated
	protected abstract void recalculatePositions(PVector pos, PVector size);
	
	@Override
	public void onKeyType(char key) {
		for (GraphicsComponent g : getAllComponents()) {
			g.onKeyType(key);
		}
	}
	
	@Override
	public void onMousePress(MouseEvent event) {
		for (GraphicsComponent g : getAllComponents()) {
			g.onMousePress(event);
		}
	}
	
	@Override
	public void onMouseRelease(MouseEvent event) {
		for (GraphicsComponent g : getAllComponents()) {
			g.onMouseRelease(event);
		}
	}
}
