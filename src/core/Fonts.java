package core;

import static main.Program.*;

import java.util.Arrays;

import processing.core.*;

public class Fonts {
	public static PFont TwCenMT;
	public static PFont FreeSans;

	public static void Init() {
		Arrays.stream(PFont.list()).forEach(s->System.out.println(s));
		TwCenMT = P.createFont("Tw Cen MT", 1);
		FreeSans=P.createFont("FreeSans", 1);
	}
}
