import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Centipede, the enemy insect that moves toward the player, navigating through
 * mushrooms to do so.
 * 
 * Centipedes are vulnerable to lasers.
 * 
 * Every level, the color scheme of the centipede changes.
 * 
 * Number of centipedes spawned and the speed of the centipede
 * may vary among different levels.
 * 
 * @author Bill Xiang
 * @version 2.0
 */
public class Centipede extends Actor
{

    
    private static final int MENU = 0;
    private static final int PLAYING = 1;
    private static final int SUSPENDED = 2;
    private static final int NEXTLEVEL = 3;
    private static final int GAMEOVER = 4;
    private static final int WIN = 5;
    
    private int scoreValue = 10;
    
    public int level = 0;
    
    int dx = 3;  
    
    public Centipede() {
        /*
        int level = ((MyWorld)getWorld()).getLevel();
        dx = 2;
        if (level == 2) {
            dx = 4;
        } else if (level == 3) {
            dx = 6;
        }
        */
        
    }
    
    public void act() 
    {
        // Add your action code here.
        MyWorld myWorld = (MyWorld)(getWorld());

        if (myWorld.getState() == PLAYING || myWorld.getState() == NEXTLEVEL) {            
            
            setLocation(getX() + dx, getY());
            World w = getWorld();
            int worldW = w.getWidth();
            int worldH = w.getHeight();
            
            GreenfootImage img = getImage();
            int imgW = img.getWidth();
            int imgH = img.getHeight();
            
            if (w.getObjectsAt(getX() + imgW/2, getY(), Mushroom.class).size() != 0) {
                setLocation(getX(), getY() + imgH);
                img.mirrorHorizontally();
                dx *= -1;
            } else if (w.getObjectsAt(getX() - imgW/2, getY(), Mushroom.class).size() != 0) {
                setLocation(getX(), getY() + imgH);
                img.mirrorHorizontally();
                dx *= -1;
            }
            
            if (getX() + imgW/2 > worldW) {
                setLocation(worldW - imgW/2, getY() + imgH); 
                img.mirrorHorizontally();
                dx *= -1;
            } else if (getX() - imgW/2 < 0) {
                setLocation(imgW/2, getY() + imgH);
                img.mirrorHorizontally();
                dx *= -1;
            }
            
            if (dx > 0) {
                if (getWorld().getObjectsAt(getX()+((MyWorld)getWorld()).GRID_SIZE, getY(), Centipede.class).isEmpty() && getX()+((MyWorld)getWorld()).GRID_SIZE *3 /2 < ((MyWorld)getWorld()).WORLD_W) {
                    GreenfootImage image = new GreenfootImage((level + 1) + "CentipedeHead.png");
                    image.scale(20, 20);
                    setImage(image);
                } else {
                    while (!getIntersectingObjects(Centipede.class).isEmpty()) {
                        setLocation(getX()-1, getY());
                    }
                    GreenfootImage image = new GreenfootImage((level + 1) + "CentipedeBody.png");
                    image.scale(20, 20);
                    setImage(image);
                }
           
                
                
            } else if (dx < 0) {
                if (getWorld().getObjectsAt(getX()-((MyWorld)getWorld()).GRID_SIZE, getY(), Centipede.class).isEmpty() && getX()-((MyWorld)getWorld()).GRID_SIZE *3 /2 > 0) {
                    GreenfootImage image = new GreenfootImage((level + 1) + "CentipedeHead.png");
                    image.scale(20, 20);
                    image.mirrorHorizontally();
                    setImage(image);
                } else {
                    
                    while (!getIntersectingObjects(Centipede.class).isEmpty()) {
                        setLocation(getX()+1, getY());
                    }
                    GreenfootImage image = new GreenfootImage((level + 1) + "CentipedeBody.png");
                    image.scale(20, 20);
                    image.mirrorHorizontally();
                    setImage(image);
                }
            }
            
            
            if (isTouching(Laser.class) == true) {
                removeTouching(Laser.class);
                Mushroom mush = new Mushroom();
                mush.getImage().scale(20, 20);
                mush.level = this.level;
                List scoreList = getWorld().getObjects(Score.class);
                Score score = (Score)scoreList.get(0);
                score.setScore(score.getScore()+100);
                ((MyWorld)getWorld()).mushrooms.add(mush);
                getWorld().addObject(mush, getX(), getY());
                getWorld().removeObject(this);
                
                
            } else if (isTouching(Player.class)|| getY() > myWorld.getHeight()) {
                myWorld.setState(GAMEOVER);
            }
            
            
        }
        
        
    }    
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}
