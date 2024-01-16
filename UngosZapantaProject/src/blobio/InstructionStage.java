package blobio;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InstructionStage {
	private Scene scene;
	private Stage stage;
	private ImageView instructionsbg;

	public void setStage(Stage stage){
		this.stage = stage;
		Image instrucbg = new Image("images/instructions.gif");
		this.instructionsbg = new ImageView();

		this.instructionsbg.setImage(instrucbg);
		this.instructionsbg.setFitHeight(WelcomeStage.WINDOW_HEIGHT);
		this.instructionsbg.setPreserveRatio(true);;

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
				stage.close();
			}
		});

		backbtn.setPrefSize(160, 30);
		backbtn.setBackground(null);

		Group btn = new Group();
		btn.setLayoutX(300);
		btn.setLayoutY(650);
		btn.getChildren().add(backbtn);

		Group root = new Group();
		root.getChildren().addAll(this.instructionsbg, btn);

		this.scene = new Scene(root, 800, 800);

		// Sets the logo of the application
		// Reference: https://youtu.be/UZKKaI8OnjY
		Image icon = new Image("images/game_icon.png");
		this.stage.getIcons().add(icon);

		this.stage.setTitle("Instructions");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	Scene getScene() {
		return this.scene;
	}

}