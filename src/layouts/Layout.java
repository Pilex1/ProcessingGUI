package layouts;

import java.util.ArrayList;

import gui.GraphicsComponent;

//represents a screen/display on which GraphicsComponents can be added
public abstract class Layout extends GraphicsComponent {
	
	protected abstract ArrayList<GraphicsComponent> getAllComponents();
	
	@Override
	public void onKeyType(char key) {
		for (GraphicsComponent g : getAllComponents()) {
			g.onKeyType(key);
		}
	}
	
	@Override
	public void onMousePress(int mouseBtn) {
		for (GraphicsComponent g : getAllComponents()) {
			g.onMousePress(mouseBtn);
		}
	}
}
