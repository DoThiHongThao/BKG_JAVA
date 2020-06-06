package dtp.human;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

import dtp.bullet.MuiTen;
import dtp.effect.Animation;
import dtp.effect.DataLoader;
import dtp.gameobject.Bullet;
import dtp.gameobject.Human;
import dtp.state.GameWorld;

public class GreenBoss extends Human {
    
    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation goforward, goback; 
    private Animation dieforward, dieback;
    private long startTimeForAttacked;
    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>();
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;

    @SuppressWarnings("deprecation")
    public GreenBoss(float x, float y, GameWorld gameWorld, boolean music){
        super(x, y, 100, 150, 0.1f, 100, gameWorld, music);
        
        setTimeForNoBeHurt(500 * 1000000);
        setDamage(10);

        idleback = DataLoader.getInstance().getAnimation("boss_dung");
        idleforward = DataLoader.getInstance().getAnimation("boss_dung");
        idleback.flipAllImage();

        shootingback = DataLoader.getInstance().getAnimation("boss_ban");
        shootingforward = DataLoader.getInstance().getAnimation("boss_ban");
        shootingback.flipAllImage();

        goback = DataLoader.getInstance().getAnimation("boss_boss_di");
        goforward = DataLoader.getInstance().getAnimation("boss_boss_di");
        goback.flipAllImage();

        dieback = DataLoader.getInstance().getAnimation("boss_chet");
        dieforward = DataLoader.getInstance().getAnimation("boss_chet");
        dieback.flipAllImage();

        attackType[0] = "NONE";
        attackType[1] = "shooting";
        attackType[2] = "NONE";
        attackType[3] = "go";

        timeAttack.put("NONE", new Long(2000));
        timeAttack.put("shooting", new Long(300));
        timeAttack.put("go", new Long(4000));
    }

    public void Update() {
        super.Update();
        if (getGameWorld().getNinja().getPosX() > getPosX())
            setDirection(RIGHT_DIR);
        else setDirection(LEFT_DIR);

        if (startTimeForAttacked == 0)
            startTimeForAttacked = System.currentTimeMillis();
        else if (System.currentTimeMillis() - startTimeForAttacked > 300) {
            attack();
            startTimeForAttacked = System.currentTimeMillis();
        }

        if (!attackType[attackIndex].equals("NONE")) {
            if (attackType[attackIndex].equals("shooting")) {

                Bullet bullet = new MuiTen(getPosX(), getPosY(), getGameWorld());

                if (getDirection() == LEFT_DIR) bullet.setSpeedX(-4);
                else bullet.setSpeedX(4);
                bullet.setTeamType(getTeamType());
                getGameWorld().getBulletManager().addObject(bullet);
            } else if (attackType[attackIndex].equals("go")) {
                if (getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null)
                    setSpeedX(5);
                if (getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null)
                    setSpeedX(-5);
                setPosX(getPosX() + getSpeedX());
            }
        } else {
            setSpeedX(0);
        }
    }

    @Override
    public void run() {
        setSpeedX(5);
    }
    @Override
    public void jump() {
        setSpeedY(-5);
    }
    @Override
    public void dick() {}
    @Override
    public void standUp() {}
    @Override
    public void stopRun() {}
    @Override
    public void attack() {
        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])) {
            lastAttackTime = System.currentTimeMillis();
            
            attackIndex++;
            if(attackIndex >= attackType.length) attackIndex = 0;
            
            if(attackType[attackIndex].equals("go")) {
                if(getPosX() < getGameWorld().getNinja().getPosX()) setSpeedX(5);
                else setSpeedX(-5);
            }
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        if(attackType[attackIndex].equals("go")) {
            Rectangle rect = getBoundForCollisionWithMap();
            rect.x += 10;
            rect.width -= 10;
            return rect;
        }else
            return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if(getState() == NOBEHURT && (System.nanoTime() / 10000000)%2 != 1) {
            
        }else if(getState() != FEY) {
            if(attackType[attackIndex].equals("NONE")) {
                if(getDirection() == RIGHT_DIR) {
                    idleforward.Update(System.nanoTime());
                    idleforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), (int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }else {
                    idleback.Update(System.nanoTime());
                    idleback.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
                        (int)getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
                }
            }else  if(attackType[attackIndex].equals("shooting")) {
                if(getDirection() == RIGHT_DIR) {
                    shootingforward.Update(System.nanoTime());
                    shootingforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int)getPosY() - (int)getGameWorld().getCamera().getPosY(), g2);
                }else {
                    shootingback.Update(System.nanoTime());
                    shootingback.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()),
                    (int)getPosY() - (int)getGameWorld().getCamera().getPosY(), g2);
                }
            }else if(attackType[attackIndex].equals("go")) {
                if(getSpeedX() > 0) {
                    goforward.Update(System.nanoTime());
                    goforward.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()), (int)getPosY() - (int)getGameWorld().getCamera().getPosY(), g2);
                }else {
                    goback.Update(System.nanoTime());
                    goback.draw((int)(getPosX() - getGameWorld().getCamera().getPosX()), (int)getPosY() - (int)getGameWorld().getCamera().getPosY(), g2);
                }
            }
        }else if(getState() == FEY){
            if(getDirection() == RIGHT_DIR) {
				dieforward.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()), 
				(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			}else {
				dieback.setCurrentFrame(dieforward.getCurrentFrame());
				dieback.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
				(int) getPosY() - (int) getGameWorld().getCamera().getPosY(), g2);
			}
        }
    }
}