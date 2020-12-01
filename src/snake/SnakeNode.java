package snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SnakeNode {
	
	public static final int SIZE = 15;
	
	private int x;
	private int y;
	private int vx;
	private int vy;
	private Shape shape;
	
	
	public SnakeNode(int x, int y) {
		this(x, y, 0, 0);
	}
	
	public SnakeNode(int x, int y, int vx, int vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
		Rectangle rect = new Rectangle(SIZE, SIZE);
		rect.setStroke(Color.WHITE);
		rect.setFill(Color.BLUE);
		shape = rect;
	}
	
	public SnakeNode(int x, int y, int vx, int vy, Shape shape) {
		this(x, y, vx, vy);
		this.shape = shape;
	}
	
	public void move() {
		x += vx;
		y += vy;
	}

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

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}
	
	public Shape getShape() {
		return shape;
	}
}
