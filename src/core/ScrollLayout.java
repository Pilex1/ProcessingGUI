package core;

public class ScrollLayout {
	
	protected boolean scrollHorizontal, scrollVertical;
	
	private GraphicsComponent innerComponent;
	
	public ScrollLayout(GraphicsComponent innerComponent, boolean scrollHorizontal, boolean scrollVertical) {
		this.innerComponent=innerComponent;
		this.scrollHorizontal = scrollHorizontal;
		this.scrollVertical = scrollVertical;
	}

}
