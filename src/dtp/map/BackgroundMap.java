package dtp.map;

import java.awt.Graphics2D;

import dtp.effect.DataLoader;
import dtp.gameobject.GameObject;
import dtp.state.GameWorld;
import dtp.userinterface.GameMain;

public class BackgroundMap extends GameObject{
    
    public int[][] backmap;
	private int titleSize;
	
	public BackgroundMap(float x, float y, GameWorld gameWorld) {
		super(x, y, gameWorld);
		backmap = DataLoader.getInstance().getBackgroundMap();
		titleSize = 30;
    }

    @Override
    public void Update() {}
    
    public void draw(Graphics2D g2) {
		//Camera camera = getGameWorld().getCamera();
		
		for(int i = 0; i < backmap.length; i++) {
			for(int j = 0; j < backmap[0].length; j++) {
				if(backmap[i][j] != 0 && j * titleSize /*- camera.getPosX()*/ > -30 && j * titleSize /*- camera.getPosX()*/ < GameMain.SCREEN_WIDTH
					&& i * titleSize /*- camera.getPosY()*/ > -30 && i * titleSize/* - camera.getPosY()*/ < GameMain.SCREEN_HEIGHT) {
					g2.drawImage(DataLoader.getInstance().getFrameImage("tiled"+backmap[i][j]).getImage(), (int) getPosX() + j * titleSize /*- (int) camera.getPosX()*/,
							(int) getPosY() + i * titleSize /*- (int) camera.getPosY()*/, null);
				}
			}
		}
	}

}