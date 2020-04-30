package dtp.effect;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Animation {
	private String name;
	private boolean isRepeated;
	private ArrayList<FrameImage> frameImages;
	private int currentFrame; 	//chi den cac so anh
	private ArrayList<Boolean> igoreFrames;
	private ArrayList<Double> delayFrames;  //chua thoi gian delay
	private long beginTime;
	private boolean drawRectFrame; //ve them cai khung
	
	public Animation() {
		delayFrames=new ArrayList<Double>();
		beginTime=0;
		currentFrame=0;
		igoreFrames=new ArrayList<Boolean>();
		drawRectFrame=false;
		isRepeated=true;
		
	}
	
	public Animation(Animation animation) {
		beginTime=animation.beginTime;	//chi truyen gtri k truyen vung nho
		currentFrame=animation.currentFrame;
		drawRectFrame=animation.drawRectFrame;
		isRepeated=animation.drawRectFrame;
		delayFrames=new ArrayList<Double>();
		for(Double d:animation.delayFrames) {
			delayFrames.add(d);
		}
		igoreFrames= new ArrayList<Boolean>();
		for(boolean b:animation.igoreFrames) {
			igoreFrames.add(b);
		}
		frameImages=new ArrayList<FrameImage>();
		for(FrameImage f: animation.frameImages) {
			frameImages.add(f);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsRepeated() {
		return isRepeated;
	}

	public void setRepeated(boolean isRepeated) {
		this.isRepeated = isRepeated;
	}

	public ArrayList<FrameImage> getFrameImages() {
		return frameImages;
	}

	public void setFrameImages(ArrayList<FrameImage> frameImages) {
		this.frameImages = frameImages;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public ArrayList<Boolean> getIgoreFrames() {
		return igoreFrames;
	}

	public void setIgoreFrames(ArrayList<Boolean> igoreFrames) {
		this.igoreFrames = igoreFrames;
	}

	public ArrayList<Double> getDelayFrames() {
		return delayFrames;
	}

	public void setDelayFrames(ArrayList<Double> delayFrames) {
		this.delayFrames = delayFrames;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public boolean getDrawRectFrame() {
		return drawRectFrame;
	}

	public void setDrawRectFrame(boolean drawRectFrame) {
		this.drawRectFrame = drawRectFrame;
	}
	
	public boolean isIgnoreFrame(int id) { //kiem tra co phai la 1 frame bi bo k
		return igoreFrames.get(id);
	}
	
	public void setIgnoreFrame(int id) {
		if(id>=0 && id<igoreFrames.size()) {
			igoreFrames.set(id, true);
		}
	}
	
	public void unIgnoreFrame(int id){
        if(id >= 0 && id <igoreFrames.size())
            igoreFrames.set(id, false);
    }
	
	public void setCurrentFrame(int currentFrame){ //kiem tra khoi luong Frame vao co hop le k
        if(currentFrame >= 0 && currentFrame < frameImages.size())
            this.currentFrame = currentFrame;
        else this.currentFrame = 0;
    }
	
	public void reset(){	//xóa bỏ mọi thứ, reset lại trạng thái ban đầu 
        currentFrame = 0;
        beginTime = 0;
        for(int i=0; i<igoreFrames.size(); i++) {
        	igoreFrames.set(i,false);
        }
    }
	public void add(FrameImage frameImage, double timeToNextFrame){
        igoreFrames.add(false);
        frameImages.add(frameImage);
        delayFrames.add(new Double(timeToNextFrame));
        
    }
	
	public BufferedImage getCurrentImage(){		//tra ve anh
        return frameImages.get(currentFrame).getImage();
    }
	
	public void Update(long currentTime){ //tham so thoi gian truyen vao, thoi gian hien tai
	        
	        if(beginTime == 0) beginTime = currentTime;
	        else{
	            
	            if(currentTime - beginTime > delayFrames.get(currentFrame)){   // neu thoi gian hien tai-beginTime lon hon thoi gian delay thi nextTime
	                nextFrame();
	                beginTime = currentTime;		//gan thoi gian bat dau bang thoi gian hien tai
	            }
	        }
	        
	    }

	private void nextFrame(){
	    
	    if(currentFrame >= frameImages.size() - 1){   //kiem tra currentFrame co bang phan tu cuoi cung cua mang k
	        if(isRepeated) 
	        	currentFrame = 0;	//muon lap lai thi cho currentFrame =0
	    }
	    else currentFrame++;		//neu k thi tang len
	    if(igoreFrames.get(currentFrame)) 	//neu la 1 frame bi bo qua thi nextFrame
	    	nextFrame();
	    
	}
	
	public boolean isLastFrame(){			//kiem tra xem chayj xong chua de chuyen trang thais nhan vat
        if(currentFrame == frameImages.size() - 1)
            return true;
        else return false;
    }
	
	public void flipAllImage(){	//di chuyen nguoc
	        
		for(int i = 0;i < frameImages.size(); i++){
	            
			BufferedImage image = frameImages.get(i).getImage();
	            
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);		//AffineTransform để lật
	        tx.translate(-image.getWidth(), 0);
	
	        AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_BILINEAR);
	        image = op.filter(image, null);
	            
	        frameImages.get(i).setImage(image);
	            
	        }
	        
	    }
	public void draw(int x, int y, Graphics2D g2){
        
        BufferedImage image = getCurrentImage();
        
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
        if(drawRectFrame)
            g2.drawRect(x - image.getWidth()/2, x - image.getWidth()/2, image.getWidth(), image.getHeight());
        
    }
}
