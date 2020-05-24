package dtp.effect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("deprecation")

public class DataLoader {
    private static DataLoader instance = null;

    private String soundfile = "data/sounds.txt";
    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String, Animation> animations;
    private Hashtable<String, AudioClip> sounds;
    private int[][] phys_map;
    private int[][] background_map;

    JFrame LoadMain;
	JPanel panel;
	JLabel[] ldJLabel;
	JLabel zoroJLabel;

    private DataLoader() {
    }

    public static DataLoader getInstance() {
        if (instance == null)
            instance = new DataLoader();
        return instance;
    }

    public AudioClip getSound(String name) {
        return instance.sounds.get(name);
    }

    public Animation getAnimation(String name) {
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }

    public FrameImage getFrameImage(String name) {
        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }

    public int[][] getPhysicalMap() {
        return instance.phys_map;
    }

    public int[][] getBackgroundMap() {
        return instance.background_map;
    }

    public void LoadData(String framefile, String animationfile, String physmapfile, String backgroundmapfile) throws IOException {
        LoadMain = new JFrame();
		Toolkit tookit = LoadMain.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		LoadMain.setBounds((dimension.width - 800)/2, (dimension.height - 320)/2, 
				800, 320 );
		
		LoadMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoadMain.setVisible(true);
		
		
		JLabel tTJLabel = new JLabel(new ImageIcon("data/loading-001.png"));
		ldJLabel = new JLabel[20];
		ldJLabel[0] = new JLabel(new ImageIcon("data/loading10.png"));
		
		zoroJLabel = new JLabel(new ImageIcon("data/zoro.png"));

		JLabel loadingJLabel = new JLabel(new ImageIcon("data/loading.png"));
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 800, 300);
		
		
		panel.add(ldJLabel[0]);
		for(int i=1;i<20;i++) {
			ldJLabel[i] = new JLabel(new ImageIcon("data/loading90.png"));
			panel.add(ldJLabel[i]);
		}
		
		JLabel backJLabel = new JLabel(new ImageIcon("data/image2.jpg"));
		backJLabel.setBounds(0, 0, 800, 300);
		backJLabel.setLayout(null);
		backJLabel.add(zoroJLabel);
		
		panel.add(tTJLabel);
		panel.add(loadingJLabel);
		panel.add(backJLabel);
		LoadMain.add(panel);

		tTJLabel.setBounds(100, 120, 600, 100);
		ldJLabel[0].setBounds(97, 124, 90, 80);
		
		loadingJLabel.setBounds(300, 124+60, 200, 80);
		zoroJLabel.setBounds(119, 60, 100, 90);
		
		zoroJLabel.setOpaque(false);
		
		LoadFrame(framefile);
		LoadMain.setVisible(true);
		LoadAnimation(animationfile);
		zoroJLabel.setBounds(111+31*13, 60, 100, 90);
		//ldJLabel[13].setBounds(120+31*13, 125, 60, 79);
		LoadPhysMap(physmapfile);
		zoroJLabel.setBounds(620-32-34, 60, 100, 90);
		ldJLabel[14].setBounds(620-32-35, 125, 60, 79);
		LoadBackgroundMap(backgroundmapfile);
		zoroJLabel.setBounds(620-33, 60, 100, 90);
		ldJLabel[15].setBounds(620-32, 125, 60, 79);
		LoadSounds();
		zoroJLabel.setBounds(620, 60, 100, 90);
		ldJLabel[16].setIcon(new ImageIcon("data/loading100.png"));
		ldJLabel[16].setBounds(620, 125, 60, 79);
		
		
		double a = System.currentTimeMillis();
		double b;
		do {
		b = System.currentTimeMillis();
		}while(b - a < 10000);
		
		LoadMain.setVisible(false);
    }

    @SuppressWarnings("resource")
    public void LoadSounds() throws IOException {
        sounds = new Hashtable<String, AudioClip>();
        FileReader fr = new FileReader(soundfile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        if (br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        } else {
            fr = new FileReader(soundfile);
            br = new BufferedReader(fr);
            while ((line = br.readLine()).equals("")) ;
            int n = Integer.parseInt(line);
            for (int i = 0; i < n; i++) {
                AudioClip audioClip = null;
                while ((line = br.readLine()).equals("")) ;

                String[] str = line.split(" ");
                String name = str[0];
                try {
                    audioClip = Applet.newAudioClip(new URL("file", "", str[1]));
                } catch (MalformedURLException ex) {
                }
                instance.sounds.put(name, audioClip);
            }
        }
        br.close();
    }

    public void LoadBackgroundMap(String backgroundmapfile) throws IOException {
        FileReader fr = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);

        instance.background_map = new int[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            line = br.readLine();
            String[] str = line.split(" |  ");
            for (int j = 0; j < numberOfColumns; j++)
                instance.background_map[i][j] = Integer.parseInt(str[j]);
        }

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++)
                System.out.print(" " + instance.background_map[i][j]);
            System.out.println();
        }
        br.close();
    }

    public void LoadPhysMap(String physmapfile) throws IOException {
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);

        instance.phys_map = new int[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            line = br.readLine();
            String[] str = line.split(" ");
            for (int j = 0; j < numberOfColumns; j++)
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
        }
        br.close();
    }

    @SuppressWarnings("resource")
    public void LoadAnimation(String animationfile) throws IOException {
        animations = new Hashtable<String, Animation>();

        FileReader fr = new FileReader(animationfile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        } else {
            fr = new FileReader(animationfile);
            br = new BufferedReader(fr);
            while ((line = br.readLine()).equals("")) ;
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++) {
                Animation animation = new Animation();
                while ((line = br.readLine()).equals("")) ;
                animation.setName(line);
                while ((line = br.readLine()).equals("")) ;
                String[] str = line.split(" ");
                for (int j = 0; j < str.length; j += 2)
                    animation.add(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));
                instance.animations.put(animation.getName(), animation);
            }
        }
        br.close();
    }

    @SuppressWarnings("resource")
    public void LoadFrame(String framefile) throws IOException {
        frameImages = new Hashtable<String, FrameImage>();
        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if (br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        } else {
            fr = new FileReader(framefile);
            br = new BufferedReader(fr);

            while ((line = br.readLine()).equals("")) ;

            int n = Integer.parseInt(line);

            int j = 1;

            for (int i = 0; i < n; i++) {

                if(i == ((n-1)/12)*j) {
					zoroJLabel.setBounds(111+34*j, 60, 100, 90);
					if(j==1)
						ldJLabel[j].setBounds(111+33, 125, 60, 79);
					else ldJLabel[j].setBounds(111+34*j, 125, 60, 79);
					
					double a = System.nanoTime();
					double b;
					do {
					b = System.nanoTime();
					}while(b - a < 1000000);
					
					LoadMain.setVisible(true);
					j++;
				}

                FrameImage frame = new FrameImage();

                while ((line = br.readLine()).equals("")) ;
                frame.setName(line);

                while ((line = br.readLine()).equals("")) ;
                String[] str = line.split(" ");
                String path = str[1];

                while ((line = br.readLine()).equals("")) ;
                str = line.split(" ");
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

                BufferedImage imageData = ImageIO.read(new File(path));
                BufferedImage image = imageData.getSubimage(x, y, w, h);
                frame.setImage(image);

                instance.frameImages.put(frame.getName(), frame);
            }
        }
        br.close();
    }
}

