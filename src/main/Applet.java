package main;

import java.util.Random;

import automata.LangtonsAnt;
import automata.RandomCells;
import components.Button;
import components.IntTextbox;
import components.Label;
import components.Textbox;
import core.*;
import processing.core.*;
import processing.event.MouseEvent;
import util.Color;

public class Applet extends PApplet {

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
		size(1280, 720);
		P = this;
		Color.Init();
		// fullScreen();
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
			LangtonsAnt l = new LangtonsAnt(P.width-400,P.height);
			l.square = true;
			l.gridWrap = true;
			layout.addComponent(l);

			StaticGridLayout gui = new StaticGridLayout(1, 12);
			gui.addComponent(new Button("Spawn ant in middle", () -> {
				l.addAnt();
			}));
			gui.addComponent(new Button("Invert all ants", ()->{
				l.invertAnts();
			}));
			gui.addComponent(new Button("Clear world", () -> {
				l.clear();
				l.removeAllAnts();
			}));
			gui.addComponent(new Button("Destroy all ants", () -> {
				l.removeAllAnts();
			}));
			gui.addComponent(new Button("Step",()->{
				l.step();
				l.pause=true;
			}));
			gui.addComponent(new Button("Stop/Start",()->{
				l.pause=!l.pause;
			}));

			Label lbl_rule = new Label("Rule: LR");
			gui.addComponent(lbl_rule);
			Textbox t_rule = new Textbox("LR");
			t_rule.onKeyEnter = () -> {
				if (l.isRuleValid(t_rule.getText())) {
					l.setRule(t_rule.getText());
					l.clear();
					l.removeAllAnts();
					l.addAnt();
					lbl_rule.setText("Rule: " + t_rule.getText());
				}
			};
			gui.addComponent(t_rule);

			gui.addComponent(new Label("Speed"));
			IntTextbox t_speed = new IntTextbox("2");
			t_speed.allowNegatives = true;
			t_speed.onKeyEnter = () -> {
				l.speed = t_speed.getValue();
			};
			gui.addComponent(t_speed);

			gui.addComponent(new Label("Grid size"));
			IntTextbox t_size = new IntTextbox("6");
			t_size.onKeyEnter = () -> {
				l.setGridSize(t_size.getValue());
			};
			gui.addComponent(t_size);

			layout.addComponent(gui);

			l.setRule(t_rule.getText());
			l.speed = t_speed.getValue();
			l.setGridSize(t_size.getValue());
			
			l.addAnt();
		}
		return layout;
	}

	@Override
	public void setup() {
		Fonts.Init();
		keys = new boolean[256];
		frame = new Frame(langton());
	}

	@Override
	public void draw() {
		frame.update();
		frame.render();
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
	public void keyTyped() {
		frame.onKeyType(key);
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
