package dtp.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import dtp.control.Lables;
import dtp.userinterface.GamePanel;
import dtp.userinterface.*;


public class MenuState extends State implements MouseListener{

	private int NUMBER_OF_LABEL;
    private BufferedImage bufferedImage;
    public Graphics graphicsPaint;
    
    
    Lables[] lables;
    public MenuState(final GamePanel gamePanel) throws IOException {
    	super(gamePanel);
    	
    	bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
     
        
    	FileReader fileReader;
		try {
			fileReader = new FileReader("data/setmenu.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	    	String lineString;
	    	
	    	while((lineString = bufferedReader.readLine()).equals(""));
	    	NUMBER_OF_LABEL = Integer.parseInt(lineString);
	    	
	    	lables = new Lables[NUMBER_OF_LABEL];
	    	for(int i=0; i<NUMBER_OF_LABEL; i++) {
	    		int x, y, width, height;
	    		
	    		while((lineString = bufferedReader.readLine()).equals(""));
	    		lables[i] = new Lables(lineString);
	    		
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
	    		
	    		final BufferedImage imageData = ImageIO.read(new File("data/setmenu.png"));
				final BufferedImage image = imageData.getSubimage(x, y, width, height);
				lables[i].setImageIcon1(image);
				if(i <= 3) {
					lables[i].setBound(350 , 100 + (10 + 80)*i, 280, 80);
				}
				
				lables[i].addMouseListener(this);
	    	}
	    	lables[4].setBound(0, 0, 40, 40);
	    	lables[5].setBound(0, 0, 40, 40);
	    	lables[5].setSet(false);
	    	lables[4].setImageIcon2(lables[5].getImage());
			bufferedReader.close();
		} catch (final FileNotFoundException e) {
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

        final Image image = new ImageIcon("data/bg-01.png").getImage();
        graphicsPaint.drawImage(image, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
      
        for(int i=0; i< NUMBER_OF_LABEL; i++) {
        	if(lables[i].getSet() == true)
        		lables[i].draw((Graphics2D) graphicsPaint);
        }
    }
    
    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
   
 
    private Boolean Compare(final MouseEvent e, final Lables l1) {
    	if(e.getX() >= l1.getX() && e.getY()>=l1.getY()) {
    		if(e.getX() <= l1.getX() + l1.getWidth() && e.getY() <= l1.getY() + l1.getHeight())
    			return true;
    	}
    	return false;
    }

	@Override
	public void mouseClicked(final MouseEvent e) {

		if(Compare(e, lables[0]) ) {
			//gamePanel.setState(new GameWorld(gamePanel));
		}
		if(Compare(e, lables[1])) {
			try {
				gamePanel.setState(new Instruction(gamePanel));
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(Compare(e, lables[4])) {
			lables[4].change();
			lables[4].draw((Graphics2D)graphicsPaint);
			System.out.println("hello");
		}
		if(Compare(e, lables[3])) {
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {}

	@Override
	public void mouseReleased(final MouseEvent e) {}

	@Override
	public void mouseEntered(final MouseEvent e) {}

	@Override
	public void mouseExited(final MouseEvent e) {}

	@Override
	public void setPressedButton(final int code) {}

	@Override
	public void setReleasedButton(final int code) {}

}