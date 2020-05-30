package dtp.userinterface;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JPanel;

import dtp.state.MenuState;
import dtp.state.State;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    
	private static final long serialVersionUID = 1L;
	
<<<<<<< HEAD
	public State gameState;
	public InputManager inputManager;
	
	private Thread thread;
	private boolean isRunning = true;
	FrameImage frame1;
	Animation anima1;
	
	public GamePanel() {
	
//		gameState = new MenuState(this);
		inputManager = new InputManager();
//		try {
//			BufferedImage image=ImageIO.read(new File("data/bat.png"));
//			BufferedImage image1=image.getSubimage(529, 38, 100, 100);
//			frame1=new FrameImage("frame1",image1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		frame1=DataLoader.getInstance().getFrameImage("chem_manh7");
		anima1=DataLoader.getInstance().getAnimation("chet");
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
=======
	State gameState;
    InputManager inputManager;
    Thread gameThread;

    public boolean isRunning = true;

    public GamePanel() throws IOException {
        gameState = new MenuState(this);
        inputManager = new InputManager(gameState);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
>>>>>>> c58463736c18437dbb98a2b4705819365a00e412
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

    public void paint(Graphics g) {
        g.drawImage(gameState.getBufferedImage(), 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.setPressedButton(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.setReleasedButton(e.getKeyCode());
    }

    public void setState(State state) {
        gameState = state;
        inputManager.setState(state);
    }

}
