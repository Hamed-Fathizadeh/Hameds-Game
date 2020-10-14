package Entity;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Diamond extends MapObject{

    private boolean drawIt = true;
    private int width = 24;
    private int height = 21;
    
    private BufferedImage[] diamonds; 
    
	public Diamond(TileMap tm) {
		super(tm);
		drawIt = true;
		
		
        try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
						"/Sprites/Player/diamond.png" 
						//"/Sprites/Player/playersprites3.png"
				)
			);
			
			diamonds = new BufferedImage[1];
			diamonds[0] = spritesheet.getSubimage(
					0,
					0,
					width,
					height
					
				);
        
            }
        catch(Exception ex)
             {
        	
        }
     
        animation = new Animation();
		animation.setFrames(diamonds);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
        
       
   }
	public int getHeight() { return height;}
	public int getWeidth() { return width;}
		
    public void setDrawIt(boolean b) {
    	drawIt = b;
    }
	
	public void update() {
			
		
		animation.update();
			
	}
	public void draw(Graphics2D g) {
		
		setMapPosition();
		super.draw(g);
		
		
		
	}
	
}