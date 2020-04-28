package dtp.state;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import dtp.control.Buttons;
import dtp.userinterface.GameMain;
import dtp.userinterface.GamePanel;

public class MenuState extends State{

	public final int NUMBER_OF_BUTTON = 4;
	private BufferedImage bufferedImage;
	public Graphics2D graphicsPaint;
	
	private Buttons[] buttons;
	private int buttonSelected = 0;
	
	public MenuState(GamePanel gamePanel) {
		super(gamePanel);
		
		bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, 
				GameMain.SCREEEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		buttons = new Buttons[NUMBER_OF_BUTTON];
		
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPressedButton(int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReleasedButton(int code) {
		// TODO Auto-generated method stub
		
	}
	
}
