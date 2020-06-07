package dtp.bullet;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.state.GameWorld;

public class MuiTen extends Bullet{
    
    private Animation forwardBulletAnim, backBulletAnim;

    public MuiTen(float x, float y, GameWorld gameWorld){
        super(x, y, 50 , 20, 1.0f, 10, gameWorld);

        forwardBulletAnim = DataLoader.getInstance().getAnimation("mui_ten");
        backBulletAnim = DataLoader.getInstance().getAnimation("mui_ten");
        backBulletAnim.flipAllImage();
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
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
    }

    @Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void attack() {}
}