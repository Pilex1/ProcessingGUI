package util;

import java.io.*;

import processing.core.*;

public class Rectangle implements Serializable {

	private static final long serialVersionUID = 1L;

	// top left
	private PVector pos;
	private PVector size;
	
	public Rectangle(float x, float y, float width, float height) {
		this(new PVector(x,y), new PVector(width,height));
	}

	public Rectangle(PVector pos, PVector size) {
		this.pos = pos.copy();
		this.size = size.copy();
	}

	// makes sure that size is positive
	public Rectangle regularise() {
		PVector pos = this.pos.copy();
		PVector size = this.size.copy();
		if (size.x < 0) {
			pos.x += size.x;
			size.x *= -1;
		}
		if (size.y < 0) {
			pos.y += size.y;
			size.y *= -1;
		}
		return new Rectangle(pos, size);
	}

	public boolean inside(PVector point) {
		return point.x >= getX1() && point.x <= getX2() && point.y >= getY1() && point.y <= getY2();
	}

	public void setPos(PVector pos) {
		this.pos = pos.copy();
	}

	public void setSize(PVector size) {
		this.size = size.copy();
	}

	public PVector getSize() {
		return size;
	}

	public void decrX(float x) {
		pos.x -= x;
	}

	public void decrY(float y) {
		pos.y -= y;
	}

	public void incrX(float x) {
		pos.x += x;
	}

	public void incrY(float y) {
		pos.y += y;
	}

	public void setX1(float x) {
		pos.x = x;
	}

	public void setX2(float x) {
		pos.x = x - size.x;
	}

	public void setY1(float y) {
		pos.y = y;
	}

	public void setY2(float y) {
		pos.y = y - size.y;
	}

	public void setWidth(float w) {
		size.x = w;
	}

	public void setHeight(float h) {
		size.y = h;
	}

	// left
	public float getX1() {
		return pos.x;
	}

	// right
	public float getX2() {
		return pos.x + size.x;
	}

	// top
	public float getY1() {
		return pos.y;
	}

	// bottom
	public float getY2() {
		return pos.y + size.y;
	}

	public float getWidth() {
		return size.x;
	}

	public float getHeight() {
		return size.y;
	}

	public PVector getCenter() {
		return new PVector(getCenterX(), getCenterY());
	}

	public float getCenterX() {
		return (getX1() + getX2()) / 2;
	}

	public float getCenterY() {
		return (getY1() + getY2()) / 2;
	}

	public Rectangle fromCamera(PVector camera) {
		return new Rectangle(PVector.sub(pos, camera), size.copy());
	}

	public boolean isIntersecting(Rectangle other, float margin) {
		Rectangle increased = new Rectangle(PVector.sub(pos, new PVector(margin, margin)),
				PVector.add(size, new PVector(2 * margin, 2 * margin)));
		return increased.equals(other);
	}

	public boolean isIntersecting(Rectangle other) {
		return isIntersecting(other, false);
	}

	public boolean isIntersecting(Rectangle other, boolean countEdges) {
		if (countEdges) {
			return this.getX1() <= other.getX2() && this.getX2() >= other.getX1() && this.getY2() >= other.getY1()
					&& this.getY1() <= other.getY2();
		} else {
			return this.getX1() < other.getX2() && this.getX2() > other.getX1() && this.getY2() > other.getY1()
					&& this.getY1() < other.getY2();
		}
	}

	// returns if the given side of THIS rectangle is intersecting with the other
	// rectangle
	public boolean isIntersecting(Rectangle other, Side side) {
		if (!isIntersecting(other, true))
			return false;
		switch (side) {
		case BOTTOM:
			return other.getY1() <= this.getY2() && other.getY2() >= this.getY2();
		case LEFT:
			return other.getX2() >= this.getX1() && other.getX1() <= this.getX1();
		case RIGHT:
			return other.getX1() <= this.getX2() && other.getX2() >= this.getX2();
		case TOP:
			return other.getY2() >= this.getY1() && other.getY1() <= this.getY1();
		default:
			throw new RuntimeException();
		}
	}

	public PVector topLeft() {
		return new PVector(pos.x, pos.y);
	}

	public PVector topRight() {
		return new PVector(pos.x + size.x, pos.y);
	}

	public PVector bottomLeft() {
		return new PVector(pos.x, pos.y + size.y);
	}

	public PVector bottomRight() {
		return new PVector(pos.x + size.x, pos.y + size.y);
	}

	public Rectangle copy() {
		return new Rectangle(pos.copy(), size.copy());
	}

}
