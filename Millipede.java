import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Millipede, the ally insect that moves away the player, destroying a mushroom
 * , and killing a centipede.
 * 
 * It is unaffected by friendly laser fire.
 * 
 * It dies after impact with a mushroom or a centipede.
 * 
 * @author Bill Xiang
 * @version 1.0
 */
public class Millipede extends Actor
{
    private static final int MENU = 0;
    private static final int PLAYING = 1;
    private static final int SUSPENDED = 2;
    private static final int NEXTLEVEL = 3;
    private static final int GAMEOVER = 4;
    private static final int WIN = 5;
    
    public int level = 0;
    
    int dx = 3;
    
    public Millipede() {
        
    }
    
    public void act() 
    {
        MyWorld myWorld = (MyWorld)(getWorld());
        
        if (myWorld.getState() == PLAYING || myWorld.getState() == NEXTLEVEL) {            
            /** One of the differences with Centipede, dx is subtracted from X */
            setLocation(getX() - dx, getY());
            World w = getWorld();
            int worldW = w.getWidth();
            int worldH = w.getHeight();
            
            GreenfootImage img = getImage();
            int imgW = img.getWidth();
            int imgH = img.getHeight();
            /*
            if (w.getObjectsAt(getX() + imgW/2, getY(), Mushroom.class).size() != 0) {
                setLocation(getX(), getY() + imgH);
                img.mirrorHorizontally();
                dx *= -1;
            } else if (w.getObjectsAt(getX() - imgW/2, getY(), Mushroom.class).size() != 0) {
                setLocation(getX(), getY() + imgH);
                img.mirrorHorizontally();
                dx *= -1;
            }
            */
            
            if (getX() + imgW/2 > worldW) {
                setLocation(worldW - imgW/2, getY() - imgH); 
                img.mirrorHorizontally();
                dx *= -1;
            } else if (getX() - imgW/2 < 0) {
                setLocation(imgW/2, getY() - imgH);
                img.mirrorHorizontally();
                dx *= -1;
            }
            
            /** In millipedes the image is mirrored horizontally when dx is positive, opposite to Centipede*/
            if (dx > 0) {
                if (getWorld().getObjectsAt(getX()-((MyWorld)getWorld()).GRID_SIZE, getY(), Millipede.class).isEmpty() && getX()+((MyWorld)getWorld()).GRID_SIZE *3 /2 < ((MyWorld)getWorld()).WORLD_W) {
                    GreenfootImage image = new GreenfootImage((level + 1) + "MillipedeHead.png");
                    image.mirrorHorizontally();
                    image.scale(20, 20);
                    setImage(image);
                } else {
                    //while (!getIntersectingObjects(Millipede.class).isEmpty()) {
                      //  setLocation(getX()-1, getY());
                    //}
                    GreenfootImage image = new GreenfootImage((level + 1) + "MillipedeBody.png");
                    image.mirrorHorizontally();
                    image.scale(20, 20);
                    setImage(image);
                }
           
                                
            } else if (dx < 0) {
                if (getWorld().getObjectsAt(getX()+((MyWorld)getWorld()).GRID_SIZE, getY(), Millipede.class).isEmpty() && getX()-((MyWorld)getWorld()).GRID_SIZE *3 /2 > 0) {
                    GreenfootImage image = new GreenfootImage((level + 1) + "MillipedeHead.png");
                    image.scale(20, 20);
                    setImage(image);
                } else {
                    
                    //while (!getIntersectingObjects(Millipede.class).isEmpty()) {
                      //  setLocation(getX()+1, getY());
                    //}
                    GreenfootImage image = new GreenfootImage((level + 1) + "MillipedeBody.png");
                    image.scale(20, 20);
                    setImage(image);
                }
            }
        
            if (isTouching(Centipede.class) == true) {
                    removeTouching(Centipede.class);
                    Centipede tcent = new Centipede();
                    int scoreValue = tcent.getScoreValue();
                    
                    List scoreList = getWorld().getObjects(Score.class);
                    Score score = (Score)scoreList.get(0);
                    score.setScore(score.getScore()+scoreValue);
                    
                    getWorld().removeObject(this);
                    
                    
            } else if (isTouching(Mushroom.class) == true) {
                    removeTouching(Mushroom.class);
                    Mushroom tmush = new Mushroom();
                    int scoreValue = tmush.getScoreValue();
                    
                    List scoreList = getWorld().getObjects(Score.class);
                    Score score = (Score)scoreList.get(0);
                    score.setScore(score.getScore()+scoreValue);
                    
                    getWorld().removeObject(this);
                    
                    
            }
        }
    }    
}
