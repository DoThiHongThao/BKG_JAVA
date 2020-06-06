package dtp.state;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;

import java.io.BufferedReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import dtp.control.Lables;
import java.awt.image.BufferedImage;


public class WinLose {
    public Lables WL[];
    public WinLose(GameWorld gameWorld){
        WL = new Lables[9];
        try {
            FileReader fr = new FileReader("data/hd.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;

            if (br.readLine() == null) {
                System.out.println("No data");
                throw new IOException();
            }else {

                while ((line = br.readLine()).equals("")) ;
                int n = Integer.parseInt(line);

                for(int i = 0; i < 9; i++){

                        while ((line = br.readLine()).equals("")) ;
                        WL[i] = new Lables(line);
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

                        BufferedImage imageData = ImageIO.read(new File("data/hd.png"));
                        BufferedImage image = imageData.getSubimage(x, y, w, h);

                        WL[i].setOpaque(false);
                        WL[i].setImageIcon1(image);
                        //gamePanel.add(WL[i]);
                }
                WL[2].setBounds(50 , 50, 400, 400);
                WL[1].setBounds(350, 60, 40, 40);
                WL[3].setBounds(100, 300, 80, 80);
                WL[4].setBounds(300, 300, 80, 80);
                WL[5].setBounds(150, 50, 100, 50);
                WL[8].setBounds(150, 100, 80, 50);
                WL[7].setBounds(150, 200, 80, 50);
                for(int i=1; i<9; i++){
                   // WL[i].draw((Graphics2D) bufferedImage.getGraphics());
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}