package automata;

import java.awt.Color;
import java.util.HashMap;

import core.Canvas;

import static main.Applet.P;

import processing.core.PGraphics;
import processing.core.PVector;
import util.Vector2i;

public abstract class Grid<T> extends Canvas {

	public Color gridColor = Color.BLACK;
	private float gridSize = 2;
	public boolean square = false;
	public boolean gridWrap = false;

	private T[][] grid;

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
		requestGraphicalUpdate();
	}

	public float getGridSize() {
		return gridSize;
	}

	public void setGridSize(float gridSize) {
		if (gridSize == this.gridSize)
			return;
		this.gridSize = gridSize;
		initialiseGrid();
	}

	public void clear() {
		buffer.beginDraw();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				setCell(i, j, getDefault());
			}
		}
		buffer.endDraw();
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
	}

	private void drawToGraphics(int x, int y, T t) {
		buffer.noStroke();
		buffer.fill(getColor(t).getRGB());
		buffer.rect(x * gridSize, y * gridSize, gridSize, gridSize);
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		renderBuffer(pos, size);
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
