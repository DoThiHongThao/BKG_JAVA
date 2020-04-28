package dtp.userinterface;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import javax.swing.JFrame;

import dtp.state.MenuState;
import dtp.state.State;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public State gameState;
	public InputManager inputManager;
	
	private Thread thread;
	private boolean isRunning = true;
	
	public GamePanel() {
	
		gameState = new MenuState(this);
		//inputManager = new InputManager(gameState);
		
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;
        long period = 1000000000 / 80;
        
        while (isRunning) {
            gameState.Update();
            gameState.Render();

            repaint();
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
	
	public void paint(Graphics2D g) {
		g.drawImage(gameState.getBufferedImage(), 0, 0, this);
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
