package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	protected Image img;
	protected int x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

    public Sprite(int xPos, int yPos){
		this.x = xPos;
		this.y = yPos;
		this.visible = true;
	}

	// Set the respective image of the object
	protected void loadImage(Image image){
		try{
			this.img = image;
	        this.setSize();
		} catch(Exception e) {}
	}

	// Sets the image to the image view node
	public void render(GraphicsContext gc){
        gc.drawImage(this.img, this.x, this.y);
    }

	// Set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
        this.height = this.img.getHeight();
	}

	// Checks for the collision of 2 sprites
	protected boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}

	// Returns the bounds of an image
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	// Getter Methods
	public Image getImage(){
		return this.img;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public int getDX(){
		return this.dx;
	}

	public int getDY(){
		return this.dy;
	}

	public int getHeight(){
		return (int)this.height;

	}

	public int getWidth(){
		return (int)this.width;
	}

	public boolean isVisible(){
		return visible;
	}

	// Setter Methods
	public void setDX(int dx){
		this.dx = dx;
	}

	public void setDY(int dy){
		this.dy = dy;
	}

	public void setX (int x){
		this.x = x;
	}

	public void setY (int y){
		this.y = y;
	}

	public void vanish(){
		this.visible = false;
	}

	public void setWidth(double val){
		this.width = val;
	}

	public void setHeight(double val){
		this.height = val;
	}

	public void setVisible(boolean value){
		this.visible = value;
	}
}
