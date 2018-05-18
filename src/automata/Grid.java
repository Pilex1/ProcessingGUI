package automata;

import core.Canvas;

import processing.core.PVector;
import util.Color;
import util.Vector2i;

public abstract class Grid<T> extends Canvas {

	private float gridSize = 2;
	public boolean square = false;
	public boolean gridWrap = false;

	private T[][] grid;

	public Grid(int width, int height) {
		super(width, height);
		initialiseToNull();
	}

	protected void initialiseToNull() {
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
				setCell(i, j, null);
			}
		}
		setBufferSize((int) (x * gridSize), (int) (y * gridSize));
		requestGraphicalUpdate();
	}

	protected void setToDefaults() {
		for (int i = 0; i < getGridX(); i++) {
			for (int j = 0; j < getGridY(); j++) {
				setCell(i, j, getDefault(i, j));
			}
		}
		requestGraphicalUpdate();
	}

	public float getGridSize() {
		return gridSize;
	}

	protected abstract void onGridSizeChange();

	public void setGridSize(float gridSize) {
		if (gridSize == this.gridSize)
			return;
		this.gridSize = gridSize;
		initialiseToNull();
		onGridSizeChange();
		setToDefaults();
	}

	public void clear() {
		initialiseToNull();
		setToDefaults();
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

	protected abstract T getDefault(int x, int y);

	@Override
	protected void onUpdate(PVector pos, PVector size) {
	}

	private void drawToGraphics(int x, int y, T t) {
		noStroke();
		fill(getColor(t));
		rect(x * gridSize, y * gridSize, gridSize, gridSize);
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		renderBuffer(pos, size);
	}

	public T getCell(int x, int y) {
		return grid[x][y];
	}

	public T getCell(Vector2i v) {
		return getCell(v.x, v.y);
	}

	protected void setCell(int x, int y, T t) {
		if (t == null)
			return;
		grid[x][y] = t;
		drawToGraphics(x, y, t);
		requestGraphicalUpdate();
	}

	protected void setCell(Vector2i v, T t) {
		setCell(v.x, v.y, t);
	}

	protected abstract Color getColor(T t);
}
