package blobio;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sprites.PlayerSumo;

public class GameOverStage {
	private Scene scene;

	public GameOverStage(PlayerSumo sumo, long timeAlive, Stage stage){
		Canvas canvas = new Canvas(WelcomeStage.WINDOW_HEIGHT, WelcomeStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		StackPane stackPane = new StackPane();

		// TODO: Import custom font
		Font font = Font.loadFont("file:resources/font/prstartk.ttf", 40);
		gc.setFont(font);
		// TODO: Text for food eaten
		gc.fillText("" + sumo.getScore(), 220, 379);
		// TODO: Text for enemy sumo eaten
		gc.fillText("" + sumo.getBlobsEaten(), 545, 379);
		// TODO: Text for current size
		gc.fillText("" + sumo.getHeight(), 370, 500);
		// TODO: Text for time alive
		gc.fillText("" + timeAlive, 370, 623);

		// TODO: Add background
		Image bg = new Image("images/game_over.gif");
		ImageView endGame = new ImageView();

		endGame.setImage(bg);
		endGame.setFitHeight(WelcomeStage.WINDOW_HEIGHT);
		endGame.setPreserveRatio(true);

		stackPane.getChildren().add(endGame);

		// TODO: Create a return to menu button
		// Reference: https://jenkov.com/tutorials/javafx/button.html
		Image returnButton = new Image("images/return_button.png");
		ImageView retButton = new ImageView(returnButton);
		retButton.setFitHeight(68);
		retButton.setPreserveRatio(true);
		Button backbtn = new Button();
		backbtn.setGraphic(retButton);

		backbtn.setOnMouseEntered(event -> {
			backbtn.setStyle("-fx-cursor: hand;");
		});

		// Reference: https://docs.oracle.com/javafx/2/ui_controls/button.htm
		DropShadow shadow = new DropShadow();
		shadow.setRadius(15);

		// Adds a shadow effect when the mouse hovers on the button
		backbtn.addEventHandler(MouseEvent.MOUSE_ENTERED,
		    new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) {
		            backbtn.setEffect(shadow);
		        }
		});

		// Removes the shadow effect when the mouse leaves the button area
		backbtn.addEventHandler(MouseEvent.MOUSE_EXITED,
		    new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) {
		            backbtn.setEffect(null);
		        }
		});

		backbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				// TODO: Return to main menu
				WelcomeStage welcome = new WelcomeStage();
				Stage welcomeHome = new Stage();
				stage.hide();
				welcome.setStage(welcomeHome);
			}
		});

		backbtn.setPrefSize(160, 30);
		backbtn.setBackground(null);

		Group btn = new Group();
		btn.setLayoutX(300);
		btn.setLayoutY(670);
		btn.getChildren().add(backbtn);

		Group root = new Group();
		root.getChildren().addAll(stackPane, canvas, btn);

		this.scene = new Scene(root, WelcomeStage.WINDOW_HEIGHT, WelcomeStage.WINDOW_HEIGHT);
	}

	public Scene getScene(){
		return this.scene;
	}
}