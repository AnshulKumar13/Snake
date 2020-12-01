package snake;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SnakeBody {
	private List<SnakeNode> snake;
	private int size;
	private boolean alive;
	
	
	public SnakeBody() {
		reset();
	}
	
	public void reset() {
		snake = new ArrayList<SnakeNode>();
		
		Rectangle rect = new Rectangle(SnakeNode.SIZE, SnakeNode.SIZE);
		rect.setStroke(Color.WHITE);
		rect.setFill(Color.DEEPPINK);
		
		SnakeNode head = new SnakeNode(GameWindow.WIDTH / 2, GameWindow.HEIGHT / 2, -1, 0, rect);
		snake.add(head);
		size = 1;
		alive = true;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public SnakeNode getHead() {
		return snake.get(0);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void checkForOverlap() {
		if(snake.size() == 0)
			return;
		
		/*
		 * Iterator<SnakeNode> itr = snake.iterator(); SnakeNode head = itr.next();
		 * 
		 * while(itr.hasNext()) { SnakeNode node = itr.next();
		 * 
		 * if(node.getX() == head.getX() && node.getY() == head.getY()) { alive = false;
		 * } }
		 */
		
		for(int i = 0; i < snake.size(); i++) {
			for(int j = 0; j < snake.size(); j++) {
				if(i != j) {
					if(snake.get(i).getX() == snake.get(j).getX() && snake.get(i).getY() == snake.get(j).getY()) {
						alive = false;
					}
				}
			}
		}
	}
	
	public boolean isOnSnake(int x, int y) {
		for(SnakeNode node : snake) {
			if(node.getX() == x && node.getY() == y) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addNode() {
		SnakeNode head = getHead();
		int x = head.getX();
		int y = head.getY();
		head.move();
		
		SnakeNode newNode = new SnakeNode(x, y, head.getVx(), head.getVy());
		snake.add(1, newNode);
		size++;
	}
	
	public void move() {
		SnakeNode head = getHead();
		head.move();
		
		if(head.getX() <= 0 || head.getX() >= GameWindow.WIDTH-1 || head.getY() <= 0 || head.getY() >= GameWindow.HEIGHT-1) {
			alive = false;
			return;
		}
		
		int oldVx = head.getVx();
		int oldVy = head.getVy();
		
		for(int i = 1; i < snake.size(); i++) {
			SnakeNode node = snake.get(i);
			int oldX = node.getX();
			int oldY = node.getY();
			node.move();
			node.setVx(oldVx);
			node.setVy(oldVy);
			oldVx = node.getX() - oldX;
			oldVy = node.getY() - oldY;
		}
	}
	
	public void displaySnake(Grid grid) {
		boolean headPainted = false;
		
		int pattern = 0;
		for(SnakeNode node : snake) {
			Shape shape = node.getShape();
			
			if(pattern == 0) {
				shape.setFill(Color.BLUE);
			}else if(pattern == 1) {
				shape.setFill(Color.DARKVIOLET);
			}else {
				shape.setFill(Color.HOTPINK);
			}
			
			pattern = (pattern + 1) % 3;
			
			grid.add(shape, node.getX(), node.getY());
		}
	}
	
	public void cleanSnake(Grid grid) {
		for(SnakeNode node : snake) {
			grid.reset(node.getX(), node.getY());
		}
	}
	
}
