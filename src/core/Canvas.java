package core;

import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import util.Color;
import util.Rectangle;

import static main.Applet.P;

import java.awt.Image;

public abstract class Canvas extends GraphicsComponent {

	// stores the PGraphics object to draw to
	// by default returns an off-screen buffer
	// however some Canvases (e.g. GameCanvas) draw directly
	// to the screen, so this would be set to
	// P.graphics
	protected PGraphics graphics;

	protected PGraphics buffer;
	private boolean draw;

	public Canvas(int width, int height) {
		setBufferSize(width, height);
		setMinSize(width, height);
		setMaxSize(width, height);
	}

	protected void setBufferSize(int width, int height) {
		buffer = P.createGraphics(width, height);
		buffer.beginDraw();
		buffer.background(255);
		buffer.endDraw();
		graphics = buffer;
	}

	public Image getImage() {
		return graphics.image;
	}

	public int getWidth() {
		return graphics.width;
	}

	public int getHeight() {
		return graphics.height;
	}

	protected void renderBuffer(PVector pos, PVector size) {
		endDraw();
		float spaceX = size.x - buffer.width;
		float spaceY = size.y - buffer.height;
		P.image(buffer, pos.x + spaceX / 2, pos.y + spaceY / 2, buffer.width, buffer.height);
	}

	@Override
	protected abstract void onRender(PVector pos, PVector size);

	private void checkDrawState() {
		if (this instanceof GameCanvas)
			return;
		beginDraw();
	}

	public void beginDraw() {
		if (!draw) {
			buffer.beginDraw();
			draw = true;
		}
	}

	public void endDraw() {
		if (draw) {
			buffer.endDraw();
			draw = false;
		}
	}

	public void background(int r, int g, int b, int a) {
		checkDrawState();
		graphics.background(r, g, b, a);
	}

	public void background(int r, int g, int b) {
		background(r, g, b, 255);
	}

	public void background(Color c) {
		background(c.r, c.g, c.b, c.a);
	}

	public void strokeWeight(float weight) {
		checkDrawState();
		if (weight == 0) {
			graphics.noStroke();
		} else {
			graphics.strokeWeight(weight);
		}
	}

	public void noStroke() {
		strokeWeight(0);
	}

	public void stroke(int r, int g, int b, int a) {
		checkDrawState();
		graphics.stroke(r, g, b, a);
	}

	public void stroke(int r, int g, int b) {
		stroke(r, g, b, 255);
	}

	public void stroke(Color c) {
		stroke(c.r, c.g, c.b, c.a);
	}

	public void fill(int r, int g, int b, int a) {
		checkDrawState();
		graphics.fill(r, g, b, a);
	}

	public void fill(int r, int g, int b) {
		fill(r, g, b, 255);
	}

	public void fill(Color c) {
		fill(c.r, c.g, c.b, c.a);
	}

	public void fill(java.awt.Color c) {
		fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public void ellipse(float centerX, float centerY, float radiusX, float radiusY) {
		checkDrawState();
		graphics.ellipse(centerX, centerY, radiusX, radiusY);
	}

	public void circle(float centerX, float centerY, float radius) {
		ellipse(centerX, centerY, radius, radius);
	}

	public void roundedRect(float x, float y, float width, float height, float radius) {
		checkDrawState();
		graphics.rect(x, y, width, height, radius);
	}

	public void roundedRect(Rectangle rect, float radius) {
		roundedRect(rect.getX1(), rect.getY1(), rect.getWidth(), rect.getHeight(), radius);
	}

	public void roundedRect(Rectangle rect, float radius, PVector camera) {
		roundedRect(rect.fromCamera(camera), radius);
	}

	public void rect(float x, float y, float width, float height) {
		checkDrawState();
		graphics.rect(x, y, width, height);
	}

	public void rect(Rectangle rect) {
		rect(rect.getX1(), rect.getY1(), rect.getWidth(), rect.getHeight());
	}

	public void rect(Rectangle rect, PVector camera) {
		rect(rect.fromCamera(camera));
	}

	public void image(PImage img, float x, float y) {
		checkDrawState();
		graphics.image(img, x, y);
	}

	public void image(PImage img, PVector pos) {
		image(img, pos.x, pos.y);
	}

	public void image(PImage img, PVector pos, PVector camera) {
		image(img, PVector.sub(pos, camera));
	}

	public void image(PImage img, float x, float y, PVector camera) {
		image(img, new PVector(x, y), camera);
	}

	public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		checkDrawState();
		graphics.triangle(x1, y1, x2, y2, x3, y3);
	}

	public void text(String s, float x, float y) {
		checkDrawState();
		graphics.text(s, x, y);
	}

	public void text(String s, float x, float y, float width, float height) {
		checkDrawState();
		graphics.text(s, x, y, x + width, y + height);
	}

	public void textSize(float size) {
		checkDrawState();
		graphics.textSize(size);
	}

	public void textAlign(int hAlign, int vAlign) {
		checkDrawState();
		graphics.textAlign(hAlign, vAlign);
	}

	public void textFont(PFont font, float size) {
		checkDrawState();
		graphics.textFont(font, size);
	}

	public void tint(Color color) {
		checkDrawState();
		graphics.tint(color.r, color.g, color.b, color.a);
	}

	public void transparency(float alpha) {
		checkDrawState();
		graphics.tint(255, 255, 255, alpha);
	}

}
