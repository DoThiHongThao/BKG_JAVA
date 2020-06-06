package dtp.state;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dtp.camera.Camera;
import dtp.control.Lables;
import dtp.effect.DataLoader;
import dtp.gameobject.ParticularObject;
import dtp.human.Bat;
import dtp.human.DarkRaise;
import dtp.human.FinalBoss;
import dtp.human.Ninja;
import dtp.human.RedEyeDevil;
import dtp.human.RobotR;
import dtp.manager.BulletManager;
import dtp.manager.ParticularObjectManager;
import dtp.map.BackgroundMap;
import dtp.map.PhysicalMap;
import dtp.userinterface.GameMain;
import dtp.userinterface.GamePanel;

@SuppressWarnings("deprecation")
public class GameWorld extends State {

    private BufferedImage bufferedImage;
    private int lastState;

    Lables[] lable;

    private Ninja ninja;
    private PhysicalMap physicalMap;
    private Camera camera;
    private BulletManager bulletManager;
    private ParticularObjectManager particularObjectManager;
    private BackgroundMap backgroundMap;

    public static final int finalBossX = 2270;
    public static final int finalBossY = 840;
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;

    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;

    public int storyTutorial = 0;
    
    private boolean finalbossTrigger = true;
    private boolean music;

    private long score = 0;

    ParticularObject boss;
    private int numberOfLife = 3;

    public AudioClip bgMusic;

    public int BOT;

    public GameWorld(GamePanel gamePanel, boolean music) {
        super(gamePanel);

        this.music = music;

        bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        ninja = new Ninja(136, 1800, this, music);
        physicalMap = new PhysicalMap(0, 0, this);
        backgroundMap = new BackgroundMap(0, 0, this);
        camera = new Camera(0, 45, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT, this);
        bulletManager = new BulletManager(this);

        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(ninja);

        initEnemies();
        bgMusic = DataLoader.getInstance().getSounds("bgmusic");
    }

    private void initEnemies() {
        this.BOT = 1;
        ParticularObject redeye = new RedEyeDevil(480, 1696, this, isMusic());
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);

        ParticularObject redeye1 = new RedEyeDevil(2585, 1800, this, isMusic());
        redeye1.setDirection(ParticularObject.LEFT_DIR);
        redeye1.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye1);

        ParticularObject redeye2 = new RedEyeDevil(2390, 1496, this, isMusic());
        redeye2.setDirection(ParticularObject.RIGHT_DIR);
        redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye2);

        ParticularObject redeye3 = new RedEyeDevil(920, 1292, this, isMusic());
        redeye3.setDirection(ParticularObject.LEFT_DIR);
        redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);

        ParticularObject darkraise = new DarkRaise(950, 1720, this, isMusic());
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);

        ParticularObject darkraise2 = new DarkRaise(2040, 1200, this, isMusic());
        darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise2);

        ParticularObject bat = new Bat(1938, 1428, this, isMusic());
        bat.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(bat);

        ParticularObject robotR = new RobotR(1730, 1700, this, isMusic());
        robotR.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR);

        ParticularObject robotR2 = new RobotR(1428, 1200, this, isMusic());
        robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR2);

    }

    public PhysicalMap getPhysicalMap() {
        return physicalMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public ParticularObjectManager getParticularObjectManager() {
        return particularObjectManager;
    }

    public boolean isMusic() {
        return music;
    }

    public Ninja getNinja() {
        return ninja;
    }

    public long getScore(){
        return score;
    }

    public void setScore(long score){
        this.score = score;
    }
    public BulletManager getBulletManager() {
        return bulletManager;
    }

    public BackgroundMap getBackgroundMap() {
        return backgroundMap;
    }

    public void switchState(int state) {
        previousState = this.state;
        this.state = state;
    }

    private void TutorialUpdate() {
        switch (tutorialState) {

            case MEETFINALBOSS:
                if (storyTutorial == 0) {
                    if (openIntroGameY >= 450) {
                        openIntroGameY -= 1;
                    }
                    if (camera.getPosX() < finalBossX) {
                        camera.setPosX(camera.getPosX() + 2);
                    }
                    if (ninja.getPosX() < finalBossX + 150) {

                        ninja.setDirection(ParticularObject.RIGHT_DIR);
                        ninja.run();
                        ninja.Update();

                    } else {
                        ninja.stopRun();
                    }
                    if (openIntroGameY < 450 && camera.getPosX() >= finalBossX && 
                            ninja.getPosX() >= finalBossX + 150 && ninja.getPosY() <= finalBossY) {
                        camera.lock();
                        ninja.stopRun();
                        physicalMap.phys_map[21][89] = 1;
                        physicalMap.phys_map[22][89] = 1;
                        physicalMap.phys_map[23][89] = 1;
                        physicalMap.phys_map[24][89] = 1;
                        switchState(GAMEPLAY);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void TutorialRender(Graphics2D g2) {
        switch (tutorialState) {
            case INTROGAME:
                int yMid = GameMain.SCREEN_HEIGHT / 2 - 15;
                int y1 = yMid - GameMain.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                int y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT / 2);

                break;
            case MEETFINALBOSS:
                yMid = GameMain.SCREEN_HEIGHT / 2 - 15;
                y1 = yMid - GameMain.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT / 2);
                break;
            default:
                break;
        }
    }

    @Override
    public void Update() {
        switch (state) {
            case INIT_GAME:
                break;
            case TUTORIAL:
                TutorialUpdate();
                break;
            case GAMEPLAY:
                particularObjectManager.UpdateObjects(); // update
                bulletManager.UpdateObjects(); //
                physicalMap.Update();
                if(ninja.getPosX() <= 2500)
                    camera.Update();
                
                if ((ninja.getPosX() > finalBossX && ninja.getPosY() <= finalBossY) && finalbossTrigger) {
                    finalbossTrigger = false;
                    switchState(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;
                    boss = new FinalBoss(finalBossX + 364, finalBossY - 20, this, isMusic());
                    boss.setTeamType(ParticularObject.ENEMY_TEAM);
                    boss.setDirection(ParticularObject.LEFT_DIR);
                    particularObjectManager.addObject(boss);
                }

                if (ninja.getState() == ParticularObject.DEATH) {
                    numberOfLife--;
                    if (numberOfLife >= 0) {
                        ninja.setBlood(100);
                        ninja.setPosY(ninja.getPosY() - 50);
                        ninja.setState(ParticularObject.NOBEHURT);
                        particularObjectManager.addObject(ninja);
                    } else {
                        switchState(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if (BOT <= 0)
                    switchState(GAMEWIN);
                break;
            case GAMEOVER:
                break;
            case GAMEWIN:
                break;
        }
    }

    @Override
    public void Render() {
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if (g2 != null) {
            switch (state) {
                case INIT_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    break;
                case PAUSEGAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(300, 260, 500, 70);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ESC TO CONTINUE", 400, 300);
                    break;
                case TUTORIAL:
                    backgroundMap.draw(g2);
                    if (tutorialState == MEETFINALBOSS) {
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    backgroundMap.draw(g2);//
                    particularObjectManager.draw(g2);//
                    bulletManager.draw(g2);//
                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, ninja.getBlood(), 20);

                    String diem = String.valueOf(getScore());
                    g2.setColor(Color.YELLOW);
                    g2.drawString("SCORE: " +diem.toString(),GameMain.SCREEN_WIDTH - 100, 20);
                    for (int i = 0; i < numberOfLife; i++) {
                        g2.drawImage(DataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i * 40, 18,
                                null);
                    }
                    if (state == GAMEWIN) {
                        //g2.drawImage(DataLoader.getInstance().getFrameImage("score").getImage(), 250, 50, null);
                        createGameWin();
                        drawGameWin(g2);
                    }
                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;
            }
        }
    }

    private void createGameWin(){
        lable = new Lables[6];
        lable[0].setOpaque(false);
        lable[0].setImageIcon1(DataLoader.getInstance().getFrameImage("endgame").getImage());
        lable[0].setBound(250, 50, 400, 400);
    }

    private void drawGameWin(Graphics2D g2){
        lable[0].draw(g2);
    }

    @Override
    public BufferedImage getBufferedImage() {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(GameMain.SCREEN_WIDTH, GameMain.SCREEN_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            return this.bufferedImage;
        }
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                ninja.jump();
                break;
            case KeyEvent.VK_DOWN:
                ninja.dick();
                break;
            case KeyEvent.VK_RIGHT:
                ninja.setDirection(ninja.RIGHT_DIR);
                ninja.run();
                break;
            case KeyEvent.VK_LEFT:
                ninja.setDirection(ninja.LEFT_DIR);
                ninja.run();
                break;
            case KeyEvent.VK_ENTER:
                if (state == GameWorld.INIT_GAME) {
                    if (previousState == GameWorld.GAMEPLAY)
                        switchState(GameWorld.GAMEPLAY);
                    else
                        switchState(GameWorld.TUTORIAL);
                    if (isMusic() == true) {
                        bgMusic.loop();
                        bgMusic.play();
                    } else
                        bgMusic.stop();
                }
                if (state == GameWorld.TUTORIAL) {
                    if (tutorialState != GameWorld.MEETFINALBOSS) {
                        switchState(GameWorld.GAMEPLAY);
                    }
                    if (tutorialState == GameWorld.MEETFINALBOSS) {
                        switchState(GameWorld.GAMEPLAY);
                    }
                }
                break;
            case KeyEvent.VK_SPACE:
                ninja.attack();
                break;

        }
    }

    @Override
    public void setReleasedButton(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_DOWN:
                ninja.standUp();
                break;
            case KeyEvent.VK_RIGHT:
                if (ninja.getSpeedX() > 0)
                    ninja.stopRun();
                break;
            case KeyEvent.VK_LEFT:
                if (ninja.getSpeedX() < 0)
                    ninja.stopRun();
                break;
            case KeyEvent.VK_ENTER:
                
                if (state == PAUSEGAME) {
                    state = lastState;
                }
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_ESCAPE:
                if(state != PAUSEGAME){
                    lastState = state;
                    state = PAUSEGAME;
                }else{
                    state = lastState;
                }
                break;
        }
    }
}

