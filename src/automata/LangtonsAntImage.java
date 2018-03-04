package automata;

import processing.core.PImage;
import util.Color;

public class LangtonsAntImage extends LangtonsAnt {

	private PImage img;
	private int[][] brightness;

	public LangtonsAntImage(int width, int height, PImage img) {
		super(width, height);
		this.img = img;
		String rule = "";
		for (int i = 0; i < 128; i++) {
			rule += "LR";
		}
		setRule(rule);
		setImage();
	}

	private void setImage() {
		int x = getGridX();
		int y = getGridY();
		img.resize(x, y);
		brightness = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Color c = new Color(img.get(i, j));
				brightness[i][j] = c.getBrightness();
			}
		}
	}

	@Override
	protected void onGridSizeChange() {
		setImage();
	}

	@Override
	protected Integer getDefault(int x, int y) {
		return brightness[x][y];
	}

}
