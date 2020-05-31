package dtp.human;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.bullet.RedEyeBullet;
import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.gameobject.ParticularObject;
import dtp.state.GameWorld;
@SuppressWarnings("deprecation")
public class RedEyeDevil extends ParticularObject{
    
    private Animation forwardAnim, backAnim;
	private long startTimeToShoot;
    private AudioClip shooting;
	
	public RedEyeDevil(float x, float y, GameWorld gameWorld, boolean music) {
		super(x, y, 127, 89, 0, 100, gameWorld, music);
		backAnim = DataLoader.getInstance().getAnimation("redeye");
		forwardAnim = DataLoader.getInstance().getAnimation("redeye");
		forwardAnim.flipAllImage();
		startTimeToShoot = 0;
		
		setDamage(10);
		setTimeForNoBeHurt(300000000);
		shooting = DataLoader.getInstance().getSounds("redeyeshooting");
	}
	
	@Override
	public void attack() {
        if(isMusic() == true){
            shooting.play();
        }
		
		Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
		if(getDirection() == LEFT_DIR) bullet.setSpeedX(-7);
		else bullet.setSpeedX(7);
		bullet.setTeamType(getTeamType());
		getGameWorld().getBulletManager().addObject(bullet);
	}
	
	public void Update() {
		super.Update();
		if(System.nanoTime() - startTimeToShoot > 1000 * 10000000) {
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
			if(getState() == NOBEHURT && (System.nanoTime() / 10000000) % 2 != 1) {
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