package util;

import java.io.*;

public class Color implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Color Transparent;

	public static Color White;
	public static Color LightGrey;
	public static Color Grey;
	public static Color DarkGrey;
	public static Color Black;

	public static Color LightBlue;
	public static Color MediumBlue;
	public static Color DeepBlue;
	public static Color DarkBlue;
	public static Color LightGreen;
	public static Color MediumGreen;
	public static Color DeepGreen;

	public static Color LightPurple;
	public static Color Purple;
	public static Color Red;
	public static Color LightOrange;

	private static int Alpha = 128;

	public static void Init() {

		LightOrange = new Color(225, 235, 178);
		Purple = new Color(110, 56, 255);
		LightPurple = new Color(206, 142, 255);
		Red = new Color(255, 58, 104);

		Transparent = new Color(255, 255, 255, 0);

		White = new Color(255);
		LightGrey = new Color(192);
		Grey = new Color(128);
		DarkGrey = new Color(64);
		Black = new Color(0);

		LightBlue = new Color(22, 79, 184);
		MediumBlue = new Color(37, 101, 218);
		DeepBlue = new Color(136, 190, 244);
		DarkBlue = new Color(38, 66, 112);
		LightGreen = new Color(22, 184, 79);
		MediumGreen = new Color(37, 218, 101);
		DeepGreen = new Color(136, 244, 190);
	}

	public int r, g, b, a;

	public Color(java.awt.Color color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public Color(int hex) {
		this((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, (hex & 0xFF));
	}

	public Color Transparent() {
		return new Color(r, g, b, Alpha);
	}

	public Color copy() {
		return new Color(r, g, b, a);
	}

	public int getBrightness() {
		return (r + g + b) / 3;
	}

}
