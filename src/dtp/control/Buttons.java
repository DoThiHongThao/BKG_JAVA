package dtp.control;

import java.awt.Image;

public abstract class Buttons {

	private static final int NONE = 0;
	protected Image image;
	protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int paddingTextX;
    protected int paddingTextY;
    protected int state;
    
    public Buttons(Image image, int x, int y, int width, int height,
    		int paddingX, int paddingY) {
    	this.image = image;
    	this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.paddingTextX = paddingX;
        this.paddingTextY = paddingY;
    }
	
}
