package dtp.gameobject;

import java.awt.Graphics2D;

import dtp.state.GameWorld;

public abstract class Bullet extends ParticularObject{
    
    public Bullet(float x, float y, float width, float height, float mass, int damage, GameWorld gameWorld) {
		super(x, y, width, height, mass, 1, gameWorld, true);
		setDamage(damage);
    }
    
    public abstract void draw(Graphics2D g2);

    public void Update() {
		super.Update();
		setPosX(getPosX() + getSpeedX());
		setPosY(getPosY() + getSpeedY());
		ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWithEnemyObject(this);
		if(object != null && object.getState() == ALIVE) {
			setBlood(0);
			setState(DEATH);
			object.beHurt(getDamage());
		}
	}
}