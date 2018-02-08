package canvas;

import java.awt.Image;

import gui.GraphicsComponent;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import static main.Program.P;

public class Canvas extends GraphicsComponent {

	// when the canvas needs to be resized, it is resized by a multiple of this
	private int resizeIncr = 400;

	protected PGraphics buffer;

	public Canvas() {
		buffer = P.createGraphics(P.WIDTH, P.HEIGHT);
	}

	// resizes the canvas
	// ensure that the new width + height >= current sizes
	private void resize(int width, int height) {
		int newWidth = buffer.width;
		int newHeight = buffer.height;
		while (newWidth < width) {
			newWidth += resizeIncr;
		}
		while (newHeight < height) {
			newHeight += resizeIncr;
		}
		if (newWidth == width && newHeight == height)
			return;
		PImage img = buffer.get();
		buffer = P.createGraphics(width, height);
		buffer.beginDraw();
		buffer.image(img, 0, 0);
		buffer.endDraw();
	}

	public void drawRectangle(float x, float y, float width, float height) {
		resize((int) (x + width), (int) (y + height));
		buffer.rect(x, y, width, height);
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		P.image(buffer.get(0, 0, (int) size.x, (int) size.y), pos.x, pos.y, size.x, size.y);
	}

}
