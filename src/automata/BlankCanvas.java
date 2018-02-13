package automata;

import static main.Program.P;

import core.Canvas;
import processing.core.PImage;
import processing.core.PVector;

public class BlankCanvas extends Canvas {

	@Override
	protected void onResize(float newWidth, float newHeight) {
		if (newWidth < buffer.width) {
			newWidth = buffer.width;
		}
		if (newHeight < buffer.height) {
			newHeight = buffer.height;
		}
		PImage img = buffer.get();
		buffer = P.createGraphics((int) newWidth, (int) newHeight);
		buffer.beginDraw();
		buffer.image(img, 0, 0);
		buffer.endDraw();
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		P.image(buffer.get(0, 0, (int) size.x, (int) size.y), pos.x, pos.y, size.x, size.y);
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		// TODO Auto-generated method stub
		
	}

}
