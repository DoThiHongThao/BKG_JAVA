package dtp.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import dtp.effect.DataLoader;



public class GameMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 600;

	private String framefile = "data/data_nv.txt";
    private String animationfile = "data/data_animation.txt";
    private String physmapfile = "data/physmap.txt";
    private String backgroundmapfile = "data/bgmap.txt";
	
	GamePanel gamepanel;
	
	public GameMain() {
		super("BKG");
		Toolkit tookit = this.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		this.setLocationRelativeTo(null);
		this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			DataLoader.getInstance().LoadData(framefile, animationfile, physmapfile, backgroundmapfile);			//phải load dữ liệu trc khi chạy game
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			gamepanel = new GamePanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
