package dtp.bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.state.GameWorld;

public class RedEyeBullet extends Bullet{
    
    private Animation forwardBulletAnim, backBulletAnim;
	
	public RedEyeBullet(float x, float y, GameWorld gameWorld) {
		super(x, y, 30, 30, 1.0f, 10, gameWorld);
		forwardBulletAnim = DataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnim = DataLoader.getInstance().getAnimation("redeyebullet");
		backBulletAnim.flipAllImage();
	}
	
	@Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }
	
	@Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
        } else {
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
        }
    }
	
	@Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void attack() {}
}