package automata;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PVector;
import util.Vector2i;

public class LangtonsAnt extends Grid<Integer> {

	private ArrayList<Ant> ants = new ArrayList<>();
	private ArrayList<Ant> antsToBeRemoved = new ArrayList<>();
	private Move[] rule;

	public Color[] colors = new Color[] { new Color(0xFFFFFF), new Color(0x96858F), new Color(0x6D7993), new Color(0x9099A2),
			new Color(0x062F4F), new Color(0x813772), new Color(0xCAEBF2), new Color(0xC09F80) };

	// speed of -2 = 1 update per 4 frames
	// speed of -1 = 1 update per 2 frames
	// speed of 0 = 1 update per frame
	// speed of 1 = 2 updates per frame
	// speed of 2 = 4 updates per frame
	// etc.
	public int speed = 4;
	private int frameCount = 0;

	public LangtonsAnt(String rule) {
		setRule(rule);
	}

	public LangtonsAnt() {
		this("RL");
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);
		frameCount++;
		if (speed <= 0) {
			if (frameCount % (int) Math.pow(2, -speed) == 0) {
				for (Ant ant : ants) {
					ant.move();
				}
			}
		} else {
			for (int i = 0; i < Math.pow(2, speed); i++) {
				for (Ant ant : ants) {
					ant.move();
				}
			}
		}
		ants.removeAll(antsToBeRemoved);
	}

	private class Move {
		char action;
		int amount;

		Move(char action, int amount) {
			this.action = action;
			this.amount = amount;
		}
	}

	private class Ant {
		int x, y;
		Direction dir = Direction.NORTH;

		Ant(int x, int y) {
			this.x = x;
			this.y = y;
		}

		void move() {
			Integer state = getCell(x, y);
			if (state == null) {
				// the ant has reached the edge of the grid, so destroy the ant
				antsToBeRemoved.add(this);
				return;
			}
			Move move = rule[state];
			if (move.action == 'N') {
				dir = Direction.NORTH;
			} else if (move.action == 'E') {
				dir = Direction.EAST;
			} else if (move.action == 'S') {
				dir = Direction.SOUTH;
			} else if (move.action == 'W') {
				dir = Direction.WEST;
			} else if (move.action == 'L') {
				dir = dir.turnLeft();
			} else if (move.action == 'R') {
				dir = dir.turnRight();
			}

			for (int i = 0; i < move.amount; i++) {
				int newState = (getCell(x, y) + 1) % getStates();
				// note this is not quite how it is implemented in original!!!
				// by original i mean the one i made in 2015/2016
				setCell(x, y, newState);
				switch (dir) {
				case NORTH:
					y--;
					break;
				case EAST:
					x++;
					break;
				case SOUTH:
					y++;
					break;
				case WEST:
					x--;
					break;
				}
			}

		}
	}

	// how many states a cell can be on
	// dependent on the number of movement characters in the rule
	// by default (LR) equals 2, since a cell is either white or black
	public int getStates() {
		return rule.length;
	}

	public boolean isRuleValid(String rule) {
		Move[] ruleCopy = this.rule;
		boolean valid = setRule(rule);
		this.rule = ruleCopy;
		return valid;
	}

	public void setRule(int x) {
		String s = Integer.toBinaryString(x);
		rule = new Move[s.length()];
		for (int i = 0; i < s.length(); i++) {
			rule[i] = (s.charAt(i) == '0') ? new Move('L', 1) : new Move('R', 1);
		}
	}

	// returns whether the rule has been successfully set
	// if this returns false, then the rule is not valid
	public boolean setRule(String rule) {
		try {
			int x = Integer.parseInt(rule);
			setRule(x);
			return true;
		} catch (NumberFormatException e) {
			
		}
		ArrayList<Move> moves = new ArrayList<>();
		for (int i = 0; i < rule.length();) {
			char c = rule.charAt(i);
			if (!(c == 'N' || c == 'E' || c == 'S' || c == 'W' || c == 'L' || c == 'R')) {
				System.err.println("Invalid rule: " + rule);
				return false;
			}
			String number = "";
			int n = 1;
			int j = i + 1;

			while (j < rule.length() && Character.isDigit(rule.charAt(j))) {
				number += rule.charAt(j);
				j++;
			}
			if (j == rule.length() && number.equals("")) {
				moves.add(new Move(c, 1));
				break;
			}

			try {
				if (number.equals("")) {
					n = 1;
				} else {
					n = Integer.parseInt(number);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid rule: " + rule);
				return false;
			}
			moves.add(new Move(c, n));
			if (i == rule.length() - 1)
				break;
			i = j;

		}
		this.rule = moves.toArray(new Move[0]);
		return true;
	}

	public void addAnt(int x, int y) {
		ants.add(new Ant(x, y));
	}
	
	/**
	 * Adds an ant to the center of the grid
	 */
	public void addAnt() {
		addAnt(getCenterX(),getCenterY());
	}

	public void removeLastAnt() {
		if (ants.size() == 0)
			return;
		ants.remove(ants.size() - 1);
	}

	public void removeAllAnts() {
		ants.clear();
	}

	@Override
	protected Integer getDefault() {
		return 0;
	}

	@Override
	protected Color getColor(Integer t) {
		/*
		 * float f = 1 - (float) t / (getStates() - 1); return new Color(f, f, f);
		 */
		t = t % colors.length;
		return colors[t];
	}

}
