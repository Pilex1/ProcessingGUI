package util;

public class Vector2i {

	public int x, y;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y=y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector2i))
			return false;
		Vector2i i = (Vector2i) obj;
		return i.x == this.x && i.y == this.y;
	}
	
	@Override
	public Vector2i clone()  {
		return new Vector2i(x,y);
	}
	
	@Override
	public int hashCode() {
		return 9999999*x+y;
	}

}
