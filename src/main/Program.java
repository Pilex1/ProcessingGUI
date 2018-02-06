package main;

import java.util.Random;

import gui.*;
import layouts.DynamicGridLayout;
import layouts.Frame;
import layouts.StaticGridLayout;
import processing.core.*;

public class Program extends PApplet {

	public static Program P;

	public final int WIDTH = 1280;
	public final int HEIGHT = 720;
	public boolean[] keys;
	public boolean keyEscape;
	public boolean keyBackspace;
	public Frame frame;

	public Random R = new Random();

	@Override
	public void settings() {
		P = this;
		size(WIDTH, HEIGHT);
	}

	@Override
	public void setup() {
		Fonts.Init();

		keys = new boolean[256];

		// StaticGridLayout grid = new StaticGridLayout(3, 2);
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 2; j++) {
		// grid.addComponent(i, j, new Button("Yes " + i + "," + j, () -> {
		// }));
		// }
		// }
		// frame = new Frame(grid);

		DynamicGridLayout grid = new DynamicGridLayout();
		frame = new Frame(grid);

		// grid.addComponent(new Button("", () -> {
		// grid.removeComponentAt(0, 0);
		// }), 0, 0);
		// grid.addComponent(new Button("", () -> {
		// grid.removeComponentAt(1, 0);
		// }), 1, 0);
		// grid.addComponent(new Button("", () -> {
		// grid.removeComponentAt(2,1);
		// }), 2, 1);
	}

	@Override
	public void draw() {

		background(255);

		DynamicGridLayout grid = ((DynamicGridLayout) frame.layout);
		System.out.println(grid.getSizeX() + "," + grid.getSizeY());

		if (frameCount % 60 == 0) {
			int x = R.nextInt(10);
			int y = R.nextInt(8);

			final int fx = x;
			final int fy = y;
			Button b = new Button(x + "," + y, () -> {
				((DynamicGridLayout) frame.layout).removeComponentAt(fx, fy);
			});
			((DynamicGridLayout) frame.layout).addComponent(b, x, y);

			// x = R.nextInt(10);
			// y = R.nextInt(8);
			// ((DynamicGridLayout)frame.layout).removeComponentAt(x, y);
		}

		frame.update();
		frame.render();
	}

	@Override
	public void mousePressed() {
		frame.onMousePress(mouseButton);
	}

	@Override
	public void keyTyped() {
		frame.onKeyType(key);
	}

	@Override
	public void keyPressed() {
		if (key >= 0 && key <= 255) {
			keys[key] = true;
		}
		if (keyCode == ESC) {
			keyEscape = true;
			key = 0;
		}
		if (keyCode == BACKSPACE) {
			keyBackspace = true;
		}
	}

	@Override
	public void keyReleased() {
		if (key >= 0 && key <= 255) {
			keys[key] = false;
		}
		if (keyCode == ESC) {
			keyEscape = false;
		}
		if (keyCode == BACKSPACE) {
			keyBackspace = false;
		}
	}

	public static void main(String[] args) {
		PApplet.main("main.Program");
	}

}
