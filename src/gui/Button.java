package gui;

import static main.Program.*;

import java.awt.Color;

import processing.core.*;

public class Button extends Label {

	public static final Color HOVER_COLOR = new Color(0xf9bc63);
	public static final Color BACKGROUND_COLOR = new Color(0xf4c98b);
	public static final Color DISABLED_COLOR = new Color(0xafafae);

	private Color hoverColor, disabledColor, backgroundColor;

	protected IAction onPress;
	protected boolean hoveredOver;

	// top left position
	public Button(String text, IAction onAction) {
		super(text);

		hoverColor = HOVER_COLOR;
		disabledColor = DISABLED_COLOR;
		backgroundColor = BACKGROUND_COLOR;

		font = Fonts.Tw_Cen_MT;
		this.text = text;

		this.onPress = onAction;

		hoveredOver = false;
	}

	@Override
	public void onMousePress(int mouseBtn) {
		if (mouseBtn == PConstants.LEFT && hoveredOver && !disabled) {
			activate();
		}
	}

	public void setOnPress(IAction onPress) {
		this.onPress = onPress;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void activate() {
		onPress.action();
	}

	@Override
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBackgroundColor(int red, int green, int blue) {
		setBackgroundColor(new Color(red, green, blue));
	}

	public void setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public void setHoverColor(int red, int green, int blue) {
		setHoverColor(new Color(red, green, blue));
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		if (disabled) {
			super.currentBackgroundColor = disabledColor;
		} else {
			if (hoveredOver) {
				super.currentBackgroundColor = hoverColor;
			} else {
				super.currentBackgroundColor = backgroundColor;
			}
		}
		renderBackground(pos, size);
		renderText(pos, size);
	}

	@Override
	protected void onUpdate(PVector rel, PVector size) {
		boolean hoveredOver_n = (P.mouseX >= rel.x + padding.left && P.mouseX <= rel.x +size.x-padding.right&& P.mouseY >= rel.y+padding.top && P.mouseY <= rel.y + size.y-padding.bottom);
		if (hoveredOver!=hoveredOver_n) {
			requireGraphicalUpdate();
			hoveredOver= hoveredOver_n;
		}
	}
}