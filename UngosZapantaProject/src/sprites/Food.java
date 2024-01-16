package sprites;

import java.util.Random;
import javafx.scene.image.Image;

public class Food extends Sprite {
	public boolean alive;

	public static final int FOOD_WIDTH = 20;
	public final static Image GYOZA_IMAGE = new Image("images/gyoza_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );
	public final static Image RAMEN_IMAGE = new Image("images/ramen_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );
	public final static Image SUSHI_IMAGE = new Image("images/sushi_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );
	public final static Image TAKOYAKI_IMAGE = new Image("images/takoyaki_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );
	public final static Image TEMPURA_IMAGE = new Image("images/tempura_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );
	public final static Image UDON_IMAGE = new Image("images/udon_food.png",Food.FOOD_WIDTH, Food.FOOD_WIDTH, false, false );

	public Food(int x, int y) {
		super(x, y);
		this.alive = true;
	}

	public void checkCollision(Blob blob){
		if(this.collidesWith(blob)){
			if( blob instanceof PlayerSumo){
				System.out.println(blob.getName() + " collides with Food");
				PlayerSumo player = (PlayerSumo) blob;
				player.updateScore();
				this.vanish();
				blob.setHeight(blob.height+=10);
				blob.setWidth(blob.width+=10);
				blob.loadImage(new Image("images/playerSumo.png",blob.getHeight(), blob.getWidth(), false, false ));
			}
			else {
				this.vanish();
				blob.setHeight(blob.height+=10);
				blob.setWidth(blob.width+=10);
				if (blob.getName().equals("ENEMY_SUMO_1")){
					blob.loadImage(new Image("images/enemySumo1.png",blob.getHeight(), blob.getWidth(), false, false ));
				}
				else{
					blob.loadImage(new Image("images/enemySumo2.png",blob.getHeight(), blob.getWidth(), false, false ));
				}
			}

		}
	}

	// Sets the image of the food which would be displayed
	public void generateImage(){
		Random randomFood = new Random();

		int foodToUse = randomFood.nextInt(6);
		switch(foodToUse){
			case 0:
				this.loadImage(GYOZA_IMAGE);
				break;
			case 1:
				this.loadImage(RAMEN_IMAGE);
				break;
			case 2:
				this.loadImage(SUSHI_IMAGE);
				break;
			case 3:
				this.loadImage(TAKOYAKI_IMAGE);
				break;
			case 4:
				this.loadImage(TEMPURA_IMAGE);
				break;
			case 5:
				this.loadImage(UDON_IMAGE);
				break;
			default:
				this.loadImage(RAMEN_IMAGE);
		}
	}

	public boolean isAlive(){
		return this.alive;
	}

}