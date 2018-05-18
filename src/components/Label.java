package components;

import static main.Applet.P;

import core.Fonts;
import core.GraphicsComponent;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import util.Color;

public class Label extends GraphicsComponent {

	protected PFont font;
	public int textSize;
	public Color textColor = Color.White;
	public Color backgroundColor = Color.DarkViolet;
	protected Color currentBackgroundColor;

	public Color borderColor = Color.Black;
	public int borderRadius = 5;
	public int borderThickness =0;
	// controls how much inward the text is rendered at, relative to the edge of the
	// label
	public int textBorder = 5;
	
	public boolean transparent= true;

	protected String text;

	public int hAlign;
	public int vAlign;

	public Label(String text, int textSize) {
		font = Fonts.LatoLight;
		this.text = text;
		hAlign = PConstants.CENTER;
		vAlign = PConstants.CENTER;
		currentBackgroundColor = backgroundColor;
		this.textSize=textSize;
	}
	
	public Label(String text) {
		this(text,24);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		requestGraphicalUpdate();
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.currentBackgroundColor = backgroundColor;
	}

	public void setFont(PFont font) {
		this.font = font;
		requestGraphicalUpdate();
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		renderBackground(pos, size);
		renderText(pos, size);
	}

	protected void renderBackground(PVector pos, PVector size) {
		if (transparent) return;
		P.fill(currentBackgroundColor.r,currentBackgroundColor.g,currentBackgroundColor.b);
		float sizeX = size.x - padding.right - padding.left;
		float sizeY = size.y - padding.bottom - padding.top;
		P.strokeWeight(borderThickness);
		P.stroke(borderColor.r,borderColor.g,borderColor.b);
		P.rect(pos.x + padding.left, pos.y + padding.top, sizeX, sizeY, borderRadius);
	}

	protected void renderText(PVector pos, PVector size) {
		P.textAlign(hAlign, vAlign);
		P.fill(textColor.r,textColor.g,textColor.b);
		P.textFont(font);
		P.textSize(textSize);
		float x = pos.x + padding.left + textBorder, y = pos.y + padding.top + textBorder;
		float w = size.x - padding.right - padding.left - 2 * textBorder,
				h = size.y - padding.bottom - padding.top - 2 * textBorder;
		P.text(text, x, y, w, h);

		/*
		 * float yc = y; while(yc<y+h) { P.line(x, yc, x+w, yc); yc +=
		 * P.getGraphics().textLeading; }
		 */
	}

	@Override
	public String toString() {
		return text;
	}
}