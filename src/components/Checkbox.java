package components;

import java.awt.Color;

import core.GraphicsComponent;
import processing.core.PVector;
import static main.Applet.P;

public class Checkbox extends GraphicsComponent {
	
	public float maxSize = 40;
	
	public Color borderColor = Color.BLACK;
	public int strokeWeight=2;

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		float s = Math.min(maxSize,Math.min(size.x, size.y));
		P.stroke(borderColor.getRGB());
		
	}

}
