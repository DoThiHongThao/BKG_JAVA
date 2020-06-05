package dtp.bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.gameobject.ParticularObject;
import dtp.state.GameWorld;

public class BlueFire extends Bullet{
    
    private Animation forwardBulletAnim, backBulletAnim;

    public BlueFire(float x, float y, GameWorld gameWorld) {
		super(x, y, 60, 30, 1.0f, 10, gameWorld);
		forwardBulletAnim = DataLoader.getInstance().getAnimation("chieu_thuc_luoi");
		backBulletAnim = DataLoader.getInstance().getAnimation("chieu_thuc_luoi");
		backBulletAnim.flipAllImage();
    }
    
    @Override
	public void draw(Graphics2D g2) {

		if (getSpeedX() > 0) {
			if(!forwardBulletAnim.isIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 3) {
				forwardBulletAnim.setIgnoreFrame(0);
				forwardBulletAnim.setIgnoreFrame(1);
				forwardBulletAnim.setIgnoreFrame(2);
			}
			forwardBulletAnim.Update(System.nanoTime());
			
			forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}else {
			if(!backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 3) {
				backBulletAnim.setIgnoreFrame(0);
				backBulletAnim.setIgnoreFrame(1);
				backBulletAnim.setIgnoreFrame(2);
			}
			backBulletAnim.Update(System.nanoTime());
			backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
		}
    }
    
    @Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
    }
    
    @Override
	public void Update() {
		
		if(forwardBulletAnim.isIgnoreFrame(0) || backBulletAnim.isIgnoreFrame(0)) {
			setPosX(getPosX() + getSpeedX());	
		}
		ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWithEnemyObject(this);
		if(object != null && object.getState() == ALIVE) {
			setBlood(0);
			setState(DEATH);
			object.setBlood(object.getBlood() - getDamage());
			object.setState(BEHURT);
		}
	}
	
	@Override
	public void attack() {}
}