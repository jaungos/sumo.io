package blobio;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sprites.*;

public class GameTimer extends AnimationTimer{
	private GraphicsContext gc;
	private Stage theStage;
	private Scene theScene;
	private PlayerSumo player;
	private ArrayList<EnemySumo> enemy;
	private ArrayList<Food> food;
	private ArrayList<PowerUps> powerups;
	private long startSpawn;
	public ParallelCamera cam;

	public final static int MAX_FOOD = 50;
	public final static int MAX_ENEMIES = 10;
	public final static int MAX_POWERUPS = 2;
	public final static int OFFSET_MAXX = WelcomeStage.MAP_HEIGHT - WelcomeStage.WINDOW_HEIGHT;
	public final static int OFFSET_MINX = 0;
	public final static int OFFSET_MAXY = WelcomeStage.MAP_HEIGHT - WelcomeStage.WINDOW_HEIGHT;
	public final static int OFFSET_MINY = 0;

	GameTimer(GraphicsContext gc, Scene theScene, Stage theStage){
		this.gc = gc;
		this.theScene = theScene;
		this.theStage = theStage;
		this.player = new PlayerSumo("Ushio Hinomaru", 1200, 1200);
		this.food = new ArrayList<Food>();
		this.enemy = new ArrayList<EnemySumo>();
		this.powerups = new ArrayList <PowerUps>();
		this.startSpawn = System.nanoTime();
		this.spawnEnemies();
		this.cam = new ParallelCamera();
		this.theScene.setCamera(this.cam);
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime){
		this.gc.clearRect(0, 0, WelcomeStage.MAP_HEIGHT, WelcomeStage.MAP_HEIGHT);

		// TODO: Call the methods to move the playerSumo
		this.player.move(this);

		// TODO: Render the playerSumo
		this.player.render(this.gc);

		// TODO: Render the food, enemySumos, and powerups
		this.spawnFood();
		this.renderFood();

		this.renderEnemy();

		this.spawnPowerUps();
		this.renderPowerups();

		this.moveEnemies(currentNanoTime);
		this.eatFood();
		this.eatPowerUps();

		if (this.player.getStatus() == false){
			this.stop();
			long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
			long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);

			long timeAlive = currentSec - startSec;

			// TODO: Show the gameover scene
			GameOverStage gameover = new GameOverStage(this.player, timeAlive, this.theStage);
			this.theStage.setScene(gameover.getScene());
		}
	}

	private void renderFood(){
		for (Food f: this.food){
			f.render(this.gc);
		}
	}

	private void renderEnemy(){
		for (EnemySumo enemiess: this.enemy){
			enemiess.render(this.gc);
		}
	}

	private void renderPowerups(){
		for (PowerUps powerUps : this.powerups){
			powerUps.render(this.gc);
		}

	}

	private void moveEnemies(long currentNanoTime ) {
		Random r = new Random();
		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);

		for(int i = 0; i < this.enemy.size(); i++){
			EnemySumo enemySumo = this.enemy.get(i);
			if(enemySumo.isVisible()){
				if ((currentSec - startSec)% (r.nextInt(151)+ 5) == 0){
					if(!enemySumo.getMoved()){
						enemySumo.changeDirection();
						enemySumo.setMoved(true);
					}
				}
				else{
					enemySumo.move();
					enemySumo.setMoved(false);
				}
				enemySumo.checkCollision(this.player);

				for (EnemySumo enemies: this.enemy){
					enemySumo.checkCollision(enemies);
				}
			}
			else this.enemy.remove(i);
		}
	}

	private void eatFood(){
		for(int i = 0; i < this.food.size(); i++){
			Food foodd = this.food.get(i);
			if(foodd.isVisible()){
				foodd.checkCollision(this.player);
				for (EnemySumo enemies: this.enemy){
					foodd.checkCollision(enemies);
				}
			}
			else this.food.remove(i);
		}
	}

	private void eatPowerUps(){
		for(int i = 0; i < this.powerups.size(); i++){
			PowerUps powerUps = this.powerups.get(i);
			if(powerUps.isVisible()){
				powerUps.checkCollision(this.player);
			}
			else this.powerups.remove(i);
		}
	}

	private void spawnFood(){
		Random randomFood = new Random();

		for(int i=0; i < GameTimer.MAX_FOOD; i++){
			if(this.food.size() != GameTimer.MAX_FOOD){
				int x = randomFood.nextInt(WelcomeStage.MAP_HEIGHT+1);
				int y = randomFood.nextInt(WelcomeStage.MAP_HEIGHT+1);
				Food newFood = new Food(x, y);
				// Calls the method of setting the image of food
				newFood.generateImage();
				this.food.add(newFood);
				System.out.println("A food has spawned.");
			}
		}
	}

	private void spawnPowerUps(){
		Random randomPowerUps = new Random();

		for(int i=0; i < GameTimer.MAX_POWERUPS; i++){
			if(this.powerups.size() != GameTimer.MAX_POWERUPS){
				int c = randomPowerUps.nextInt(WelcomeStage.MAP_HEIGHT+1);
				int d = randomPowerUps.nextInt(WelcomeStage.MAP_HEIGHT+1);
				PowerUps powerUps = new PowerUps(c, d);
				// Calls the method of setting the image of food
				powerUps.generateImage();
				this.powerups.add(powerUps);
				System.out.println("A power-up has spawned.");
			}
		}
	}

//	private void movePowerUps (long currentNanoTime){
////		Random r = new Random();
//		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
//		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);
//
//		for(int i = 0; i < this.powerups.size(); i++){
//			PowerUps powerUps = this.powerups.get(i);
//			if(powerUps.isVisible() && ((currentSec-startSec)%10==0)){
//				if ((currentSec - startSec)% 10 == 0){
//					powerUps.setVisible(true);
//					powerUps.checkCollision(this.player);
//					}
////				else{
////					powerUps.vanish();
////				}
////				powerUps.checkCollision(this.player);
//
////				for (PowerUps powerupS: this.powerups){
////					powerupS.checkCollision(this.player);
////				}
//			}
//			else {
//				powerUps.setVisible(false);
//				this.powerups.remove(i);
//			}
//		}
//	}

//	private void movePowerUps(long currentNanoTime ) {
//
//		Random r = new Random();
//		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
//		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);
//
//		for(int i = 0; i < this.powerups.size(); i++){
//			PowerUps powerupS = this.powerups.get(i);
//			if(powerupS.isVisible()){
//				if ((currentSec - startSec)% 10 == 0){
//					if(!powerupS.getMoved()){
//						powerupS.changeDirection();
//						powerupS.setMoved(true);
//					}
//				}
//				else{
//					powerupS.move();
//					powerupS.setMoved(false);
//				}
//				powerupS.checkCollision(this.player);
//
//				for (EnemySumo enemies: this.enemy){
//					powerupS.checkCollision(enemies);
//				}
//			}
//			else this.enemy.remove(i);
//		}
//	}

//	private void moveEnemies(long currentNanoTime ) {
//
//		Random r = new Random();
//		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
//		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);
//
//		for(int i = 0; i < this.enemy.size(); i++){
//			EnemySumo enemySumo = this.enemy.get(i);
//			if(enemySumo.isVisible()){
//				if ((currentSec - startSec)% (r.nextInt(151)+ 5) == 0){
//					if(!enemySumo.getMoved()){
//						enemySumo.changeDirection();
//						enemySumo.setMoved(true);
//					}
//				}
//				else{
//					enemySumo.move();
//					enemySumo.setMoved(false);
//				}
//				enemySumo.checkCollision(this.player);
//
//				for (EnemySumo enemies: this.enemy){
//					enemySumo.checkCollision(enemies);
//				}
//			}
//			else this.enemy.remove(i);
//		}
////	}


	private void spawnEnemies(){
		Random randomEnemy = new Random();

		for(int i=0; i<GameTimer.MAX_ENEMIES; i++){
			int a = randomEnemy.nextInt(WelcomeStage.MAP_HEIGHT+1);
			int b = randomEnemy.nextInt(WelcomeStage.MAP_HEIGHT+1);
			EnemySumo newEnemy = new EnemySumo(a, b);
			// Calls the method of setting the image of EnemySumo
			newEnemy.generateImage();
			this.enemy.add(newEnemy);
			System.out.println("An enemy has spawned.");
		}
	}

	// Listens and handles the key press events
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                movePlayer(code);
			}

		});

		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopPlayer(code);
		            }
		        });
    }

	// Moves the player sumo depending on the key pressed
	private void movePlayer(KeyCode ke) {
		int speed = 120 / this.player.getHeight();
//		if (){
//			if(ke==KeyCode.W) this.player.setDY(-1*(Blob.SPEED_CAP / this.player.getWidth()));
//
//			if(ke==KeyCode.A) this.player.setDX(-1*(Blob.SPEED_CAP / this.player.getWidth()));
//
//			if(ke==KeyCode.S) this.player.setDY(Blob.SPEED_CAP / this.player.getWidth());
//
//			if(ke==KeyCode.D) this.player.setDX(Blob.SPEED_CAP / this.player.getWidth());
//
//		}
		if(ke==KeyCode.W) {
			this.player.setDY(-3);
//			borderBlob();
		}
		if(ke==KeyCode.A) {
			this.player.setDX(-3);
//			borderBlob();
		}
		if(ke==KeyCode.S){
			this.player.setDY(3);
//			borderBlob();
		}

		if(ke==KeyCode.D) {
			this.player.setDX(3);
//			borderBlob();
		}
//		borderBlob();
//		if(ke==KeyCode.SPACE) this.player.shoot();
		System.out.println(ke+" key pressed.");
   	}

	private void borderBlob(){
		int left = 0;
		int right = 0 ; //(int) (WelcomeStage.WINDOW_HEIGHT - player.getWidth());
		int bottom = 0; //(int) (WelcomeStage.WINDOW_HEIGHT - player.getWidth());
		int top = 0;

		if (player.getX() <= left){
			player.setX(left + 10);
		}

		if (player.getX()>= right){
			player.setX(right + 10);

		}
		if (player.getY() <= top){
			player.setY(top + 10);
		}

		if (player.getY() <= bottom){
			player.setY(bottom + 10);
		}
	}

	// Stops the player sumo's movement and also, sets the player sumo's DX and DY to 0
	private void stopPlayer(KeyCode ke){
		this.player.setDX(0);
		this.player.setDY(0);
	}

	// Sets the x axis direction of the parallel camera
	// Reference: https://gamedev.stackexchange.com/questions/44256/how-to-add-a-scrolling-camera-to-a-2d-java-game
	public void camX(){
		int camX = this.player.getX() - WelcomeStage.WINDOW_HEIGHT/2 + this.player.getHeight()/2;

		if (camX > OFFSET_MAXX){
			camX = OFFSET_MAXX;
			this.theScene.getCamera().setTranslateX(camX);
		} else if (camX < OFFSET_MINX){
			camX = OFFSET_MINX;
			this.theScene.getCamera().setTranslateX(camX);
		} else { // Case wherein the player sumo is not within the maximum/minimum X-axis offsets of the camera
			int locX = this.player.getX() - 400;
			this.theScene.getCamera().setTranslateX(this.player.getDX() + this.player.getHeight()/2 + locX );
		}
	}

	// Sets the y axis direction of the parallel camera
	// Reference: https://gamedev.stackexchange.com/questions/44256/how-to-add-a-scrolling-camera-to-a-2d-java-game
	public void camY(){
		int camY = this.player.getY() - WelcomeStage.WINDOW_HEIGHT/2 + this.player.getWidth()/2;

		if (camY > OFFSET_MAXY){
			camY = OFFSET_MAXY;
			this.theScene.getCamera().setTranslateY(camY);
		} else if (camY < OFFSET_MINY){
			camY = OFFSET_MINY;
			this.theScene.getCamera().setTranslateY(camY);
		} else { // Case wherein the player sumo is not within the maximum/minimum Y-axis offsets of the camera
			int locY = this.player.getY() - 400;
			this.theScene.getCamera().setTranslateY(this.player.getDY() + this.player.getWidth()/2 + locY );
		}
	}
}
