package dtp.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import dtp.control.Lables;
import dtp.userinterface.*;

public class Instruction extends State implements MouseListener{

	private BufferedImage bufferedImage;
    public Graphics graphicsPaint;
    Lables[] gameLable;
	private int NUMBER_OF_LABEL;
    
    public Instruction(GamePanel gamePanel) throws IOException {
    	super(gamePanel);
    	FileReader fileReader;
		try {
			fileReader = new FileReader("data/setmenu.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	    	String lineString;
	    	
	    	while((lineString = bufferedReader.readLine()).equals(""));
	    	NUMBER_OF_LABEL = Integer.parseInt(lineString);
	    	
	    	gameLable = new Lables[NUMBER_OF_LABEL];
	    	for(int i=0; i<NUMBER_OF_LABEL; i++) {
	    		int x, y, width, height;
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		gameLable[i] = new Lables(lineString);
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		String[] arr = lineString.split(" ");
	    		x = Integer.parseInt(arr[1]);
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		arr = lineString.split(" ");
	    		y = Integer.parseInt(arr[1]);
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		arr = lineString.split(" ");
	    		width = Integer.parseInt(arr[1]);
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		arr = lineString.split(" ");
	    		height = Integer.parseInt(arr[1]);
	    		
	    		BufferedImage imageData = ImageIO.read(new File("data/setmenu.png"));
				BufferedImage image = imageData.getSubimage(x, y, width, height);
				gameLable[i].setImageIcon1(image);
				gameLable[i].setBound(350 , 100 + (10 + 80)*i, 280, 80);
				
				gameLable[i].addMouseListener(this);
	    	}
	    	gameLable[4].setBound(0, 0, 40, 40);
	    	gameLable[5].setBound(0, 0, 40, 40);
	    	
	    	bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		gamePanel.addMouseListener(this);
	}
    
	@Override
	public void Update() {}

	@Override
	public void Render() {
		if (bufferedImage == null) {
            bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if (graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }
        
        for(int i=0; i< NUMBER_OF_LABEL; i++) {
        	if(gameLable[i].getSet() == true)
        		gameLable[i].draw((Graphics2D) graphicsPaint);
        }
	}

	@Override
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	@SuppressWarnings("unused")
	private Boolean Compare(MouseEvent e, Lables l1) {
    	if(e.getX() >= l1.getX() && e.getY()>=l1.getY()) {
    		if(e.getX() <= l1.getX() + l1.getWidth() && e.getY() <= l1.getY() + l1.getHeight())
    			return true;
    	}
    	return false;
    }
	
	@Override
	public void setPressedButton(int code) {}

	@Override
	public void setReleasedButton(int code) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(Compare(e, gameLable[0]) ) {
			try {
				gamePanel.setState(new MenuState(gamePanel));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}