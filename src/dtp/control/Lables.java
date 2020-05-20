package dtp.control;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public class Lables extends JLabel {
    private static final long serialVersionUID = 1L;
    
    private int x, y, width, height;
    BufferedImage imageIcon, imageIcon2;
	String image, name;
	private boolean set;
	public Lables(String name) {
		setText(name);
		set = true;
	}
	
	public void draw(Graphics2D graphics2d) {
		if(getSet() == true) {
			graphics2d.drawImage(imageIcon, x, y, width, height,this);
		}
	}
	public void setBound(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setBounds(x, y, width, height);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setImageIcon1(BufferedImage image) {
		this.imageIcon = image;
		
	}
	
	public void setImageIcon2(BufferedImage image) {
		this.imageIcon2 = image;
	}
	
	public void change() {
		BufferedImage image;
		image = imageIcon;
		imageIcon = imageIcon2;
		imageIcon2 = image;
	}
	
	public BufferedImage getImage() {
		return imageIcon;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setText(name);
	}

	public boolean getSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}
}