package dtp.human;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.bullet.RobotRBullet;
import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.gameobject.ParticularObject;
import dtp.state.GameWorld;

@SuppressWarnings("deprecation")
public class RobotR extends ParticularObject{
    
    private Animation forwardAnim, backAnim;
    private long startTimeToShoot;
    private float x1, x2, y1, y2;
    private AudioClip shooting;

    public RobotR(float x, float y, GameWorld gameWorld, boolean music) {
    	super(x, y, 127, 90, 0, 100, gameWorld, music);
    	backAnim = DataLoader.getInstance().getAnimation("robotR");
    	forwardAnim = DataLoader.getInstance().getAnimation("robotR");
    	forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        
        setTimeForNoBeHurt(300000000);
        setDamage(10);

        x1 = x - 100;
        x2 = x + 100;
        y1 = y - 50;
        y2 = y + 50;

        setSpeedX(1);
        setSpeedY(1);
        shooting = DataLoader.getInstance().getSounds("robotRshooting");
    }
    



	@Override
    public void attack() {
        if(isMusic() == true){
            shooting.play();
        }
    	Bullet bullet = new RobotRBullet(getPosX(), getPosY(), getGameWorld());
    	if(getDirection() == LEFT_DIR) bullet.setSpeedX(5);
    	else bullet.setSpeedX(-5);
    	bullet.setTeamType(getTeamType());
    	getGameWorld().getBulletManager().addObject(bullet);
    }
    
    public void Update() {
    	super.Update();
    	if(getPosX() - getGameWorld().getNinja().getPosX() > 0) setDirection(ParticularObject.RIGHT_DIR);
    	else setDirection(ParticularObject.LEFT_DIR);
    	
    	if(getPosX() < x1)
    		setSpeedX(1);
    	else if(getPosX() > x2)
    		setSpeedX(-1);
    	setPosX(getPosX() + getSpeedX());
    	
    	if(getPosY() < y1)
    		setSpeedY(1);
    	else if(getPosY() > y2)
    		setSpeedY(-1);
    	setPosY(getPosY() + getSpeedY());
    	
    	if(System.nanoTime() - startTimeToShoot > 1000 * 10000000 * 1.5) {
    		attack();
    		startTimeToShoot = System.nanoTime();
    	}
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
    	Rectangle rect = getBoundForCollisionWithMap();
    	rect.x += 20;
    	rect.width -= 40;
    	return rect;
    }
    
    @Override
    public void draw(Graphics2D g2) {
    	if(!isObjectOutOfCameraView()) {
    		if(getState() == NOBEHURT && (System.nanoTime() / 10000000)%2 != 1) {
    		}else {
    			if(getDirection() == LEFT_DIR) {
    				backAnim.Update(System.nanoTime());
    				backAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
    			}else {
    				forwardAnim.Update(System.nanoTime());
    				forwardAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
    			}
    		}
    	}
    }

}