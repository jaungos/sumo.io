package sprites;

import java.util.Random;
import blobio.WelcomeStage;
import javafx.scene.image.Image;

public class EnemySumo extends Blob {
	public boolean alive;
	private int direction;
	private boolean moved;
	private boolean isGameOver;

	public final static Image ENEMYSUMO1_IMAGE = new Image("images/enemySumo1.png", Blob.BLOB_WIDTH, Blob.BLOB_WIDTH, false, false );
	public final static Image ENEMYSUMO2_IMAGE = new Image("images/enemySumo2.png", Blob.BLOB_WIDTH, Blob.BLOB_WIDTH, false, false );
	public final static int ENEMY_WIDTH = 40;
	public static final int ENEMY_SPEED = 5;

	public EnemySumo(int x, int y) {
		super(x, y);
		this.alive = true;
		this.direction = this.changeDirection();
		this.isGameOver = false;
	}

	public boolean isAlive(){
		return this.alive;
	}

	public boolean getMoved(){
		return this.moved;
	}

	public void setMoved(boolean moved){
		this.moved = moved;
	}

	public void setGameOver(){
		this.isGameOver = true;
	}

	public void move(){
		if (this.isGameOver == false){
			Random r = new Random();

			int randomDuration = (r.nextInt(5)+1);

			int newX = 0, newY = 0;

			if (this.direction == 0){ //if randomduration == 0, move upwards
				newX = this.x;
				newY = this.y + randomDuration;
			}
			else if (this.direction == 1 ){//if randomduration == 1, move downwards
				newX = this.x;
				newY = this.y - randomDuration;
			}
			else if (this.direction == 2){ //if randomduration == 2, move to the right
				newY = this.y;
				newX = this.x + randomDuration;
			}
			else if (this.direction == 3 ){ //if randomduration == 3, move to the left
				newY = this.y;
				newX = this.x - randomDuration;
			}

			if(newX <= WelcomeStage.MAP_HEIGHT-this.width && newX >=0 && newY<= WelcomeStage.MAP_HEIGHT-this.width && newY >=0){
	    		this.x = newX;
	    		this.y = newY;
	    	}
		}
	}

	public int changeDirection(){
		Random r = new Random ();
		this.direction = r.nextInt(4);

		return this.direction;
	}

	public void checkCollision(Blob blob){
		if(this.collidesWith(blob)){
			if(blob instanceof PlayerSumo){
				System.out.println(blob.getName() + " PLAYER collides with Enemy");
				PlayerSumo player = (PlayerSumo)blob;
				if (blob.getHeight() > this.getHeight()){
					this.vanish();
					this.increasePlayerSize(blob);
					player.addEnemyEaten(this);
					blob.loadImage(new Image("images/playerSumo.png",blob.getHeight(), blob.getWidth(), false, false ));
				} else {
					// TODO: Stop global movement
					player.updateStatus();
					this.setGameOver();
				}
			}

			else if (blob instanceof EnemySumo){
				if (blob.getHeight() > this.getHeight()){
					System.out.println(blob.getName() + " ENEMY collides with Enemy");
					this.vanish();
					this.increasePlayerSize(blob);
					this.setImage(blob);
				}
			}
		}
	}

	public void increasePlayerSize(Blob blob){
		blob.setHeight(blob.height += this.getHeight());
		blob.setWidth(blob.width += this.getWidth());
	}

	public void increaseEnemySize(Blob blob){
		this.setHeight(this.height += blob.getHeight());
		this.setWidth(this.width += blob.getWidth());
	}

	public void setImage(Blob blob){
		if (blob.getName().equals("ENEMY_SUMO_1")){
			blob.loadImage(new Image("images/enemySumo1.png",blob.getHeight(), blob.getWidth(), false, false ));
		}
		else{
			blob.loadImage(new Image("images/enemySumo2.png",blob.getHeight(), blob.getWidth(), false, false ));
		}
	}

	// Sets the image of the enemy sumo which would be displayed
	public void generateImage(){
		Random randomEnemy = new Random();

		int enemyToUse = randomEnemy.nextInt(2);
		switch(enemyToUse){
			case 0:
				this.loadImage(ENEMYSUMO1_IMAGE);
				this.name = "ENEMY_SUMO_1";
				break;
			case 1:
				this.loadImage(ENEMYSUMO2_IMAGE);
				this.name = "ENEMY_SUMO_2";
				break;
			default:
				this.loadImage(ENEMYSUMO2_IMAGE);
				this.name = "ENEMY_SUMO_2";
		}
	}
}