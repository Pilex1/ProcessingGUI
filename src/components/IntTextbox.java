package components;

import core.IAction;

public class IntTextbox extends Textbox {

	public boolean allowNegatives = false;

	public IntTextbox(String text) {
		super(text);
	}
	
	@Override
	public void setOnKeyEnter(IAction onKeyEnter) {
		setEditing(false);
	}

	@Override
	public void append(char c) {
		String s = getText() + c;
		if (s.equals("-")) {
			super.append(c);
			return;
		}
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return;
		}
		if (i < 0 && !allowNegatives)
			return;
		super.append(c);
	}
	
	public int getValue() {
		if (getText().equals("-")) return 0;
		return Integer.parseInt(getText());
	}

}
