package util;

import java.text.*;

import processing.core.*;

public class StringUtil {
	public static String beautify(PVector p) {
		DecimalFormat format = new DecimalFormat("#0.00");
		return "[" + format.format(p.x) + ", " + format.format(p.y) + "]";
	}
}
