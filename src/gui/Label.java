package gui;

import static main.Program.*;

import java.awt.Color;

import processing.core.*;

public class Label extends GraphicsComponent {

	protected Object tag;

	protected PFont font;
	protected int textSize;
	protected Color textColor;
	protected Color currentBackgroundColor;
	protected Color borderColor = Color.DARK_GRAY;

	protected String text;

	protected int hAlign;
	protected int vAlign;

	public Label(String text) {
		currentBackgroundColor = new Color(0, 0, 0, 0);

		font = Fonts.Tw_Cen_MT;
		textSize = 32;
		textColor = Color.BLACK;
		currentBackgroundColor = Color.LIGHT_GRAY;

		this.text = text;

		hAlign = PConstants.CENTER;
		vAlign = PConstants.CENTER;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.currentBackgroundColor = backgroundColor;
	}

	public void setFont(PFont font) {
		this.font = font;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public int getHAlign() {
		return hAlign;
	}

	public void setHAlign(int hAlign) {
		this.hAlign = hAlign;
	}

	public int getVAlign() {
		return vAlign;
	}

	public void setVAlign(int vAlign) {
		this.vAlign = vAlign;
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
		//float strokeWeight = Math.min(sizeX, sizeY)/20;
		//strokeWeight=Math.min(5, Math.max(1, strokeWeight));
		P.strokeWeight(2);
		P.stroke(borderColor.getRGB(), borderColor.getAlpha());
		P.rect(pos.x + padding.left, pos.y + padding.top, sizeX, sizeY, 15);
	}

	protected void renderText(PVector relPos, PVector relSize) {
		P.textAlign(hAlign, vAlign);
		P.fill(textColor.getRGB(), textColor.getAlpha());
		P.textFont(font);
		P.textSize(textSize);
		P.text(text, relPos.x + padding.left, relPos.y + padding.top, relSize.x - padding.right - padding.left,
				relSize.y - padding.bottom - padding.top);
	}
}