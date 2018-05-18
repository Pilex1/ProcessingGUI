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
		if (getText().equals("") && c == '-' && allowNegatives) {
			super.append('-');
			return;
		}
		
		String s = getText() + c;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return;
		}
		super.append(c);
	}
	
	public int getValue() {
		if (getText().equals("-")) return 0;
		return Integer.parseInt(getText());
	}

}
