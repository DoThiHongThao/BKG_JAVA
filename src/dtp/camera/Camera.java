package dtp.camera;

import dtp.gameobject.GameObject;
import dtp.human.Ninja;
import dtp.state.GameWorld;

public class Camera extends GameObject {

    private float widthView;
    private float heightView;
    private boolean isLocked = false;

    public Camera(float x, float y, float widthView, float heightView , GameWorld gameWorld) {
        super(x, y, gameWorld);
        this.heightView = heightView;
        this.widthView = widthView;
    }

    @Override
    public void Update() {
        if(!isLocked){
            Ninja mainCharacter = getGameWorld().getNinja();
			
			if(mainCharacter.getPosX() - getPosX() > 400) setPosX(mainCharacter.getPosX() - 400);
			if(mainCharacter.getPosX() - getPosX() < 200) setPosX(mainCharacter.getPosX() - 200);
			
			if(mainCharacter.getPosY() - getPosY() > 400) setPosY(mainCharacter.getPosY() - 400);
			else if(mainCharacter.getPosY() - getPosY() < 250) setPosY(mainCharacter.getPosY() - 250);
        }
    }
    
    public void lock() {
        isLocked = true;
    }

    public float getWidthView() {
        return widthView;
    }

    public float getHeightView() {
        return heightView;
    }

}