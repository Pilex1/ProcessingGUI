package automata;

import java.awt.Color;
import static main.Applet.P;

import processing.core.PVector;

public class RandomCells extends Grid<Boolean> {

	public RandomCells(int width, int height) {
		super(width, height);
	}

	@Override
	protected Color getColor(Boolean t) {
		if (t) {
			return Color.BLACK;
		}
		return Color.WHITE;
	}
	
	@Override
	protected Boolean getDefault() {
		return false;
	}
	
	@Override
	protected void onUpdate(PVector pos, PVector size) {
		super.onUpdate(pos, size);
		if (P.frameCount%10==0) {
			int x = P.R.nextInt(getGridX());
			int y = P.R.nextInt(getGridY());
			setCell(x, y, !getCell(x,y));
		}
	}

}
