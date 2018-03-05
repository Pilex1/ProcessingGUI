package automata;

import java.util.ArrayList;
import java.util.HashSet;

import processing.core.PVector;
import util.Color;
import util.Vector2i;

import static main.Applet.P;

public class LangtonsAnt extends Grid<Integer> {

	public boolean pause = true;
	public boolean customColor = true;

	private HashSet<Ant> ants = new HashSet<>();
	private HashSet<Ant> antsToBeRemoved = new HashSet<>();
	private char[] rule;

	public Color[] colors = new Color[] { new Color(0xFFFFFF), new Color(0x96858F), new Color(0x6D7993),
			new Color(0x9099A2), new Color(0x062F4F), new Color(0x813772), new Color(0xCAEBF2), new Color(0xC09F80) };;

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
	}

	public LangtonsAnt(int width, int height) {
		this(width, height, "RL");
	}

	public void step() {
		for (Ant ant : ants) {
			ant.move();
		}
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);
		if (getCell(0, 0) == null) {
			setToDefaults();
		}
		if (pause)
			return;
		frameCount++;

		// int sum = 0;
		// for (int i = 0; i < getGridX(); i++) {
		// for (int j = 0; j < getGridY(); j++) {
		// if (getCell(i, j) != 0)
		// sum++;
		// }
		// }
		// System.out.println(sum);

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
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		super.onRender(pos, size);
		float x = pos.x + (size.x - getWidth()) / 2;
		float y = pos.y + (size.y - getHeight()) / 2;
		for (Ant ant : ants) {
			P.noStroke();
			P.fill(255, 0, 0);
			P.rect(x + ant.x * getGridSize(), y + ant.y * getGridSize(), getGridSize(), getGridSize());
		}
	}

	public void invertAnts() {
		HashSet<Ant> newAnts = new HashSet<>();
		for (Ant ant : ants) {
			newAnts.add(invertAnt(ant));
		}
		ants = newAnts;
	}

	public Ant invertAnt(Ant ant) {
		if (ant instanceof InverseAnt) {
			return new Ant((InverseAnt) ant);
		} else {
			return new InverseAnt(ant);
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

	public String getRule() {
		return new String(rule);
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
		requestGraphicalUpdate();
	}

	public void removeLastAnt() {
		if (ants.size() == 0)
			return;
		ants.remove(getAnt());
	}

	public void removeAllAnts() {
		ants.clear();
		requestGraphicalUpdate();
	}

	@Override
	protected Integer getDefault(int x, int y) {
		// if (x < 1.0 / 3 * getGridX())
		// return 1;
		return 0;
	}

	@Override
	protected Color getColor(Integer t) {
		if (customColor) {
			t = t % colors.length;
			return colors[t];
		} else {
			int f = (int) (255 * (1 - (float) t / (getStates() - 1)));
			return new Color(f, f, f);
		}
	}

	@Override
	protected void onGridSizeChange() {
	}

	public int numberOfAnts() {
		return ants.size();
	}

	// gets some ant
	public Ant getAnt() {
		if (numberOfAnts() == 0)
			return null;
		return ants.iterator().next();
	}

	public class Ant {
		int iterations;

		public int getIterations() {
			return iterations;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public Direction getDir() {
			return dir;
		}

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

		Ant(InverseAnt ant) {
			this(ant.x, ant.y, ant.dir.opposite());
			this.iterations = ant.iterations;
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

		void move() {
			normaliseCoords();

			// rotate based on color of current cell
			// changes the color of current cell
			// moves in the new direction

			Integer state = getCell(x, y);
			if (state == null) {
				// the ant has reached the edge of the grid, so destroy the ant
				antsToBeRemoved.add(this);
				return;
			}

			char move = rule[state % getStates()];
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

			normaliseCoords();
			iterations++;

		}

		protected void normaliseCoords() {
			x = (x + getGridX()) % getGridX();
			y = (y + getGridY()) % getGridY();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Ant))
				return false;
			Ant ant = (Ant) obj;
			return ant.x == x && ant.y == y;
		}

		@Override
		public int hashCode() {
			return x * 9999999 + y;
		}

	}

	public class InverseAnt extends Ant {

		InverseAnt(Ant ant) {
			super(ant.x, ant.y);
			this.dir = ant.dir.opposite();
			this.iterations = ant.iterations;
			switch (this.dir) {
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

		@Override
		void move() {
			normaliseCoords();

			setCell(x, y, (getCell(x, y) - 1 + getStates()) % getStates());

			Integer state = getCell(x, y);
			char move = rule[state];

			// can't invert absolute directions

			if (move == 'L') {
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

			normaliseCoords();
			iterations--;

		}

	}

}
