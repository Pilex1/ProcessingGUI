package automata;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PVector;

public class LangtonsAnt extends Grid<Integer> {

	public boolean pause = true;

	private ArrayList<Ant> ants = new ArrayList<>();
	private ArrayList<Ant> antsToBeRemoved = new ArrayList<>();
	private char[] rule;

	public Color[] colors = new Color[] { new Color(0xFFFFFF), new Color(0x96858F), new Color(0x6D7993),
			new Color(0x9099A2), new Color(0x062F4F), new Color(0x813772), new Color(0xCAEBF2), new Color(0xC09F80) };

	// speed of -2 = 1 update per 4 frames
	// speed of -1 = 1 update per 2 frames
	// speed of 0 = 1 update per frame
	// speed of 1 = 2 updates per frame
	// speed of 2 = 4 updates per frame
	// etc.
	public int speed = 4;
	private int frameCount = 0;

	public LangtonsAnt(int width, int height, String rule) {
		super(width, height);
		setRule(rule);
		
		buffer.beginDraw();
		for (int i = (int)(1.0/3*getGridX()); i <(int)(2.0/3*getGridX()); i++) {
			for (int j = 0; j  < getGridY();j++) {
				setCell(i, j, 1);
			}
		}
		buffer.endDraw();
	}

	public LangtonsAnt(int width, int height) {
		this(width, height, "RL");
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);
		if (pause)
			return;
		buffer.beginDraw();
		frameCount++;
		if (speed <= 0) {
			if (frameCount % (int) Math.pow(2, -speed) == 0) {
				step();
			}
		} else {
			for (int i = 0; i < Math.pow(2, speed); i++) {
				step();
			}
		}
		ants.removeAll(antsToBeRemoved);
		buffer.endDraw();
	}

	public void invertAnts() {
		ArrayList<Ant> newAnts = new ArrayList<>();
		for (Ant ant : ants) {
			InverseAnt inverse = new InverseAnt(ant);
			newAnts.add(inverse);
		}
		ants = newAnts;
	}

	public void step() {
		for (Ant ant : ants) {
			ant.move();
		}
	}

	private class Ant {
		int x, y;
		Direction dir;

		Ant(int x, int y) {
			this(x, y, Direction.NORTH);
		}

		Ant(int x, int y, Direction dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		void move() {

			// rotate based on color of current cell
			// changes the color of current cell
			// moves in the new direction

			Integer state = getCell(x, y);
			if (state == null) {
				// the ant has reached the edge of the grid, so destroy the ant
				antsToBeRemoved.add(this);
				return;
			}

			char move = rule[state];
			if (move == 'N') {
				dir = Direction.NORTH;
			} else if (move == 'E') {
				dir = Direction.EAST;
			} else if (move == 'S') {
				dir = Direction.SOUTH;
			} else if (move == 'W') {
				dir = Direction.WEST;
			} else if (move == 'L') {
				dir = dir.turnLeft();
			} else if (move == 'R') {
				dir = dir.turnRight();
			}

			int newState = (getCell(x, y) + 1) % getStates();
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

	private class InverseAnt extends Ant {

		InverseAnt(int x, int y, Direction dir) {
			super(x, y);
			this.dir = dir;
			switch (dir) {
			case NORTH:
				this.y--;
				break;
			case EAST:
				this.x++;
				break;
			case SOUTH:
				this.y++;
				break;
			case WEST:
				this.x--;
				break;
			}
		}

		InverseAnt(Ant ant) {
			this(ant.x, ant.y, ant.dir.opposite());
		}

		@Override
		void move() {

			// invert the color of current cell
			//

			setCell(x, y, (getCell(x, y) - 1 + getStates()) % getStates());

			Integer state = getCell(x, y);
			char move = rule[state];
			if (move == 'N') {
				dir = Direction.NORTH;
			} else if (move == 'E') {
				dir = Direction.EAST;
			} else if (move == 'S') {
				dir = Direction.SOUTH;
			} else if (move == 'W') {
				dir = Direction.WEST;
			} else if (move == 'L') {
				dir = dir.turnRight();
			} else if (move == 'R') {
				dir = dir.turnLeft();
			}

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

	// how many states a cell can be on
	// dependent on the number of movement characters in the rule
	// by default (LR) equals 2, since a cell is either white or black
	public int getStates() {
		return rule.length;
	}

	public boolean isRuleValid(String rule) {
		for (int i = 0; i < rule.length(); i++) {
			char c = rule.charAt(i);
			if (!(c == 'N' || c == 'E' || c == 'S' || c == 'W' || c == 'L' || c == 'R')) {
				return false;
			}
		}
		return true;
	}

	public void setRule(int x) {
		String s = Integer.toBinaryString(x);
		rule = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			rule[i] = (s.charAt(i) == '0') ? 'L' : 'R';
		}
	}

	// returns whether the rule has been successfully set
	// if this returns false, then the rule is not valid
	public void setRule(String rule) {
		if (!isRuleValid(rule))
			return;
		this.rule = rule.toCharArray();
	}

	public void addAnt(int x, int y) {
		ants.add(new Ant(x, y));
	}

	/**
	 * Adds an ant to the center of the grid
	 */
	public void addAnt() {
		addAnt(getCenterX(), getCenterY());
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
