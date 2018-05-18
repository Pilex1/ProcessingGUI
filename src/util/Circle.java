package util;

import processing.core.*;

public class Circle {

	private PVector pos;
	private float radius;

	public Circle(PVector pos, float radius) {
		this.pos = pos;
		this.radius = radius;
	}

	public boolean isIntersecting(Circle other) {
		return (pos.x - other.pos.x) * (pos.x - other.pos.x) + (pos.y - other.pos.y) * (pos.y - other.pos.y) < radius
				* radius;
	}

}
