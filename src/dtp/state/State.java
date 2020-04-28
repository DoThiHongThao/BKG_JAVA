package dtp.state;

import java.awt.image.BufferedImage;

import dtp.userinterface.GamePanel;

public abstract class State {

	protected GamePanel gamePanel;
	
	public State(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public abstract void Update();
	public abstract void Render();
	
	public abstract BufferedImage getBufferedImage();
	
	public abstract void setPressedButton(int code);
	
	public abstract void setReleasedButton(int code);
	
}
