package dtp.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.sun.org.apache.bcel.internal.classfile.Field;

import dtp.effect.DataLoader;
import dtp.effect.FrameImage;
import dtp.state.MenuState;
import dtp.state.State;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public State gameState;
	public InputManager inputManager;
	
	private Thread thread;
	private boolean isRunning = true;
	FrameImage frame1;
	public GamePanel() {
	
//		gameState = new MenuState(this);
		inputManager = new InputManager();
		try {
			BufferedImage image=ImageIO.read(new File("data/465.jpg"));
			BufferedImage image1=image.getSubimage(529, 38, 100, 100);
			frame1=new FrameImage("frame1",image1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame1=DataLoader.getInstance().getFrameImage("chem1");
		System.out.println(frame1.getName());
	}

	public void startGame() {
//		thread = new Thread(this);
//		thread.start();
		if(thread==null) {
			isRunning=true;
			thread=new Thread(this);
			thread.start();
			
		}
	}

	@Override
	public void run() {
		long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;
        long period = 1000000000 / 80;
        
        while (isRunning) {
//            gameState.Update();
//            gameState.Render();

            //repaint();
            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else Thread.sleep(period / 2000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            previousTime = System.nanoTime();
        }
	}
	
	@Override
	public void paint(Graphics g) {
//		g.drawImage(gameState.getBufferedImage(), 0, 0, this);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GameMain.SCREEN_WIDTH, GameMain.SCREEEN_HEIGHT);
		Graphics2D g2=(Graphics2D)g;
		System.out.println(frame1.getName());
		frame1.draw(g2, 100, 130);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	
}
