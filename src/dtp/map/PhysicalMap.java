package dtp.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.camera.Camera;
import dtp.effect.DataLoader;
import dtp.gameobject.GameObject;
import dtp.state.GameWorld;

public class PhysicalMap extends GameObject{
    
    public int[][] phys_map;
    private int titleSize;
    
    public PhysicalMap(float x, float y, GameWorld gameWorld){
        super(x, y, gameWorld);

        this.titleSize = 34;
        this.phys_map = DataLoader.getInstance().getPhysicalMap();
    }

    public int getTitleSize(){
        return this.titleSize;
    }

    public int[][] getPhys_map() {
		return phys_map;
    }
    
    @Override
	public void Update() {}
	
	public Rectangle haveCollisionWithTop(Rectangle rect) { // kiem tra va cham voi tran 
		
		int PosX1 = rect.x/titleSize;
		PosX1 -= 2;
		int PosX2 = (rect.x + rect.width)/titleSize;
		PosX2 += 2;
		
		int PosY1 = rect.y /titleSize;
		
		if(PosX1 < 0) PosX1 = 0;
		
		if(PosX2 >= phys_map[0].length) PosX2 = phys_map[0].length - 1;
		
		for(int y = PosY1; y >= 0; y--) {
			
			for(int x = PosX1; x <= PosX2; x++) {
				
				if(phys_map[y][x] == 1) {
					Rectangle rectangle = new Rectangle((int) getPosX() + x * titleSize, (int) getPosY() + y * titleSize, titleSize, titleSize);
					if(rect.intersects(rectangle))
						return rectangle;
				}
			}
		}
		
		return null;
    }
    
    public Rectangle haveCollisionWithLand(Rectangle rect) {    // kiem tra cham dat
		
		int PosX1 = rect.x / titleSize;
		PosX1 -= 2;
		int PosX2 = (rect.x + rect.width)/titleSize;
		PosX2 += 2;
		
		int PosY1 = (rect.y + rect.height)/titleSize;
		
		if(PosX1 < 0) PosX1 = 0;
		
		if(PosX2 >= phys_map[0].length) PosX2 = phys_map[0].length - 1;
		
		for(int y = PosY1; y < phys_map.length; y++) {
			for(int x = PosX1; x <= PosX2; x++) {
				
				if(phys_map[y][x]== 1 ) {
					Rectangle re = new Rectangle((int) getPosX() + x*titleSize, (int)getPosY() + y*titleSize, titleSize, titleSize);
					if(rect.intersects(re))
						return re;
				}
			}
		}
		
		return null;
    }
    
    public Rectangle haveCollisionWithRightWall(Rectangle rect) {   // kiem tra cham tuong phai
		
		int PosY1 = rect.y / titleSize;
		PosY1 -= 2;
		int PosY2 = (rect.y + rect.height)/titleSize;
		PosY2 += 2;
		
		int PosX1 = (rect.x + rect.width) / titleSize;
		int PosX2 = PosX1 + 3;
		
		if(PosX2 >= phys_map[0].length) PosX2 = phys_map[0].length - 1;
		
		if(PosY1 < 0) PosY1 = 0;
		if(PosY2 >= phys_map.length) PosY2 = phys_map.length - 1;
		
		for(int x = PosX1; x <= PosX2; x++) {
			
			for(int y = PosY1; y <= PosY2; y++) {
				if(phys_map[y][x] == 1) {
					Rectangle r = new Rectangle((int) getPosX() + x * titleSize, (int) getPosY() + y *titleSize,
							titleSize, titleSize);
					if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
						return r;
				}
			}
		}
		
		return null;
    }
    
    public Rectangle haveCollisionWithLeftWall(Rectangle rect) {    // kiem tra cham tuong trai
		
		int posY1 = rect.y / titleSize;
		posY1 -= 2;
		int posY2 = (rect.y + rect.height)/titleSize;
		posY2 += 2;
		
		int posX1 = (rect.x + rect.width)/titleSize;
		int posX2 = posX1 - 3;
		if(posX2 < 0) posX2 = 0;
		
		if(posY1 < 0) posY1 = 0;
		if(posY2 >= phys_map.length) posY2 = phys_map.length - 1;
		
		for(int x = posX1; x >= posX2; x--) {
			
			for(int y = posY1; y <= posY2; y++) {
				if(phys_map[y][x] == 1) {
					Rectangle r = new Rectangle((int) getPosX() + x *titleSize, (int) getPosY() + y * titleSize, titleSize, titleSize);
					if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
						return r;
				}
			}
		}
		
		return null;
    }
    
    public void draw(Graphics2D g2) {   // ve physical map
		
		Camera camera = getGameWorld().getCamera();
		
		g2.setColor(Color.GRAY);
		for(int i = 0; i < phys_map.length; i++) {
			for(int j = 0; j < phys_map[0].length; j++) {
				if(phys_map[i][j] != 0)
					g2.fillRect((int) getPosX() + j*titleSize - (int) camera.getPosX(), (int) getPosY() + i*titleSize - (int) camera.getPosY(),
					titleSize, titleSize);
			}
		}
	}

}