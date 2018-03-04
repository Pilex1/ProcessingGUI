package components;

import core.IAction;

public class FloatTextbox extends Textbox{
	
	public boolean allowNegatives = false;

	public FloatTextbox(String s) {
		super(s);
	}
	
	@Override
	public void setOnKeyEnter(IAction onKeyEnter) {
		setEditing(false);
	}
	
	@Override
	public void append(char c) {
		if (!getText().contains(".") && c == '.') {
			super.append(c);
			return;
		}
		
		if (getText().equals("") && c == '-' && allowNegatives) {
			super.append('-');
			return;
		}
		
		String s = getText() + c;
		
		float f = 0;
		try {
			f = Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return;
		}
		super.append(c);
	}
	
	public float getValue() {
		if (getText().equals("-")) return 0;
		return Float.parseFloat(getText());
	}
	
}
