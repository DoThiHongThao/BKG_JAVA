package dtp.state;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dtp.userinterface.GamePanel;

@SuppressWarnings("deprecation")
public class GameWorld extends State {

    private BufferedImage bufferedImage;
    private int lastState;

    public static final int finalBossX = 3600;
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
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;

    private boolean finalbossTrigger = true;

    private int numberOfLife = 3;

    public AudioClip bgMusic;

    public GameWorld(GamePanel gamePanel) {
        super(gamePanel);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Render() {
        // TODO Auto-generated method stub

    }

    @Override
    public BufferedImage getBufferedImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPressedButton(int code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setReleasedButton(int code) {
        // TODO Auto-generated method stub

    }


}
