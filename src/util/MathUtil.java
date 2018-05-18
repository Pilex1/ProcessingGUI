package util;

import java.util.Random;

import processing.core.PVector;

public class MathUtil {

	/**
	 * returns a random integer between low (inclusive) and high (exclusive)
	 * 
	 * @param rand
	 * @param low
	 * @param high
	 * @return
	 */
	public static int randInt(Random rand, int low, int high) {
		return rand.nextInt(high - low) + low;
	}

	/**
	 * returns a random float between low and high
	 * 
	 * @param rand
	 * @param low
	 * @param high
	 * @return
	 */
	public static float randFloat(Random rand, float low, float high) {
		return rand.nextFloat() * (high - low) + low;
	}
	
	public static PVector randVector(Random rand, float low, float high) {
		return new PVector(randFloat(rand,low,high),randFloat(rand,low,high));
	}

	public static int max(int... vals) {
		int max = vals[0];
		for (int i = 1; i < vals.length; i++) {
			max = Math.max(max, vals[i]);
		}
		return max;
	}

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

	public static int min(int... vals) {
		int min = vals[0];
		for (int i = 1; i < vals.length; i++) {
			min = Math.min(min, vals[i]);
		}
		return min;
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
			if (b)
				count++;
		}
		return count;
	}

	public static int countFalse(boolean[] arr) {
		int count = 0;
		for (boolean b : arr) {
			if (!b)
				count++;
		}
		return count;
	}

	public static PVector clampAbsolute(PVector p, PVector bounds) {
		PVector r = p.copy();
		r.x = Math.signum(p.x) * Math.min(Math.abs(p.x), Math.abs(bounds.x));
		r.y = Math.signum(p.y) * Math.min(Math.abs(p.y), Math.abs(bounds.y));
		return r;
	}

}
