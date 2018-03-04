package automata;

import static main.Applet.P;

import processing.core.PVector;
import util.Color;

public class RandomCells extends Grid<Boolean> {

	public RandomCells(int width, int height) {
		super(width, height);
	}

	@Override
	protected Color getColor(Boolean t) {
		if (t) {
			return Color.Black;
		}
		return Color.White;
	}

	@Override
	protected Boolean getDefault(int x, int y) {
		return false;
	}

	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);
		if (P.frameCount % 10 == 0) {
			int x = P.R.nextInt(getGridX());
			int y = P.R.nextInt(getGridY());
			setCell(x, y, !getCell(x, y));
		}
	}

	@Override
	protected void onGridSizeChange() {
		// TODO Auto-generated method stub

	}

}
