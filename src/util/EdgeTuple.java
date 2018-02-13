package util;

public class EdgeTuple {

	public float top, right, bottom, left;

	public EdgeTuple(float top, float right, float bottom, float left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
	public EdgeTuple(float x) {
		this(x,x,x,x);
	}

	public void setAll(float x) {
		top=right=bottom=left=x;
	}
	
}
