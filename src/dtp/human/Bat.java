package dtp.human;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.bullet.FlyBullet;
import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.gameobject.ParticularObject;
import dtp.state.GameWorld;

public class Bat extends ParticularObject{
    
    private Animation forwardAnim, backAnim, Anim;
	
	private long startTimeShoot;
    private float x1, x2;
    
    public Bat(float x, float y, GameWorld gameWorld, boolean music){
        super(x, y, 127, 89, 0, 100, gameWorld, music);
        Anim = DataLoader.getInstance().getAnimation("bat");
		backAnim = DataLoader.getInstance().getAnimation("bat_right");
		forwardAnim = DataLoader.getInstance().getAnimation("bat_right");
		forwardAnim.flipAllImage();
		startTimeShoot = 0;
		setTimeForNoBeHurt(300000000);
		x1 = x - 100;
		x2 = x + 100;
		
		setSpeedX(1);
		setDamage(10);
    }

    @Override
	public void attack() {
		float NinjaX = getGameWorld().getNinja().getPosX();
		float NinjaY = getGameWorld().getNinja().getPosY();
		float deltaX = NinjaX - getPosX();
		float deltaY = NinjaY - getPosY();
		float speed = 4;
		float a = Math.abs(deltaX/deltaY);
		float speedX = (float) Math.sqrt(speed * speed * a * a / (a * a +1));
		float speedY = (float) Math.sqrt(speed * speed / (a * a + 1));
		
		Bullet bullet = new FlyBullet(getPosX(), getPosY(), getGameWorld());
		if(deltaX < 0) {
			bullet.setSpeedX(-speedX);
		}else bullet.setSpeedX(speedX);
		bullet.setSpeedY(speedY);
		bullet.setTeamType(getTeamType());
		getGameWorld().getParticularObjectManager().addObject(bullet);
    }
    
    public void Update() {
		super.Update();
		if(getPosX() < x1)
			setSpeedX(1);
		else if(getPosX() > x2)
			setSpeedX(-1);
		setPosX(getPosX() + getSpeedX());
		if(System.nanoTime() - startTimeShoot > 1000 * 10000000 * 1.5) {
			attack();
			startTimeShoot = System.nanoTime();
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
                Anim.Update(System.nanoTime());
                Anim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
			}else {

                if(getDirection() == LEFT_DIR) {
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()), g2);
                }else {
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()),
                        (int)(getPosY() - getGameWorld().getCamera().getPosY()), g2);
                }
            }
        }
    }
}