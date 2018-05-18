package core;

import static main.Applet.*;

import processing.core.*;

public class Fonts {

	public static PFont LatoLight;
	public static PFont Japanese;

	public static void Init() {
		// Arrays.stream(PFont.list()).forEach(s->System.out.println(s));

		LatoLight = P.loadFont("res/Lato-Light-48.vlw");
		Japanese = P.createFont("Japanese", 48);
	}
}
