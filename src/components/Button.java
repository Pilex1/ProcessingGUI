package components;

import static main.Applet.P;

import core.IAction;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.MouseEvent;
import util.Color;

public class Button extends Label {

	protected Color hoverColor;
	protected Color disabledColor;
	protected Color clickColor;

	public IAction onPress;
	protected boolean hoveredOver = false;
	protected boolean clickedOn = false;
	
	// top left position
	public Button(String text, IAction onPress) {
		super(text);
		hoverColor = Color.Violet;
		clickColor =  Color.LightViolet;
		disabledColor = new Color(0xafafae);
		this.onPress = onPress;
		transparent=false;
	}
	
	@Override
	public void onMousePress(MouseEvent event) {
		if (event.getButton() == PConstants.LEFT && event.getAction() == MouseEvent.PRESS && hoveredOver && !disabled) {
			clickedOn = true;
			requestGraphicalUpdate();
		}

	}

	@Override
	public void onMouseRelease(MouseEvent event) {
		if (event.getButton() == PConstants.LEFT && event.getAction() == MouseEvent.RELEASE && !disabled) {
			if (hoveredOver) {
				activate();
			} else {
				clickedOn = false;
			}
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