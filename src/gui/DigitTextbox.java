package gui;

public class DigitTextbox extends Textbox {

	public DigitTextbox() {
	}

	@Override
	public void append(char c) {
		if (!(c >= '0' && c <= '9')) {
			return;
		}
		if (text.endsWith("_")) {
			text = text.substring(0, text.length() - 1) + c + "_";
		} else {
			text += c;
		}
	}

}
