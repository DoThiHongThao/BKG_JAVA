package dtp.effect;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class DataLoader {
	private static DataLoader instance= null;	//chi dung duy nhat 1 minh instance
	private String framefile="data/nv.txt";
	//private String animationfile ="data/animation.txt";
	
	private Hashtable<String, FrameImage> frameImages;		//lưu theo dạng key và value
	//private Hashtable<String, Animation> animation;
	//private Hashtable<String, AudioClip> sounds;
	
	private int[][] phys_map;
	private int[][] background_map;
	private DataLoader() {}
	public static DataLoader getInstance(){
		if(instance==null) {
			instance=new DataLoader();
		}
		return instance;
	}
	
	public FrameImage getFrameImage(String name) {
			FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
			return frameImage;
	}
	
//	public Animation getAnimation(String name) {
//		Animation animation=new Animation(instance.animation.get(name));
//		return animation;
//	}
	public void LoadFrame() throws IOException{
		frameImages=new Hashtable<String, FrameImage>();
		FileReader fr=new FileReader(framefile);
		BufferedReader br=new BufferedReader(fr);
		
		String line=null;
		if(br.readLine()==null) {
			System.out.println("No data");
			throw new IOException();
		}
		else {
			fr=new FileReader(framefile);
			br=new BufferedReader(fr);
			
			while((line=br.readLine()).equals(""));
			int n=Integer.parseInt(line);
			for(int i=0; i<n; i++) {
				FrameImage frame=new FrameImage();
				while((line=br.readLine()).equals(""));
				frame.setName(line);
				
				while((line=br.readLine()).equals(""));
				String[] str=line.split(" ");			//splip chia line thành các mảng cách nhau bởi dấu cách
				String path=str[1];
				
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int x=Integer.parseInt(str[1]);
				
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int y=Integer.parseInt(str[1]);
				
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int w=Integer.parseInt(str[1]);
				
				while((line=br.readLine()).equals(""));
				str=line.split(" ");
				int h=Integer.parseInt(str[1]);
				
				BufferedImage imageData=ImageIO.read(new File(path));
				BufferedImage image=imageData.getSubimage(x,y,w,h);
				BufferedImage scaledImage = new MultiStepRe
				frame.setImage(image);
				
				instance.frameImages.put(frame.getName(), frame);
			}
		}
		br.close();
	}
//	public void LoadAnimation() throws IOException{
//		animation=new Hashtable<String, Animation>();
//		FileReader fr=new FileReader(animationfile);
//		BufferedReader br=new BufferedReader(fr);
//		String line=null;
//		if(br.readLine()==null) {
//			System.out.println("No data");
//			throw new IOException();
//		}
//		else {
//			fr=new FileReader(framefile);
//			br=new BufferedReader(fr);
//			
//			while((line=br.readLine()).equals(""));
//			int n=Integer.parseInt(line);
//			
//			for(int i=0; i<n; i++) {
//				Animation animation=new Animation();
//				while((line=br.readLine()).equals(""));
//				animation.setName(line);
//				
//				while((line=br.readLine()).equals(""));
//				String[] str=line.split(" ");
//				for(int j=0; j<str.length; j+=2) {
//					animation.add(getFrameImage(str[j]),Double.parseDouble(str[j+1]));
//				}
//				instance.animation.put(animation.getName(), animation);
//			}
//		}
//		br.close();
//	}
	
	public void LoadData() throws IOException{
		LoadFrame();
//		LoadAnimation();
//		LoadPhysMap();
//		LoadBackground();
//		LoadSounds();
	}
}
