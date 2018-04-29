package util;

import processing.core.PVector;

public class MathUtil {

	public static float max(float[] arr) {
		float max = Float.MIN_VALUE;
		for (float f : arr) {
			max = Math.max(max, f);
		}
		return max;
	}

	// ignores arr[i] if ignore[i] == true while calculating max
	public static float max(float[] arr, boolean[] ignore) {
		float max = Float.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (i < ignore.length && ignore[i])
				continue;
			max = Math.max(max, arr[i]);
		}
		return max;
	}

	public static float min(float[] arr) {
		float min = Float.MAX_VALUE;
		for (float f : arr) {
			min = Math.min(min, f);
		}
		return min;
	}

	// ignores arr[i] if ignore[i] == true while calculating min
	public static float min(float[] arr, boolean[] ignore) {
		float min = Float.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (i < ignore.length && ignore[i])
				continue;
			min = Math.min(min, arr[i]);
		}
		return min;
	}
	
	public static int countTrue(boolean[] arr) {
		int count = 0;
		for (boolean b : arr) {
			if (b) count++;
		}
		return count;
	}
	
	public static int countFalse(boolean[] arr) {
		int count = 0;
		for (boolean b : arr) {
			if (!b) count++;
		}
		return count;
	}

	public static PVector clampAbsolute(PVector p, PVector bounds) {
		PVector  r = p.copy();
		r.x = Math.signum(p.x) * Math.min(Math.abs(p.x), Math.abs(bounds.x));
		r.y = Math.signum(p.y) * Math.min(Math.abs(p.y), Math.abs(bounds.y));
		return r;
	}
	
}
