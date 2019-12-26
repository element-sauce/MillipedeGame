import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Base Mushroom class, class for default mushroom
 * 
 * @author Bill Xiang 
 * @version 2.0
 */
public class Mushroom extends Actor
{
    public Mushroom() {
        
    }
    
    protected int numHits = 0;
    protected int hitCapacity = 4;
    protected int scoreValue = 1;
    
    public int level = 0;
    
    public void act() 
    {
        onHit();
        specialActiveBehavior();
        
    }    
    
    public void onHit() {
        
        if (isTouching(Laser.class) == true) {
            numHits++;
            removeTouching(Laser.class);
            specialHitBehavior();
            if (numHits >= hitCapacity) {
                List scoreList = getWorld().getObjects(Score.class);
                Score score = (Score)scoreList.get(0);
                score.setScore(score.getScore()+scoreValue);
                specialEndBehavior();
                
            } else {
                //getImage().scale(getImage().getWidth(), getImage().getHeight()/2);
                changeImage();
            }
            
        }
    }
    
    public void specialActiveBehavior() {
        if (numHits == 0) {
            GreenfootImage image = new GreenfootImage((level + 1) + "Mushroom_001.png");
            int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
            image.scale(gridSize, gridSize);
            setImage(image);
        } else if (numHits == 1) {
            GreenfootImage image = new GreenfootImage((level + 1) + "Mushroom_002.png");
            int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
            image.scale(gridSize, gridSize);
            setImage(image);
        } else if (numHits == 2) {
            GreenfootImage image = new GreenfootImage((level + 1) + "Mushroom_003.png");
            int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
            image.scale(gridSize, gridSize);
            setImage(image);
        } else if (numHits == 3) {
            GreenfootImage image = new GreenfootImage((level + 1) + "Mushroom_004.png");
            int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
            image.scale(gridSize, gridSize);
            setImage(image);
        }
    }
    
    public void specialHitBehavior() {
        
    }
    
    public void specialEndBehavior() {
        getWorld().removeObject(this);
    }
    
    public void changeImage() {
        /*
        GreenfootImage image = null;
        if (numHits == 1) {
            image = new GreenfootImage((level + 1) + "Mushroom_002.png");
        } else if (numHits == 2) {
            image = new GreenfootImage((level + 1) + "Mushroom_003.png");
        } else if (numHits == 3) {
            image = new GreenfootImage((level + 1) + "Mushroom_004.png");
        }
        int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
        image.scale(gridSize, gridSize);
        setImage(image);
        */
    }
    
    public int getScoreValue() {
        return scoreValue;
    }
    
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}
