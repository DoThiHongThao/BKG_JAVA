package dtp.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dtp.effect.Animation;
import dtp.state.GameWorld;

public abstract class ParticularObject extends GameObject {

    public static final int LEAGUE_TEAM = 1; // set team nhan vat
	public static final int ENEMY_TEAM = 2;     // set team boss
	
	public static final int LEFT_DIR = 0;       // di sang trai
	public static final int RIGHT_DIR = 1;      // di sang phai
	
	public static final int ALIVE = 0;          // tinh trang song
	public static final int BEHURT = 1;         // tinh trang trung dan
	public static final int FEY = 2;            // tinh trang suyt die
	public static final int DEATH = 3;          // tinh trang die
	public static final int NOBEHURT = 4;       // tinh trang khong bi trung dan
    private int state = ALIVE;                  // ban dau song
    
    private float width;                        // chieu rong cua
	private float height;                       // chieu cao cua
	private float mass;                         // trong luong cua
	private float speedX;                       // toc do theo chieu x cua
	private float speedY;                       // toc do theo chieu y cua
	private int blood;                          // mau cua
    
	private int damage;                         // kha nang sat thuong cua
	
	private int direction;                      // phuong huong cua
	
	protected Animation behurtForvardAnim, behurtBackAnimation; 
	
	private int teamType;                       // kieu cua team boos or nv
	
	private long startTimeNoBeHurt;             // thoi gian bat dau khi k trung dan
    private long timeForNoBeHurt;               // thoi gian khi k trung dan

    private boolean music;                      // set music
    
    public ParticularObject(float x, float y, float width, float height, float mass,
    int blood, GameWorld gameWorld, boolean music) {
        super(x, y, gameWorld);

        this.width = width;
        this.height = height;
        this.mass = mass;
        this.blood = blood;

        this.music = music;
        direction = RIGHT_DIR;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        if(this.blood >= 0){
            this.blood = blood;
        }else this.blood = 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Animation getBehurtForvardAnim() {
		return behurtForvardAnim;
    }
    
    public Animation getBehurtBackAnimation() {
		return behurtBackAnimation;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setBehurtForvardAnim(Animation behurtForvardAnim) {
        this.behurtForvardAnim = behurtForvardAnim;
    }

    public void setBehurtBackAnimation(Animation behurtBackAnimation) {
        this.behurtBackAnimation = behurtBackAnimation;
    }

    public long getStartTimeNoBeHurt() {
        return startTimeNoBeHurt;
    }

    public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
        this.startTimeNoBeHurt = startTimeNoBeHurt;
    }

    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }

    public void setTimeForNoBeHurt(long timeForNoBeHurt) {
        this.timeForNoBeHurt = timeForNoBeHurt;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    };

    public abstract void attack();
    
    public boolean isObjectOutOfCameraView() {
		if(getPosX() - getGameWorld().getCamera().getPosX() > getGameWorld().getCamera().getWidthView() ||
				getPosX() - getGameWorld().getCamera().getPosX() < -50 ||
				getPosY() - getGameWorld().getCamera().getPosY() > getGameWorld().getCamera().getHeightView() ||
				getPosY() - getGameWorld().getCamera().getPosY() < -50)
			return true;
		else return false;
	}

	public Rectangle getBoundForCollisionWithMap() { // kiem tra va cham voi ban do
		Rectangle bound = new Rectangle();          // tao ra mot class kiem tra va cham 
		bound.x = (int) (getPosX() - (getWidth()/2));
		bound.y = (int) (getPosY() - (getHeight()/2));
		bound.width = (int) getWidth();
		bound.height = (int) getHeight();
		
		return bound;
    }

    // set mau lai cho nv
	public void beHurt(int damgeEat) {
		setBlood(getBlood() - damgeEat);
		state = BEHURT;
		hurtingCallback(); // goi cho cai lp ke thua 
    }
    
    @Override
	public void Update() {
		switch(state) {
			case ALIVE:
				
				//note: SET DAMAGE FOR OBJECT NO DAMAGE
				ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWithEnemyObject(this);
				if(object != null) {
					
					if(object.getDamage() > 0) {
						beHurt(object.getDamage());
					}
				}
				
				break;
				
			case BEHURT: // trung dan
				if(behurtBackAnimation == null) {
					state = NOBEHURT;
					startTimeNoBeHurt = System.nanoTime();
					if(getBlood() == 0)
						state = FEY;
				}else {
					behurtForvardAnim.Update(System.nanoTime());
					if(behurtForvardAnim.isLastFrame()) {
						behurtForvardAnim.reset();
						state = NOBEHURT;
						if(getBlood() == 0)
							state = FEY;
						startTimeNoBeHurt = System.nanoTime();
					}
				}
				break;
				
			case FEY: // gan chet
                state = DEATH; // chet
<<<<<<< HEAD
                getGameWorld().BOT--;
=======
                ParticularObject object2 = getGameWorld().getParticularObjectManager().getCollisionWithEnemyObject(this);
                if(object2 != null && object2 instanceof ParticularObject)
                    this.getGameWorld().BOT--;
>>>>>>> 83b17af55560426da29e7038ddcf312ef4c6e5f3
				break;
				
			case NOBEHURT: // khong trung dan
				if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt) {
					state = ALIVE;
				}
				break;
			
				default:
					break;
		}
    }
    
    public void drawBoundForCollisionWithMap(Graphics2D g2) {
		Rectangle rectangle = getBoundForCollisionWithMap();
		g2.setColor(Color.GREEN);
		
		g2.drawRect(rectangle.x - (int) getGameWorld().getCamera().getPosX(), rectangle.y - (int) getGameWorld().getCamera().getPosY(),(int) rectangle.width, rectangle.height);
		
	}
	
	public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
		Rectangle rectangle = getBoundForCollisionWithEnemy();
		g2.setColor(Color.RED);
		g2.drawRect(rectangle.x - (int) getGameWorld().getCamera().getPosX(), rectangle.y - (int) getGameWorld().getCamera().getPosY(), rectangle.width, rectangle.height);
	}

    public abstract Rectangle getBoundForCollisionWithEnemy();
	public abstract void draw(Graphics2D g2);
	
	public void hurtingCallback() {}

}