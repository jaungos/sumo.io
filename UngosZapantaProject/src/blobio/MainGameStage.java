package blobio;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainGameStage {
	private Stage stage;
	private Scene scene;
	private Group root;
	private StackPane stack;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gameTimer;

	MainGameStage(Stage stage){
		this.stage = stage;
		this.root = new Group();
		this.stack = new StackPane();

		Image bg = new Image("images/main_game.png", WelcomeStage.MAP_HEIGHT, WelcomeStage.MAP_HEIGHT, false, false);
		BackgroundImage backImg = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(WelcomeStage.MAP_HEIGHT, WelcomeStage.MAP_HEIGHT, true, true, true, true));
		this.stack.setBackground(new Background(backImg));

		this.scene = new Scene(this.root, WelcomeStage.WINDOW_HEIGHT, WelcomeStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(WelcomeStage.MAP_HEIGHT, WelcomeStage.MAP_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		// Instantiate an Animation Timer
		this.gameTimer = new GameTimer(this.gc, this.scene, this.stage);
	}

	void setScene(){
		// TODO: Add background to the canvas
		gc.drawImage(new Image("images/main_game.png", WelcomeStage.MAP_HEIGHT, WelcomeStage.MAP_HEIGHT, false, false), 770, 770);

		Image status = new Image("images/status_bar.png", WelcomeStage.WINDOW_HEIGHT, WelcomeStage.WINDOW_HEIGHT, true, false);
		Canvas overlay = new Canvas(WelcomeStage.WINDOW_HEIGHT, WelcomeStage.WINDOW_HEIGHT);
		GraphicsContext gc_status = overlay.getGraphicsContext2D();
		gc_status.drawImage(status, 0, 0);

		this.stack.getChildren().addAll(canvas, overlay);
		this.root.getChildren().add(this.stack);
	}

	Scene getScene() {
		return this.scene;
	}

	void setGame(){
		this.gameTimer.start();
	}
}