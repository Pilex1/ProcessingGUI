package components;

import static main.Applet.P;

import java.awt.Color;

import core.Fonts;
import core.GraphicsComponent;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class Label extends GraphicsComponent {

	protected PFont font;
	public int textSize;
	public Color textColor;
	public Color currentBackgroundColor;

	public Color borderColor = Color.DARK_GRAY;
	public int borderRadius = 5;
	public int borderThickness = 2;
	// controls how much inward the text is rendered at, relative to the edge of the
	// label
	public int textBorder = 5;

	protected String text;

	public int hAlign;
	public int vAlign;

	public Label(String text) {
		currentBackgroundColor = new Color(0, 0, 0, 0);

		font = Fonts.LatoLight;
		textSize = 18;
		textColor = Color.BLACK;
		currentBackgroundColor = Color.LIGHT_GRAY;

		this.text = text;

		hAlign = PConstants.CENTER;
		vAlign = PConstants.CENTER;
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
		P.fill(currentBackgroundColor.getRGB(), currentBackgroundColor.getAlpha());
		float sizeX = size.x - padding.right - padding.left;
		float sizeY = size.y - padding.bottom - padding.top;
		P.strokeWeight(borderThickness);
		P.stroke(borderColor.getRGB(), borderColor.getAlpha());
		P.rect(pos.x + padding.left, pos.y + padding.top, sizeX, sizeY, borderRadius);
	}

	protected void renderText(PVector pos, PVector size) {
		P.textAlign(hAlign, vAlign);
		P.fill(textColor.getRGB(), textColor.getAlpha());
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