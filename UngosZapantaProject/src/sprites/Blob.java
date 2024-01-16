package sprites;

public class Blob extends Sprite {

	protected String name;
	protected double speed;
	private int foodEaten;
	private int sumoEaten;
	private boolean alive;

	//public final static Image BLOB_IMAGE = new Image("images/playerSumo2.png",Blob.BLOB_WIDTH,Blob.BLOB_WIDTH,false,false);
	public final static int BLOB_WIDTH = 40;
	public final static int SPEED_CAP = 120;

	public Blob(int x, int y){
		super(x,y);
//		this.name = name;
		this.alive = true;
		this.foodEaten = 0;
		this.sumoEaten = 0;
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public int getFoodEaten(){
		return this.foodEaten;
	}

	public int getSumoEaten(){
		return this.sumoEaten;
	}


	public void die(){
    	this.alive = false;
    }

	// Method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		this.x += this.dx;
		this.y += this.dy;
	}
}