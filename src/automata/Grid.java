package automata;

import java.awt.Color;
import java.util.HashMap;

import core.Canvas;

import static main.Applet.P;

import processing.core.PVector;
import util.Vector2i;

public abstract class Grid<T> extends Canvas {

	public Color gridColor = Color.BLACK;
	public int gridLineSize = 0;
	private float gridSize = 2;
	public boolean square = false;
	public boolean gridWrap = false;

	private T[][] grid;

	// in each update cycle, add an element to this to indicate that the cell
	// has been changed and must be re-rendered in the next render cycle
	// cleared after each render is complete
	private HashMap<Vector2i, T> toRender = new HashMap<>();

	// set to true to indicate that on the next render call, we clear the frame
	private boolean clearNext = false;

	public Grid(int width, int height) {
		super(width, height);
		initialiseGrid();
	}
	
	private void initialiseGrid() {
		int x = (int) (getWidth() / gridSize);
		if (x % 2 == 0)
			x--;
		int y = (int) (getHeight() / gridSize);
		if (y % 2 == 0)
			y--;
		if (square) {
			x = y = Math.min(x, y);
		}
		grid = (T[][]) new Object[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				grid[i][j] = getDefault();
			}
		}
	}

	public float getGridSize() {
		return gridSize;
	}

	public void setGridSize(float gridSize) {
		if (gridSize == this.gridSize)
			return;
		this.gridSize = gridSize;
		initialiseGrid();
		requestPositionalUpdate();
	}

	public void clear() {
		clearNext = true;
		requestGraphicalUpdate();
	}

	public int getCenterX() {
		return grid.length / 2;
	}

	public int getCenterY() {
		return grid[0].length / 2;
	}

	public int getGridX() {
		return grid.length;
	}

	public int getGridY() {
		return grid[0].length;
	}

	protected abstract T getDefault();

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		if (clearNext) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					grid[i][j] = getDefault();
				}
			}
		}
	}

	private void drawToGraphics(int x, int y, T t) {
		buffer.stroke(gridColor.getRGB());
		if (gridLineSize == 0) {
			buffer.noStroke();
		} else {
			buffer.strokeWeight(gridLineSize);
		}
		buffer.fill(getColor(t).getRGB());
		buffer.rect(x * gridSize, y * gridSize, gridSize, gridSize);
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		if (clearNext) {
			buffer.beginDraw();
			for (int i =0;i < grid.length;i++) {
				for (int j =0;j<grid[0].length;j++) {
					drawToGraphics(i,j,getDefault());
				}
			}
			
			renderBuffer(pos, size);
			clearNext = false;
			buffer.endDraw();
			return;
		}
		float x = pos.x + (size.x - buffer.width) / 2;
		float y = pos.x + (size.y - buffer.height) / 2;
		for (Vector2i i : toRender.keySet()) {
			T t = toRender.get(i);
			// drawToGraphics(P.getGraphics(), i.x, i.y, t);
			// does not work, i dont think P.getGraphics() is a good idea

			P.stroke(gridColor.getRGB());
			if (gridLineSize == 0) {
				P.noStroke();
			} else {
				P.strokeWeight(gridLineSize);
			}
			P.fill(getColor(t).getRGB());
			P.rect(x + i.x * gridSize, y + i.y * gridSize, gridSize, gridSize);

		}
		toRender.clear();

	}

	public T getCell(int x, int y) {
		Vector2i v = getNormalisedCoords(x, y);
		if (v.x < 0 || v.x >= grid.length || v.y < 0 || v.y >= grid[0].length)
			return null;
		return grid[v.x][v.y];
	}

	public T getCell(Vector2i v) {
		return getCell(v.x, v.y);
	}

	protected Vector2i getNormalisedCoords(Vector2i v) {
		return getNormalisedCoords(v.x, v.y);
	}

	// e.g. [-1, -1] -> [grid.length-1, grid[0].length-1]
	protected Vector2i getNormalisedCoords(int x, int y) {
		if (gridWrap) {
			while (x >= grid.length)
				x -= grid.length;
			while (x < 0)
				x += grid.length;
			while (y >= grid[0].length)
				y -= grid.length;
			while (y < 0)
				y += grid.length;
		}
		return new Vector2i(x, y);
	}

	protected boolean setCell(int x, int y, T t) {
		Vector2i v = getNormalisedCoords(x, y);
		T cell = getCell(v);
		if (t == null)
			return false;
		if (!t.equals(cell)) {
			toRender.put(v, t);
			grid[v.x][v.y] = t;
			drawToGraphics(v.x, v.y, t);
			requestGraphicalUpdate();
		}
		return true;
	}

	protected boolean setCell(Vector2i v, T t) {
		return setCell(v.x, v.y, t);
	}

	protected abstract Color getColor(T t);
}
