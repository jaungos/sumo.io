package sprites;

import java.util.ArrayList;

import blobio.GameTimer;
import blobio.WelcomeStage;
import javafx.scene.image.Image;


public class PlayerSumo extends Blob{
	private int score;
	private ArrayList<EnemySumo> blobsEaten;
	private boolean isAlive;

	public final static Image PLAYER_IMAGE = new Image("images/playerSumo.png", Blob.BLOB_WIDTH, Blob.BLOB_WIDTH, false, false);

	public PlayerSumo(String name, int x, int y){
		super(x, y);
		this.name = name;
		this.score = 0;
		this.blobsEaten = new ArrayList<EnemySumo>();
		this.isAlive = true;
		this.loadImage(PLAYER_IMAGE);
	}


	public void move(GameTimer gameTimer) {
//		if(this.x+this.dx <= WelcomeStage.MAP_HEIGHT-this.width && this.x+this.dx >=0 && this.y+this.dy <= WelcomeStage.MAP_HEIGHT-this.width && this.y+this.dy >=0){
//    		this.x += this.dx;
//    		this.y += this.dy;
//    	}

		if(this.x <= WelcomeStage.MAP_HEIGHT-this.width && this.x >=0 && this.y<= WelcomeStage.MAP_HEIGHT-this.width && this.y >=0){
    		this.x += this.dx;
    		this.y += this.dy;
    	}
		else if (this.x <= 0 && this.y >=0){
			this.x += 1;
		}
		else if (this.x<= 0 && this.y <=0){
			this.x += 1;
			this.y += 1;
		}
		else if (this.x >= 0 && this.y <= 0){
			this.y += 1;
		}
		else if (this.x >= 0 && this.y >=0){
			this.x -=1;
			this.y -=1;
		}
		if (this.getHeight() > WelcomeStage.MAP_HEIGHT){
			// TODO; PROCEED TO GAME OVER STAGE
			this.updateStatus();
		}

		gameTimer.camX();
		gameTimer.camY();
	}

	public void updateStatus(){
		this.isAlive = false;
	}

	public void updateScore(){
		this.score += 1;
	}

	public void addEnemyEaten(EnemySumo enemy){
		this.blobsEaten.add(enemy);
	}

	public void scoreMultiplier(){
		this.score *= Ramune.SCORE_MULTIPLIER;
	}

	public int getScore(){
		return this.score;
	}

	public int getBlobsEaten(){
		return this.blobsEaten.size();
	}

	public boolean getStatus(){
		return this.isAlive;
	}
}
