package snake;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Item {
	public static final int SIZE = 10;
	
	private int x;
	private int y;
	private Shape shape;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Shape getShape() {
		return shape;
	}
}
