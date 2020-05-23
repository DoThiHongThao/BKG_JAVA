package dtp.gameobject;

import dtp.state.GameWorld;

import java.awt.Rectangle;

public abstract class Human extends ParticularObject {
    
    private boolean isJumping; // trang thai bay
	private boolean isDicking; // trang thai ngoi
	
    private boolean isLanding; // trang thai dung
    
    public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
		super(x, y, width, height, mass, blood, gameWorld);
		setState(ALIVE);
    }
    
    public abstract void run(); // chay
	
	public abstract void jump(); // nhay
	
	public abstract void dick(); // ngoi
	
	public abstract void standUp(); // dung day
	
    public abstract void stopRun(); // ngung chay

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean isDicking() {
        return isDicking;
    }

    public void setDicking(boolean isDicking) {
        this.isDicking = isDicking;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public void setLanding(boolean isLanding) {
        this.isLanding = isLanding;
    }
    
    @Override
	public void Update() {
		super.Update();
		
		if(getState() == ALIVE || getState() == NOBEHURT){
			
			if(!isLanding) {
				
				setPosX(getPosX() + getSpeedX());
				
				/* neu trang thai dang di sang trai va nguoi choi bi cham tuong trai thi
				 * se lay Rectangle tra ve khi bi va cham tuong trai 
				 * de set lai vi tri cua nhan vat */
				
				if(getDirection() == LEFT_DIR &&
						getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
					
					Rectangle rectLeftWall = getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap());
					setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);
				}
				
				/* neu trang thai dang di sang phai va nguoi choi bi cham tuong phai thi
				 * se lay Rectangle tra ve khi bi va cham tuong phai 
				 * de set lai vi tri cua nhan vat */
				
				if(getDirection() == RIGHT_DIR &&
						getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
					Rectangle rectRightWall = getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap());
					setPosX(rectRightWall.x - getWidth()/2);	// lay tai tam cua nhan vat
				}
				
				
				Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();	// lay rectangle la tam cua nhan vat
				boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);	
				Rectangle rectLand = getGameWorld().getPhysicalMap().haveCollisionWithLand(boundForCollisionWithMapFuture); // rectangle cua cham dat
				
				Rectangle rectTop = getGameWorld().getPhysicalMap().haveCollisionWithTop(boundForCollisionWithMapFuture); // rectangle cham tran
				
				if(rectTop != null) {
					
					setSpeedY(0);
					setPosY(rectTop.y + getGameWorld().getPhysicalMap().getTitleSize() + getHeight()/2);
				}else if(rectLand != null){
					setJumping(false);
					if(getSpeedY() > 0) setLanding(true);
					setSpeedY(0);
					setPosY(rectLand.y - getHeight()/2 - 1);
				}else {
					setJumping(true);
					setSpeedY(getSpeedY() + getMass());
					setPosY(getPosY() + getSpeedY());
				}
			}
		}
	}
}