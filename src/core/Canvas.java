package core;

import processing.core.PGraphics;
import processing.core.PVector;
import static main.Program.P;

public abstract class Canvas extends GraphicsComponent {

	// when the canvas needs to be resized, it is resized to a multiple of this
	protected final int resizeIncr = 100;

	protected PGraphics buffer;

	public Canvas() {
		buffer = P.createGraphics(0, 0);
	}

	// resizes the canvas
	void resize(int width, int height) {
		int newWidth = (int) Math.round(Math.ceil((float) width / resizeIncr) * resizeIncr);
		int newHeight = (int) Math.round(Math.ceil((float) height / resizeIncr) * resizeIncr);
		if (newWidth == buffer.width && newHeight == buffer.height)
			return;
		onResize(newWidth, newHeight);
	}

	protected abstract void onResize(float newWidth, float newHeight);

	public void drawRectangle(float x, float y, float width, float height) {
		if (x + width >= buffer.width || y + height >= buffer.height) {
			resize((int) (x + width), (int) (y + height));
		}
		buffer.beginDraw();
		buffer.rect(x, y, width, height);
		buffer.endDraw();
	}

	@Override
	protected abstract void onRender(PVector pos, PVector size);

	public void renderBuffer(PVector pos, PVector size) {
		float extraX=size.x-buffer.width;
		float extraY=size.y-buffer.height;
		P.image(buffer.get(), pos.x+extraX/2, pos.y+extraY/2, buffer.width, buffer.height);
	}

}
