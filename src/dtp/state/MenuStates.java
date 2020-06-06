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
        lables = new Lables[21];
        int k = 0;
        String file = "data/setmenu.txt";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;

            if (br.readLine() == null) {
                System.out.println("No data");
                throw new IOException();
            }else {
                fr = new FileReader(file);
                br = new BufferedReader(fr);

                while ((line = br.readLine()).equals("")) ;
                int n = Integer.parseInt(line);

                for(int i = 0; i < n; i++){
                    while ((line = br.readLine()).equals("")) ;
                    int m = Integer.parseInt(line);

                    while ((line = br.readLine()).equals("")) ;
                    String link = line;
                    for(int j = 0; j < m; j++){

                        while ((line = br.readLine()).equals("")) ;
                        lables[k] = new Lables(line);
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

                        BufferedImage imageData = ImageIO.read(new File(link));
                        BufferedImage image = imageData.getSubimage(x, y, w, h);

                        lables[k].setOpaque(false);
                        lables[k].setImageIcon1(image);
                        lables[k].setBound(350 , 100 + (10 + 80)*k, 280, 80);
                        if(k != 9){
                            lables[k].addMouseListener(this);
                        }
                        gamePanel.add(lables[j]);
                        if( m == 8 && k < 8){
                            if(j > 4){
                                lables[j - 4].setImageIcon2(image);
                            }
                        }
                        k++;
                    }
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
                lables[8].setBound(GameMain.SCREEN_WIDTH/2 - 200, 
                GameMain.SCREEN_HEIGHT/2 - 200, 400, 400);
                lables[8].addMouseListener(this);
                lables[9].setBound(GameMain.SCREEN_WIDTH/2 + 185, 
                    GameMain.SCREEN_HEIGHT/2 - 152, 40, 40);
                gamePanel.addMouseListener(this);
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
                lables[8].draw((Graphics2D) graphicsPaint);
                lables[9].draw((Graphics2D) graphicsPaint);
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
                if(Compare(e, lables[9])){
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