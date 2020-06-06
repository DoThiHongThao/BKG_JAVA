package dtp.state;

import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.io.FileReader;
import java.io.IOException;

import dtp.control.Lables;
import dtp.userinterface.GameMain;
import dtp.userinterface.GamePanel;

public class MenuStates extends State implements MouseListener {

    private BufferedImage bufferedImage;
    private boolean music = true;
    private int state;
    
    public Graphics graphicsPaint;
    public Lables[] lables;

    public MenuStates(GamePanel gamePanel) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        loadFrame();
        gamePanel.addMouseListener(this);
    }

    @SuppressWarnings("resource")
    private void loadFrame(){
        lables = new Lables[6];
        String filetxt = "data/setmenu.txt";
        String filepng = "data/setmenu.png";
        try {
            FileReader fr = new FileReader(filetxt);
            BufferedReader br = new BufferedReader(fr);

            String line; 

                while ((line = br.readLine()).equals("")) ;
                int n = Integer.parseInt(line);

                for(int i = 0; i < n; i++){
                        while ((line = br.readLine()).equals("")) ;
                        if(i < 4)
                            lables[i] = new Lables(line);

                        if(i >= 8){
                            lables[(int)((float)i/2+0.5)] = new Lables(line);
                        }
                        while ((line = br.readLine()).equals("")) ;
                        String[] str = line.split(" ");
                        int x = Integer.parseInt(str[1]);

                        while ((line = br.readLine()).equals("")) ;
                        str = line.split(" ");
                        int y = Integer.parseInt(str[1]);

                        while ((line = br.readLine()).equals("")) ;
                        str = line.split(" ");
                        int w = Integer.parseInt(str[1]);

                        while ((line = br.readLine()).equals("")) ;
                        str = line.split(" ");
                        int h = Integer.parseInt(str[1]);

                        BufferedImage imageData = ImageIO.read(new File(filepng));
                        BufferedImage image = imageData.getSubimage(x, y, w, h);

                        if(i < 4){
                            lables[i].setOpaque(false);
                            lables[i].setImageIcon1(image);
                            lables[i].setBound(350 , 100 + (10 + 80)*i, 280, 80);
                        
                            lables[i].addMouseListener(this);
                            gamePanel.add(lables[i]);
                        }else if(i < 8){
                            lables[i - 4].setImageIcon2(image);
                        }
                        else {
                            lables[(int)(i/2.0 + 0.5)].setOpaque(false);
                            lables[(int)(i/2.0 + 0.5)].setImageIcon1(image);    
                            gamePanel.add(lables[(int)(i/2.0 + 0.5)]);
                        }
                        if(i == 7){
                            filepng = "data/hd.png";
                        }
                }

            lables[3].setBound(0, 0, 40, 40);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private Boolean Compare( MouseEvent e,  Lables l1) {
		if(e.getX() >= l1.getX() && e.getY()>=l1.getY()) {
			if(e.getX() <= l1.getX() + l1.getWidth() && e.getY() <= l1.getY() + l1.getHeight())
				return true;
		}
		return false;
    }

    @Override
    public void Update() {
        switch(getState()){
            case 0:
                this.state = 0;
                break;
            case 1:
                lables[4].setBound(GameMain.SCREEN_WIDTH/2 - 200, 
                    GameMain.SCREEN_HEIGHT/2 - 200, 400, 400);

                lables[5].setBound(GameMain.SCREEN_WIDTH/2 + 185, 
                    GameMain.SCREEN_HEIGHT/2 - 152, 40, 40);
                break;
            case 2:
                break;
        }
    }

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

        Image image = new ImageIcon("data/menu.png").getImage();
        graphicsPaint.drawImage(image, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
		switch(getState()){
            case 0:
                for(int i=0; i< 4; i++){
                    lables[i].draw((Graphics2D) graphicsPaint);
                }
                break;
            case 1:
                lables[4].draw((Graphics2D) graphicsPaint);
                lables[5].draw((Graphics2D) graphicsPaint);
                break;
        }
		
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        switch(getState()){
            case 0:
                if(Compare(e, lables[0]) ) {
                    lables[0].change();
                    lables[0].draw((Graphics2D) graphicsPaint);
                    gamePanel.setState(new GameWorld(gamePanel, isMusic()));
                }
                if(Compare(e, lables[1])) {
                    lables[1].change();
                    lables[1].draw((Graphics2D) graphicsPaint);  
                    this.setState(1);
                }
                
                if(Compare(e, lables[3])) {
                    lables[3].change();
                    lables[3].draw((Graphics2D)graphicsPaint);
                    if(isMusic()) setMusic(false);
                    else setMusic(true);
                }
                if(Compare(e, lables[2])) {
                    System.exit(0);
                }
                break;
            case 1:
                if(Compare(e, lables[4])){
                    lables[1].change();
                    this.setState(0);
                    Update();
                }
                break;
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

    @Override
    public void setPressedButton(int code) {}

    @Override
    public void setReleasedButton(int code) {}    

}