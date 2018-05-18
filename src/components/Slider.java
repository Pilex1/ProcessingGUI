package components;

import static main.Applet.P;

import core.GraphicsComponent;
import processing.core.PVector;
import util.Color;

public class Slider extends GraphicsComponent {

	public float knobRadius = 5;
	public float sliderSize = 4;
	public float strokeWeight = 2;

	public Color knobColor = Color.DarkViolet;
	public Color knobHoverColor = Color.Violet;
	public Color knobClickColor = Color.LightViolet;
	public Color sliderColor = Color.DarkGrey;
	public Color strokeColor = Color.Black;

	private float min, max, val, incr;

	public Slider(float min, float max, float val, float incr) {
		this.min = min;
		this.max = max;
		this.val = val;
		this.incr = incr;
	}

	public Slider(float max) {
		this(0, max, 0, 1);
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {

	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		P.stroke(strokeColor.r,strokeColor.g,strokeColor.b);
		P.strokeWeight(strokeWeight);
		P.fill(sliderColor.r,sliderColor.g,sliderColor.b);

		float x = pos.x + knobRadius;
		float y = pos.y - sliderSize / 2;
		float w = size.x - 2 * knobRadius;
		float h = sliderSize;
		P.rect(x, y, w, h);
	}

	@Override
	public String toString() {
		return "" + val;
	}

}
