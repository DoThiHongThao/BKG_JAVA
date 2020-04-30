package dtp.effect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {
	private String name;
	private BufferedImage image;
	public FrameImage(String name, BufferedImage image) {
		super();
		this.name = name;
		this.image = image;
	}
	public FrameImage() {
		super();
		this.name = null;
		this.image = null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public FrameImage(FrameImage frameImage) {
		image = new BufferedImage(frameImage.getWidthImage(), 
                frameImage.getHeightImage(), frameImage.image.getType());
        Graphics g = image.getGraphics();
        g.drawImage(frameImage.image, 0, 0, null);
        name = frameImage.getName();
	}
	
	public void draw(Graphics2D g2,int x, int y) {
		g2.drawImage(image, x-image.getWidth()/2,y-image.getHeight()/2 , null);
	}
	
	public int getWidthImage(){
		return image.getWidth();
	}

	public int getHeightImage(){
		return image.getHeight();	
	}
	
}
