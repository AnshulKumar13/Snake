package snake;

import java.util.Properties;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameWindow extends Application {
	
	public static final int WIDTH;
	public static final int HEIGHT;
	public static final double SNAKE_SPEED;
	
	private static Random rand = new Random();
	
	private int foodX;
	private int foodY;
	private boolean paused = false;
	private Properties gameProps;
	private int highScore;
	
	static {
		Properties props = PropsManager.getProperties();
		WIDTH = Integer.parseInt(props.getProperty("width"));
		HEIGHT = Integer.parseInt(props.getProperty("height"));
		SNAKE_SPEED = Integer.parseInt(props.getProperty("speed"));
	}
	
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	@Override
	public void start(Stage primaryStage) {
		gameProps = PropsManager.getProperties();
		highScore = Integer.parseInt(gameProps.getProperty("highScore"));
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 2 + WIDTH * (SnakeNode.SIZE + 1), 102 + HEIGHT * (SnakeNode.SIZE + 1));
		Grid grid = new Grid(WIDTH, HEIGHT);
		root.setCenter(grid.getGridPane());
		
		Canvas scoreCanvas = new Canvas(WIDTH * (SnakeNode.SIZE + 1), 100);
		root.setTop(scoreCanvas);
		
		updateScore(0, scoreCanvas);
		
		SnakeBody snake = initSnake(grid);
		
		generateFood(grid, snake);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				SnakeNode head = snake.getHead();
				switch(event.getCode()) {
				case UP:
				case W:
					turn(head, 0, -1);
					break;
				case DOWN:
				case S:
					turn(head, 0, 1);
					break;
				case LEFT:
				case A:
					turn(head, -1, 0);
					break;
				case RIGHT:
				case D:
					turn(head, 1, 0);
					break;
				case G:
					snake.addNode();
				case P:
					paused = !paused;
					break;
				case Q:
					Platform.exit();
					break;
				case R:
					paused = false;
					snake.cleanSnake(grid);
					snake.reset();
					snake.addNode();
					snake.displaySnake(grid);
					clearFood(grid);
					generateFood(grid, snake);
					updateScore(0, scoreCanvas);
					break;
				default:
					break;
				}
				
			}
			
		});
		
		AnimationTimer timer = new AnimationTimer() {
			
			private long minTime = (long)(1000000000 / SNAKE_SPEED);
			private long lastUpdate = System.nanoTime();
			
			@Override
			public void handle(long now) {
				long timePassed = now - lastUpdate;
				
				if(timePassed >= minTime && !paused && snake.isAlive()) {
					snake.cleanSnake(grid);
					snake.move();
					snake.checkForOverlap();
					
					if(checkFoodCollision(snake)) {
						snake.addNode();
						generateFood(grid, snake);
					}
					
					snake.displaySnake(grid);
					int score = snake.getSize() - 2;
					updateScore(score, scoreCanvas);
					
					if(score > highScore) {
						highScore = score;
						gameProps.put("highScore", score + "");
						PropsManager.storeProperty(gameProps);
					}
					
					lastUpdate = now;
				}
			}
			
		};
		
		timer.start();
	}
	
	private void generateFood(Grid grid, SnakeBody snake) {
		int x = getRandomInt(WIDTH - 10) + 5;
		int y = getRandomInt(HEIGHT - 10) + 5;
		
		while(snake.isOnSnake(x, y)) {
			x = getRandomInt(WIDTH - 10) + 5;
			y = getRandomInt(HEIGHT - 10) + 5;
		}
		
		Circle food = new Circle();
		food.setFill(Color.BLACK);
		food.setRadius(SnakeNode.SIZE / 2);
		grid.add(food, x, y);
		foodX = x;
		foodY = y;
	}
	
	private void turn(SnakeNode head, int vX, int vY) {
		if(head.getVx() == -vX && head.getVy() == -vY) {
			return;
		}
		
		head.setVx(vX);
		head.setVy(vY);
	}
	
	private void clearFood(Grid grid) {
		grid.clear(foodX, foodY);
	}
	
	private boolean checkFoodCollision(SnakeBody snake) {
		SnakeNode head = snake.getHead();
		
		return head.getX() == foodX && head.getY() == foodY;
	}
	
	private void updateScore(int score, Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.CORNFLOWERBLUE);
		gc.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, WIDTH / 2));
		gc.strokeText("SCORE: " + score + " HIGH SCORE: " + highScore, 50, 50);
	}
	
	private SnakeBody initSnake(Grid grid) {
		SnakeBody snake = new SnakeBody();
		snake.addNode();
		snake.displaySnake(grid);
		return snake;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
