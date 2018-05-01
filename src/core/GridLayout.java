package core;

import processing.core.PVector;
import util.MathUtil;
import util.PosSizeTuple;

public abstract class GridLayout extends Layout {

	public boolean keepGridSizesEqual = false;
	private PosSizeTuple[][] posSizeCache = null;

	public abstract GraphicsComponent getComponent(int x, int y);

	// adds the specified component into position [x,y]
	// returns true if added successfully, false otherwise (if there is already
	// another component at where you want to add the new component, or if the
	// specified position is not within range)
	public abstract boolean addComponent(GraphicsComponent g, int x, int y);

	public abstract int getSizeX();

	public abstract int getSizeY();

	// recalculate min and max sizes
	// this should be called whenever a component is added/removed
	// however, if min and max sizes have been manually set, we shouldn't change
	// them
	protected void recalculateBounds() {
		setMinSize(getMinWidth() == 0 ? getCumulativeMinWidth() : getMinWidth(),
				getMinHeight() == 0 ? getCumulativeMinHeight() : getMinHeight());
		setMaxSize(getMaxWidth() == Float.MAX_VALUE ? getCumulativeMaxWidth() : getMaxWidth(),
				getMaxHeight() == Float.MAX_VALUE ? getCumulativeMaxHeight() : getMaxHeight());
	}

	protected float getMaxWidthInCol(int col) {
		float width = Float.MAX_VALUE;
		for (int row = 0; row < getSizeY(); row++) {
			GraphicsComponent g = getComponent(col, row);
			if (g != null) {
				width = Math.min(width, g.getMaxWidth());
			}
		}
		return width;
	}

	protected float getMaxHeightInRow(int row) {
		float height = Float.MAX_VALUE;
		for (int col = 0; col < getSizeX(); col++) {
			GraphicsComponent g = getComponent(col, row);
			if (g != null) {
				height = Math.min(height, g.getMaxHeight());
			}
		}
		return height;
	}

	protected float getMinWidthInCol(int col) {
		float width = 0;
		for (int row = 0; row < getSizeY(); row++) {
			GraphicsComponent g = getComponent(col, row);
			if (g != null) {
				width = Math.max(width, g.getMinWidth());
			}
		}
		return width;
	}

	protected float getMinHeightInRow(int row) {
		float height = 0;
		for (int col = 0; col < getSizeX(); col++) {
			GraphicsComponent g = getComponent(col, row);
			if (g != null) {
				height = Math.max(height, g.getMinHeight());
			}
		}
		return height;
	}

	protected float[] getMinWidthsInCols() {
		float[] widths = new float[getSizeX()];
		float max = 0;
		for (int col = 0; col < getSizeX(); col++) {
			float w = getMinWidthInCol(col);
			max = Math.max(max, w);
			if (!keepGridSizesEqual) {
				widths[col] = w;
			}
		}
		if (keepGridSizesEqual) {
			for (int col = 0; col < getSizeX(); col++) {
				widths[col] = max;
			}
		}
		return widths;
	}

	protected float[] getMaxWidthsInCols() {
		float[] widths = new float[getSizeX()];
		float min = Float.MAX_VALUE;
		for (int col = 0; col < getSizeX(); col++) {
			float w = getMaxWidthInCol(col);
			min = Math.min(min, w);
			if (!keepGridSizesEqual) {
				widths[col] = w;
			}
		}
		if (keepGridSizesEqual) {
			for (int col = 0; col < getSizeX(); col++) {
				widths[col] = min;
			}
		}
		return widths;
	}

	protected float[] getMinHeightsInRows() {
		float[] heights = new float[getSizeY()];
		float min = 0;
		for (int row = 0; row < getSizeY(); row++) {
			float h = getMinHeightInRow(row);
			min = Math.max(min, h);
			if (!keepGridSizesEqual) {
				heights[row] = h;
			}
		}
		if (keepGridSizesEqual) {
			for (int row = 0; row < getSizeY(); row++) {
				heights[row] = min;
			}
		}
		return heights;
	}

	protected float[] getMaxHeightsInRows() {
		float[] heights = new float[getSizeY()];
		float min = Float.MAX_VALUE;
		for (int row = 0; row < getSizeY(); row++) {
			float h = getMaxHeightInRow(row);
			min = Math.min(min, h);
			if (!keepGridSizesEqual) {
				heights[row] = h;
			}
		}
		if (keepGridSizesEqual) {
			for (int row = 0; row < getSizeY(); row++) {
				heights[row] = min;
			}
		}
		return heights;
	}

	private float getCumulativeMinWidth() {
		float total = 0;
		float[] widths = getMinWidthsInCols();
		for (int i = 0; i < widths.length; i++) {
			total += widths[i];
		}
		return total;
	}

	private float getCumulativeMinHeight() {
		float total = 0;
		float[] heights = getMinHeightsInRows();
		for (int i = 0; i < heights.length; i++) {
			total += heights[i];
		}
		return total;
	}

	private float getCumulativeMaxWidth() {
		float total = 0;
		float[] widths = getMaxWidthsInCols();
		for (int i = 0; i < widths.length; i++) {
			total += widths[i];
		}
		return total;
	}

	private float getCumulativeMaxHeight() {
		float total = 0;
		float[] heights = getMaxHeightsInRows();
		for (int i = 0; i < heights.length; i++) {
			total += heights[i];
		}
		return total;
	}

	@Override
	protected void recalculatePositions(PVector pos, PVector size) {
		posSizeCache = new PosSizeTuple[getSizeX()][getSizeY()];
		PVector[][] sizes = new PVector[getSizeX()][getSizeY()];

		float[] minWidths = getMinWidthsInCols();
		float[] minHeights = getMinHeightsInRows();
		float[] maxWidths = getMaxWidthsInCols();
		float[] maxHeights = getMaxHeightsInRows();

		// amount of space available for rendering
		// i.e. size - padding
		PVector renderSize = new PVector(size.x - padding.left - padding.right, size.y - padding.top - padding.bottom);

		PVector cumulativeMinSize = new PVector(getCumulativeMinWidth(), getCumulativeMinHeight());
		// extra space we have left over if we render everything using their minimum
		// sizes
		PVector extraSpace = PVector.sub(renderSize, cumulativeMinSize);

		// we first start off by assigning each component its minimum size with respect
		// to its row and column
		// i.e. for a given component, its width is equal to the maximum minWidth in
		// that column
		// and its height is equal to the maximum minHeight in that row
		for (int i = 0; i < getSizeX(); i++) {
			float width = minWidths[i];
			for (int j = 0; j < getSizeY(); j++) {
				float height = minHeights[j];
				sizes[i][j] = new PVector(width, height);
			}
		}

		// now we distribute remaining space in extraSpace as follows
		// distribute space evenly up to the lowest maxWidth
		// then distribute remaining space evenly up to the next lowest maxWidth
		// (without distributing to the ones filled up from before as those are already
		// at their maxWidth)
		// distribute again, etc.

		// these are for storing if a given row/column has been filled up already
		boolean[] ignoreX = new boolean[getSizeX()];
		boolean[] ignoreY = new boolean[getSizeY()];
		while (extraSpace.x > 0) {
			float maxX = MathUtil.min(maxWidths, ignoreX);

			// number of components left to fill
			long n = MathUtil.countFalse(ignoreX);
			if (n == 0)
				break;

			// if the space we need to fill exceeds maxX
			// then we restrict it to maxX
			float fillX = extraSpace.x / n;
			if (fillX >= maxX) {
				extraSpace.x -= maxX * n;
				fillX = maxX;
			} else {
				// we are done
				extraSpace.x = 0;
			}
			for (int i = 0; i < getSizeX(); i++) {
				if (ignoreX[i])
					continue;
				for (int j = 0; j < getSizeY(); j++) {
					sizes[i][j].x += fillX;
				}
			}

			for (int i = 0; i < ignoreX.length; i++) {
				if (maxWidths[i] == maxX) {
					ignoreX[i] = true;
				}
			}
		}

		while (extraSpace.y > 0) {
			float maxY = MathUtil.min(maxHeights, ignoreY);

			// number of components left to fill
			long n = MathUtil.countFalse(ignoreY);
			if (n == 0)
				break;

			// if the space we need to fill exceeds maxY
			// then we restrict it to maxY
			float fillY = extraSpace.y / n;
			if (fillY >= maxY) {
				extraSpace.y -= maxY * n;
				fillY = maxY;
			} else {
				// we are done
				extraSpace.y = 0;
			}

			for (int j = 0; j < getSizeY(); j++) {
				if (ignoreY[j])
					continue;
				for (int i = 0; i < getSizeX(); i++) {
					sizes[i][j].y += fillY;
				}
			}

			for (int i = 0; i < ignoreY.length; i++) {
				if (maxHeights[i] == maxY) {
					ignoreY[i] = true;
				}
			}
		}

		float x = pos.x;
		for (int i = 0; i < getSizeX(); i++) {
			float y = pos.y;
			for (int j = 0; j < getSizeY(); j++) {
				posSizeCache[i][j] = new PosSizeTuple(new PVector(x, y), sizes[i][j]);
				y += sizes[0][j].y;
			}
			x += sizes[i][0].x;
		}
	}

	@Override
	protected void onRender(PVector pos, PVector size) {
		for (int i = 0; i < posSizeCache.length; i++) {
			for (int j = 0; j < posSizeCache[i].length; j++) {
				GraphicsComponent g = getComponent(i, j);
				if (g != null) {
					g.render(posSizeCache[i][j].pos, posSizeCache[i][j].size);
				}
				// System.out.print(i + "," + j + " ");
			}
		}
		// System.out.println();
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		for (int i = 0; i < posSizeCache.length; i++) {
			for (int j = 0; j < posSizeCache[i].length; j++) {
				GraphicsComponent g = getComponent(i, j);
				if (g != null) {
					g.update(posSizeCache[i][j].pos, posSizeCache[i][j].size);
				}
			}
		}
	}

}
