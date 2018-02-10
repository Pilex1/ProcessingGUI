package main;

import java.util.Random;

import gui.*;
import layouts.DynamicGridLayout;
import layouts.Frame;
import layouts.GridLayout;
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

		// sample GUI
		GridLayout outerGrid = new StaticGridLayout(2,1);
		outerGrid.keepGridSizesEqual=false;
		{
			DynamicGridLayout innerGrid = new DynamicGridLayout();
			innerGrid.addComponent(new Button("A", () -> {
			}), 0, 0);
			innerGrid.addComponent(new Button("B", () -> {
			}), 1, 1);
			innerGrid.addComponent(new Button("C", () -> {
			}), 3, 2);
			outerGrid.addComponent(innerGrid, 0, 0);
		}
		{
			StaticGridLayout innerGrid = new StaticGridLayout(2, 2);
			innerGrid.addComponent(new Button("X", () -> {
			}), 0, 0);
			innerGrid.addComponent(new Button("Y", () -> {
			}), 1, 0);
			innerGrid.addComponent(new Textbox(), 0, 1);
			innerGrid.addComponent(new Button("W", () -> {
			}), 1, 1);
			innerGrid.maxSize = new PVector(200, 300);
			outerGrid.addComponent(innerGrid, 1, 0);
		}
		frame = new Frame(outerGrid);

	}

	@Override
	public void draw() {
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
