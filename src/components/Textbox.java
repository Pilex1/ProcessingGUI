package components;


import core.IAction;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.MouseEvent;
import util.Color;

public class Textbox extends Button {

	private Color editColor;

	private boolean editing;
	private boolean rescindNextUnedit;

	public IAction onKeyTab = () -> {
	};
	public IAction onKeyEnter = () -> append(System.lineSeparator());

	// top left pos
	public Textbox(String text) {
		super(text, new IAction() {
			@Override
			public void action() {
			}
		});
		hAlign = PConstants.LEFT;

		editColor = Color.VeryLightViolet;
	}

	public Textbox() {
		this("");
	}

	@Override
	public void onKeyType(char key) {
		if (editing) {
			if (key == PConstants.BACKSPACE) {
				delete();
			} else if (key == PConstants.TAB) {
				onKeyTab.action();
			} else if (key == PConstants.ENTER) {
				onKeyEnter.action();
			} else if (key == PConstants.ESC) {
				editing = false;
			} else {
				append(key);
			}
			requestGraphicalUpdate();
		}
	}

	@Override
	public void onMousePress(MouseEvent event) {
		if (editing) {
			if (event.getButton() == PConstants.LEFT) {
				if (rescindNextUnedit) {
					rescindNextUnedit = false;
				} else {
					editing = false;
				}
				requestGraphicalUpdate();
			}
		} else {
			if (event.getButton() == PConstants.LEFT && hoveredOver) {
				editing = true;
				requestGraphicalUpdate();
			}
		}
	}

	public void setOnKeyTab(IAction onKeyTab) {
		this.onKeyTab = onKeyTab;
	}

	public void setOnKeyEnter(IAction onKeyEnter) {
		this.onKeyEnter = onKeyEnter;
	}

	public void clear() {
		if (text.endsWith("_")) {
			text = "_";
		} else {
			text = "";
		}
	}

	@Override
	public String getText() {
		if (text.endsWith("_")) {
			return text.substring(0, text.length() - 1);
		}
		return text;
	}

	@Override
	public void setText(String s) {
		if (text.endsWith("_")) {
			text = s + "_";
		} else {
			text = s;
		}
	}

	public void append(String s) {
		for (char c : s.toCharArray()) {
			append(c);
		}
	}

	public void append(char c) {
		if (text.endsWith("_")) {
			text = text.substring(0, text.length() - 1) + c + "_";
		} else {
			text += c;
		}
	}

	public void delete() {
		if (text.length() > 1) {
			text = text.substring(0, text.length() - 2) + "_";
		}
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public boolean isEditing() {
		return editing;
	}

	public void rescindNextUnfocus() {
		rescindNextUnedit = true;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (active == false) {
			editing = false;
		}
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);

		if (editing) {
			if (!text.endsWith("_")) {
				text += "_";
			}
		} else {
			if (text.endsWith("_")) {
				text = text.substring(0, text.length() - 1);
			}
		}
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		if (disabled) {
			currentBackgroundColor = disabledColor;
		} else {
			if (editing) {
				currentBackgroundColor = editColor;
			} else {
				if (hoveredOver) {
					currentBackgroundColor = hoverColor;
				} else {
					currentBackgroundColor = backgroundColor;
				}
			}
		}
		renderBackground(pos, size);
		renderText(pos, size);
	}

}
