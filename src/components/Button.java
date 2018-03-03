package components;

import static main.Applet.P;

import java.awt.Color;

import core.IAction;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.MouseEvent;

public class Button extends Label {

	public static final Color HOVER_COLOR = new Color(0xfbbc63);
	public static final Color BACKGROUND_COLOR = new Color(0xf4c98b);
	public static final Color DISABLED_COLOR = new Color(0xafafae);
	public static final Color CLICK_COLOR = new Color(0xffe1b7);

	private Color hoverColor, disabledColor, backgroundColor, clickColor;

	public IAction onPress;
	protected boolean hoveredOver = false;

	// top left position
	public Button(String text, IAction onPress) {
		super(text);
		hoverColor = HOVER_COLOR;
		disabledColor = DISABLED_COLOR;
		backgroundColor = BACKGROUND_COLOR;
		clickColor = CLICK_COLOR;
		this.onPress = onPress;
	}

	@Override
	public void onMousePress(MouseEvent event) {
		if (event.getButton() == PConstants.LEFT && hoveredOver && !disabled) {
			activate();
			requestGraphicalUpdate();
		}
	}

	@Override
	public void onMouseRelease(MouseEvent event) {
		if (hoveredOver) {
			requestGraphicalUpdate();
		}
	}

	public void activate() {
		onPress.action();
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		if (disabled) {
			super.currentBackgroundColor = disabledColor;
		} else {
			if (hoveredOver) {
				if (P.mousePressed && P.mouseButton == PConstants.LEFT) {
					super.currentBackgroundColor = clickColor;
				} else {
					super.currentBackgroundColor = hoverColor;
				}
			} else {
				super.currentBackgroundColor = backgroundColor;
			}
		}
		renderBackground(pos, size);
		renderText(pos, size);
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		boolean hoveredOver_n = (P.mouseX >= pos.x + padding.left && P.mouseX <= pos.x + size.x - padding.right
				&& P.mouseY >= pos.y + padding.top && P.mouseY <= pos.y + size.y - padding.bottom);
		if (hoveredOver != hoveredOver_n) {
			requestGraphicalUpdate();
			hoveredOver = hoveredOver_n;
		}
	}
}