package dtp.camera;

import dtp.gameobject.GameObject;
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