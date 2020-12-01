package snake;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {
	private GridPane gridPane;
	private StackPane[][] gridArr;
	private Color gridColor;
	
	public Grid(int width, int height) {
		this(width, height, Color.WHITE);
	}
	
	public Grid(int width, int height, Color color) {
		gridPane = new GridPane();
		gridArr = new StackPane[width][height];
		gridColor = color;
		init();
	}
	
	public GridPane getGridPane() {
		return gridPane;
	}
	
	public StackPane get(int x, int y) {
		return gridArr[y][x];
	}
	
	public void addToScene(Scene scene) {
		scene.setRoot(gridPane);
	}
	
	public void add(Node node, int x, int y) {
		StackPane pane = get(x, y);
		pane.getChildren().clear();
		pane.getChildren().add(node);
	}
	
	public void clear(int x, int y) {
		get(x, y).getChildren().clear();
	}
	
	public void reset(int x, int y) {
		StackPane pane = get(x, y);
		pane.getChildren().clear();
		pane.getChildren().add(createRectangle());
	}
	
	private void init() {
		for(int i = 0; i < gridArr.length; i++) {
			for(int j = 0; j < gridArr[i].length; j++) {
				StackPane pane = new StackPane();
				Rectangle rect = createRectangle();
				pane.getChildren().add(rect);
				gridPane.add(pane, j, i);
				gridArr[i][j] = pane;
			}
		}
		
		gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	private Rectangle createRectangle() {
		Rectangle rect = new Rectangle(SnakeNode.SIZE, SnakeNode.SIZE);
		rect.setFill(null);
		rect.setStroke(gridColor);
		return rect;
	}
}
