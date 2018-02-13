package components;

public class DigitTextbox extends Textbox {

	public boolean allowNegatives = false;

	public DigitTextbox(String text) {
		super(text);
	}

	@Override
	public void append(char c) {
		String s = getText() + c;
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
		return Integer.parseInt(getText());
	}

}
