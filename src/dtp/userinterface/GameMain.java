package dtp.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import dtp.effect.DataLoader;

import javax.swing.JPanel;


public class GameMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEEN_HEIGHT = 600;
	
	GamePanel gamepanel;
	
	public GameMain() {
		super("BKG");
		Toolkit tookit = this.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		this.setLocationRelativeTo(null);
		this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEEN_HEIGHT)/2, SCREEN_WIDTH, SCREEEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			DataLoader.getInstance().LoadData();			//phải load dữ liệu trc khi chạy game
		} catch (IOException e) {
			e.printStackTrace();
		}
		gamepanel = new GamePanel();
		this.add(gamepanel);
		this.addKeyListener(gamepanel);
	}
	
	public void startGame() {
		gamepanel.startGame();
	}
	
	public static void main(String[] args) {
		GameMain gameMain = new GameMain();
		gameMain.setVisible(true);
		gameMain.startGame();
	}

}
