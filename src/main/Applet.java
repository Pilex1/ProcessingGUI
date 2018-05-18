package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import automata.LangtonsAnt;
import automata.LangtonsAnt.Ant;
import automata.LangtonsAntImage;
import automata.RandomCells;
import components.Button;
import components.FloatTextbox;
import components.IntTextbox;
import components.Label;
import components.Textbox;
import core.*;
import processing.core.*;
import processing.event.MouseEvent;
import util.Color;

public class Applet extends PApplet {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT=  720;

	public boolean[] keys;
	public boolean keyEnter;
	public boolean keyEscape;
	public boolean keyBackspace;
	public boolean keyLeft;
	public boolean keyRight;
	public boolean keyUp;
	public boolean keyDown;
	public Frame frame;
	public Random R = new Random();

	public static Applet P;
	
	@Override
	public void settings() {
		size(WIDTH,HEIGHT);
		// DO NOT PUT ANY OTHER PROCESSING FUNCTIONS IN HERE
	}

	@Override
	public void setup() {
		keys = new boolean[256];
		P = this;
		Color.Init();
		Fonts.Init();
		// fullScreen();
	}
	
	protected GraphicsComponent mainComponent() {
return langton();
		//return basicSample();
	}
	
	private Layout basicSample() {
		StaticGridLayout layout  = new StaticGridLayout(1, 2);
		layout.addComponent(new Label("Hello"),0,0);
		layout.addComponent(new Label("World"), 0, 1);
		return layout;
	}

	private Layout sample1() {
		StaticGridLayout layout = new StaticGridLayout(2, 1);
		layout.keepGridSizesEqual = false;
		{
			DynamicGridLayout innerGrid = new DynamicGridLayout();
			innerGrid.addComponent(new Button("A", () -> {
			}), 0, 0);
			innerGrid.addComponent(new Button("B", () -> {
			}), 1, 1);
			innerGrid.addComponent(new Button("C", () -> {
			}), 3, 2);
			layout.addComponent(innerGrid, 0, 0);
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
			innerGrid.setMaxSize(200, 300);
			layout.addComponent(innerGrid, 1, 0);
		}
		return layout;
	}

	private Layout sample2() {
		StaticGridLayout layout = new StaticGridLayout(2, 1);
		layout.keepGridSizesEqual = false;
		{
			RandomCells r = new RandomCells(P.width - 200, P.height);
			layout.addComponent(r);
		}
		{
			DynamicGridLayout inner = new DynamicGridLayout();
			inner.addComponent(new Button("Test 1", () -> {
			}), 0, 0);
			inner.addComponent(new Button("Test 2", () -> {
			}), 0, 1);
			inner.addComponent(new Button("Test 3", () -> {
			}), 0, 2);
			layout.addComponent(inner);
		}
		return layout;
	}

	private Layout langton() {
		StaticGridLayout layout = new StaticGridLayout(2, 1);
		{
			// LangtonsAnt l = new LangtonsAnt(P.width - 500, P.height);
			LangtonsAnt l = new LangtonsAntImage(P.width - 500, P.height, P.loadImage("pic2.png"));
			l.square = true;
			l.gridWrap = true;
			layout.addComponent(l);

			DynamicGridLayout gui = new DynamicGridLayout();
			gui.addComponentToCol(new Button("Spawn ant in middle", () -> {
				l.addAnt();
			}), 0);
			gui.addComponentToCol(new Button("Step", () -> {
				l.step();
				l.pause = true;
			}), 0);
			gui.addComponentToCol(new Button("Stop/Start", () -> {
				l.pause = !l.pause;
			}), 0);
			gui.addComponentToCol(new Button("Invert all ants", () -> {
				l.invertAnts();
			}), 0);
			gui.addComponentToCol(new Button("Clear world", () -> {
				l.clear();
			}), 0);
			gui.addComponentToCol(new Button("Destroy all ants", () -> {
				l.removeAllAnts();
			}), 0);
			gui.addComponentToCol(new Label("Iterations: 0") {
				@Override
				protected void onUpdate(PVector pos, PVector size) {
					if (l.numberOfAnts() > 0)
						setText("Iterations: " + l.getAnt().getIterations());
				}
			}, 0);
			gui.addComponentToCol(new Button("Export to PNG", () -> {
				Image img = l.getImage();
				BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D bGr = bimage.createGraphics();
				bGr.drawImage(img, 0, 0, null);
				bGr.dispose();

				try {
					ImageIO.write(bimage, "png", new File("out.png"));
					System.out.println("File exported");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}), 0);

			Label lbl_rule = new Label("Rule: " + l.getRule());
			gui.addComponentToCol(lbl_rule, 1);
			Textbox t_rule = new Textbox(l.getRule());
			t_rule.onKeyEnter = () -> {
				if (l.isRuleValid(t_rule.getText())) {
					l.setRule(t_rule.getText());
					l.clear();
					l.removeAllAnts();
					l.addAnt();
					lbl_rule.setText("Rule: " + t_rule.getText());
					t_rule.setEditing(false);
				}
			};
			gui.addComponentToCol(t_rule, 1);

			gui.addComponentToCol(new Label("Speed") {
				@Override
				protected void onUpdate(PVector pos, PVector size) {
					setText("Speed: " + l.speed);
				}
			}, 1);
			IntTextbox t_speed = new IntTextbox("2");
			t_speed.allowNegatives = true;
			t_speed.onKeyEnter = () -> {
				l.speed = t_speed.getValue();
				t_speed.setEditing(false);
			};
			gui.addComponentToCol(t_speed, 1);

			gui.addComponentToCol(new Label("Grid size"), 1);
			FloatTextbox t_size = new FloatTextbox("5");
			t_size.onKeyEnter = () -> {
				l.setGridSize(t_size.getValue());
				t_size.setEditing(false);
			};
			gui.addComponentToCol(t_size, 1);

			gui.addComponentToCol(new Label("Position:") {
				@Override
				protected void onUpdate(PVector pos, PVector size) {
					Ant ant = l.getAnt();
					if (ant == null) {
						setText("Position:");
					} else {
						setText("Position: x=" + ant.getX() + " y=" + ant.getY());
					}
				}
			}, 1);
			gui.addComponentToCol(new Label("Direction:") {
				@Override
				protected void onUpdate(PVector pos, PVector size) {
					Ant ant = l.getAnt();
					if (ant == null) {
						setText("Direction:");
					} else {
						setText("Direction: " + ant.getDir());
					}
				}
			}, 1);

			layout.addComponent(gui);

			l.speed = t_speed.getValue();
			l.setGridSize(t_size.getValue());

			l.addAnt();
		}
		return layout;
	}

	@Override
	public void draw() {
		if (frame==null) {
			frame = new Frame(mainComponent());
		}
		frame.update();
		frame.render();
		//P.text("hello", 200, 200);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		frame.onMousePress(event);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		frame.onMouseRelease(event);
	}
	
	@Override
	public void mouseWheel(MouseEvent event) {
		frame.onScroll(event);
	}

	@Override
	public void keyTyped() {
		frame.onKeyType(Character.toLowerCase(key));
	}

	@Override
	public void keyPressed() {
		key = Character.toLowerCase(key);
		if (key >= 0 && key <= 255) {
			keys[key] = true;
		}
		if (keyCode == ESC) {
			keyEscape = true;
			key = 0;
		}
		if (keyCode == BACKSPACE)
			keyBackspace = true;
		if (keyCode == UP)
			keyUp = true;
		if (keyCode == DOWN)
			keyDown = true;
		if (keyCode == LEFT)
			keyLeft = true;
		if (keyCode == RIGHT)
			keyRight = true;
		if (keyCode == ENTER)
			keyEnter = true;
	}

	@Override
	public void keyReleased() {
		key = Character.toLowerCase(key);
		if (key >= 0 && key <= 255) {
			keys[key] = false;
		}
		if (keyCode == ESC) {
			keyEscape = false;
		}
		if (keyCode == BACKSPACE) {
			keyBackspace = false;
		}
		if (keyCode == UP)
			keyUp = false;
		if (keyCode == DOWN)
			keyDown = false;
		if (keyCode == LEFT)
			keyLeft = false;
		if (keyCode == RIGHT)
			keyRight = false;
		if (keyCode == ENTER)
			keyEnter = false;
	}

}
