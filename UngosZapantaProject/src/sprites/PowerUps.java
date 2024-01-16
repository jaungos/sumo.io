package sprites;

import java.util.Random;
import javafx.scene.image.Image;

public class PowerUps extends Sprite {
	private String name;
	public boolean alive;
	public boolean moved;

	public static final int PUPS_WIDTH = 80;
	public final static Image MOCHI_IMAGE = new Image("images/mochi_pups.png", PowerUps.PUPS_WIDTH, PowerUps.PUPS_WIDTH, false, false );
	public final static Image RAMUNE_IMAGE = new Image("images/ramune_pups.png", PowerUps.PUPS_WIDTH, PowerUps.PUPS_WIDTH, false, false );
	public final static Image SAKE_IMAGE = new Image("images/sake_pups.png", PowerUps.PUPS_WIDTH, PowerUps.PUPS_WIDTH, false, false );


	public PowerUps(int x, int y) {
		super(x, y);
		this.alive = true;
	}

	public boolean getMoved(){
		return this.moved;
	}

	public void setMoved(boolean moved){
		this.moved = moved;
	}

	public void checkCollision(Blob blob){
		if(this.collidesWith(blob)){
			if( blob instanceof PlayerSumo){

				if (this.getName().equals("MOCHI")){
					System.out.println(blob.getName() + " collides with MOCHI + 10 SPEED!");
					blob.speed += 10;
					blob.setHeight(blob.height+=10);
					blob.setWidth(blob.width+=10);
					blob.loadImage(new Image("images/playerSumo.png",blob.getHeight(), blob.getWidth(), false, false ));
				}

				else if (this.getName().equals("RAMUNE")){
					System.out.println(blob.getName() + " collides with RAMUNE + 5 seconds IMMUNITY");
					blob.setHeight(blob.height+=50);
					blob.setWidth(blob.width+=50);
					blob.loadImage(new Image("images/playerSumo.png",blob.getHeight(), blob.getWidth(), false, false ));
				}

				else if (this.getName().equals("SAKE")){
					System.out.println(blob.getName() + " collides with SAKE + 5 seconds SCORE MULTIPLIER");
					((PlayerSumo) blob).scoreMultiplier();
					blob.loadImage(new Image("images/playerSumo.png",blob.getHeight(), blob.getWidth(), false, false ));
				}
				this.vanish();
			}
		}
	}

	// Sets the image of the power-up which would also be the type
	public void generateImage(){
		Random randomFood = new Random();

		int foodToUse = randomFood.nextInt(3);
		switch(foodToUse){
			case 0:
				this.loadImage(MOCHI_IMAGE);
				this.name = "MOCHI";
				break;
			case 1:
				this.loadImage(RAMUNE_IMAGE);
				this.name = "RAMUNE";
				break;
			case 2:
				this.loadImage(SAKE_IMAGE);
				this.name = "SAKE";
				break;
			default:
				this.loadImage(MOCHI_IMAGE);
				this.name = "MOCHI";
		}
	}

	public boolean isAlive(){
		return this.alive;
	}

	public String getName(){
		return this.name;
	}

}